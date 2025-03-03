/*
 * Copyright (C) 2023 Square, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.squareup.wire.swift

import com.squareup.wire.Syntax.PROTO_2
import com.squareup.wire.Syntax.PROTO_3
import com.squareup.wire.internal.camelCase
import com.squareup.wire.schema.EnclosingType
import com.squareup.wire.schema.EnumType
import com.squareup.wire.schema.Field
import com.squareup.wire.schema.Field.EncodeMode
import com.squareup.wire.schema.MessageType
import com.squareup.wire.schema.OneOf
import com.squareup.wire.schema.ProtoType
import com.squareup.wire.schema.Schema
import com.squareup.wire.schema.Type
import com.squareup.wire.schema.internal.DagChecker
import com.squareup.wire.schema.internal.optionValueToInt
import com.squareup.wire.schema.internal.optionValueToLong
import io.outfoxx.swiftpoet.ARRAY
import io.outfoxx.swiftpoet.AttributeSpec
import io.outfoxx.swiftpoet.BOOL
import io.outfoxx.swiftpoet.CASE_ITERABLE
import io.outfoxx.swiftpoet.CodeBlock
import io.outfoxx.swiftpoet.DATA
import io.outfoxx.swiftpoet.DICTIONARY
import io.outfoxx.swiftpoet.DOUBLE
import io.outfoxx.swiftpoet.DeclaredTypeName
import io.outfoxx.swiftpoet.EnumerationCaseSpec
import io.outfoxx.swiftpoet.ExtensionSpec
import io.outfoxx.swiftpoet.FLOAT
import io.outfoxx.swiftpoet.FileMemberSpec
import io.outfoxx.swiftpoet.FileSpec
import io.outfoxx.swiftpoet.FunctionSignatureSpec
import io.outfoxx.swiftpoet.FunctionSpec
import io.outfoxx.swiftpoet.FunctionTypeName
import io.outfoxx.swiftpoet.INT
import io.outfoxx.swiftpoet.INT32
import io.outfoxx.swiftpoet.INT64
import io.outfoxx.swiftpoet.Modifier.FILEPRIVATE
import io.outfoxx.swiftpoet.Modifier.MUTATING
import io.outfoxx.swiftpoet.Modifier.PRIVATE
import io.outfoxx.swiftpoet.Modifier.PUBLIC
import io.outfoxx.swiftpoet.Modifier.STATIC
import io.outfoxx.swiftpoet.OPTIONAL
import io.outfoxx.swiftpoet.ParameterSpec
import io.outfoxx.swiftpoet.ParameterizedTypeName
import io.outfoxx.swiftpoet.PropertySpec
import io.outfoxx.swiftpoet.STRING
import io.outfoxx.swiftpoet.TypeAliasSpec
import io.outfoxx.swiftpoet.TypeName
import io.outfoxx.swiftpoet.TypeSpec
import io.outfoxx.swiftpoet.TypeVariableName
import io.outfoxx.swiftpoet.UINT32
import io.outfoxx.swiftpoet.UINT64
import io.outfoxx.swiftpoet.VOID
import io.outfoxx.swiftpoet.joinToCode
import io.outfoxx.swiftpoet.parameterizedBy
import okio.ByteString.Companion.encode

class SwiftGenerator private constructor(
  val schema: Schema,
  private val nameToTypeName: Map<ProtoType, DeclaredTypeName>,
  private val referenceCycleIndirections: Map<ProtoType, Set<ProtoType>>,
) {
  private val proto2Codable = DeclaredTypeName.typeName("Wire.Proto2Codable")
  private val proto3Codable = DeclaredTypeName.typeName("Wire.Proto3Codable")
  private val protoMessage = DeclaredTypeName.typeName("Wire.ProtoMessage")
  private val protoReader = DeclaredTypeName.typeName("Wire.ProtoReader")
  private val protoWriter = DeclaredTypeName.typeName("Wire.ProtoWriter")
  private val protoEnum = DeclaredTypeName.typeName("Wire.ProtoEnum")

  private val heap = DeclaredTypeName.typeName("Wire.Heap")
  private val indirect = DeclaredTypeName.typeName("Wire.Indirect")
  private val redactable = DeclaredTypeName.typeName("Wire.Redactable")
  private val redactedKey = DeclaredTypeName.typeName("Wire.RedactedKey")
  private val defaulted = DeclaredTypeName.typeName("Wire.Defaulted")

  private val stringLiteralCodingKeys = DeclaredTypeName.typeName("Wire.StringLiteralCodingKeys")

  private val equatable = DeclaredTypeName.typeName("Swift.Equatable")
  private val hashable = DeclaredTypeName.typeName("Swift.Hashable")
  private val sendable = DeclaredTypeName.typeName("Swift.Sendable")
  private val uncheckedSendable = TypeVariableName.typeVariable("@unchecked Sendable")

  private val codable = DeclaredTypeName.typeName("Swift.Codable")
  private val codingKey = DeclaredTypeName.typeName("Swift.CodingKey")
  private val decoder = DeclaredTypeName.typeName("Swift.Decoder")
  private val encoder = DeclaredTypeName.typeName("Swift.Encoder")
  private val writableKeyPath = DeclaredTypeName.typeName("Swift.WritableKeyPath")

  private val deprecated = AttributeSpec.builder("available").addArguments("*", "deprecated").build()

  private val ProtoType.typeName
    get() = nameToTypeName.getValue(this)
  private val ProtoType.isMessage
    get() = schema.getType(this) is MessageType
  private val ProtoType.isEnum
    get() = schema.getType(this) is EnumType
  private val Type.typeName
    get() = type.typeName

  private val MessageType.needsCustomCodable: Boolean
    get() = type.enclosingTypeOrPackage == "google.protobuf" && NEEDS_CUSTOM_CODABLE.contains(type.simpleName)

  private val TypeName.isStringEncoded
    get() = when (this.makeNonOptional()) {
      INT64, UINT64 -> true
      DATA -> true
      else -> false
    }

  fun generatedTypeName(type: Type) = type.typeName

  internal val Field.isMessage
    get() = type!!.isMessage
  internal val Field.isEnum
    get() = type!!.isEnum
  internal val Field.isOptional: Boolean
    get() = typeName.optional
  internal val Field.isMap: Boolean
    get() = type!!.isMap
  internal val Field.keyType: ProtoType
    get() = type!!.keyType!!
  internal val Field.valueType: ProtoType
    get() = type!!.valueType!!

  private val Field.isRequiredParameter: Boolean
    get() = !isOptional && !isRepeated && !isMap

  private val Field.isCollection: Boolean
    get() = when (typeName.makeNonOptional()) {
      DATA, STRING -> true
      else -> isMap || isRepeated
    }

  private val Field.codableDefaultValue: String?
    get() = default?.let {
      return it
    } ?: when (typeName.makeNonOptional()) {
      BOOL -> "false"
      DOUBLE, FLOAT -> "0"
      INT32, UINT32, INT64, UINT64 -> "0"
      else -> null
    }

  // see https://protobuf.dev/programming-guides/proto3/#default
  private val Field.proto3InitialValue: String
    get() = when {
      isMap -> "[:]"
      isRepeated -> "[]"
      isOptional -> "nil"
      else -> when (typeName.makeNonOptional()) {
        BOOL -> "false"
        DOUBLE, FLOAT -> "0"
        INT32, UINT32, INT64, UINT64 -> "0"
        STRING -> """""""" // evaluates to the empty string
        DATA -> ".init()"
        else -> "nil"
      }
    }

  private val Field.codableName: String?
    get() = jsonName?.takeIf { it != name } ?: camelCase(name).takeIf { it != name }

  private val Field.typeName: TypeName
    get() = when (encodeMode!!) {
      EncodeMode.MAP -> DICTIONARY.parameterizedBy(keyType.typeName, valueType.typeName)
      EncodeMode.REPEATED,
      EncodeMode.PACKED,
      -> ARRAY.parameterizedBy(type!!.typeName)
      EncodeMode.NULL_IF_ABSENT -> OPTIONAL.parameterizedBy(type!!.typeName)
      EncodeMode.REQUIRED -> type!!.typeName
      EncodeMode.OMIT_IDENTITY -> {
        when {
          isOneOf || isMessage -> OPTIONAL.parameterizedBy(type!!.typeName)
          else -> type!!.typeName
        }
      }
    }

  fun generateTypeTo(type: Type, builder: FileSpec.Builder) {
    val fileMembers = mutableListOf<FileMemberSpec>()
    generateType(type, fileMembers).forEach {
      builder.addType(it)
    }

    for (extension in fileMembers) {
      builder.addMember(extension)
    }
  }

  private fun generateType(type: Type, fileMembers: MutableList<FileMemberSpec>) = when (type) {
    is MessageType -> generateMessage(type, fileMembers)
    is EnumType -> listOf(generateEnum(type, fileMembers))
    is EnclosingType -> listOf(generateEnclosing(type, fileMembers))
    else -> error("Unknown type $type")
  }

  private fun String.sanitizeDoc(): String {
    return this
      // Remove trailing whitespace on each line.
      .replace("[^\\S\n]+\n".toRegex(), "\n")
      .replace("\\s+$".toRegex(), "")
      .replace("\\*/".toRegex(), "&#42;/")
      .replace("/\\*".toRegex(), "/&#42;")
  }

  /**
   * Returns true if [field] inside [type] forms a cycle and needs an indirection to allow it
   * to compile.
   */
  private fun isIndirect(type: MessageType, field: Field): Boolean {
    if (field.isRepeated) {
      return false // Arrays are heap-allocated.
    }
    return field.type in referenceCycleIndirections.getOrDefault(type.type, emptySet())
  }

  private val MessageType.isHeapAllocated get() = fields.size + oneOfs.size >= 16

  /**
   * Checks that every enum in a proto3 message contains a value with tag 0.
   *
   * @throws NoSuchElementException if the case doesn't exist
   */
  @Throws(NoSuchElementException::class)
  private fun validateProto3DefaultsExist(type: MessageType) {
    if (type.syntax == PROTO_2) { return }

    // validate each enum field
    type
      .fields
      .mapNotNull { schema.getType(it.type!!) as? EnumType }
      .forEach { enum ->
        // ensure that a 0 case exists
        if (enum.constants.filter { it.tag == 0 }.isEmpty()) {
          throw NoSuchElementException("Missing a zero value for ${enum.name}")
        }
      }
  }

  @OptIn(ExperimentalStdlibApi::class) // TODO move to build flag
  private fun generateMessage(
    type: MessageType,
    fileMembers: MutableList<FileMemberSpec>,
  ): List<TypeSpec> {
    val structType = type.typeName
    val oneOfEnumNames = type.oneOfs.associateWith { structType.nestedType(it.name.replaceFirstChar(Char::uppercase)) }

    // TODO use a NameAllocator
    val propertyNames = type.fields.map { it.name } + type.oneOfs.map { it.name }

    val storageType = structType.nestedType("Storage")
    val storageName = if ("storage" in propertyNames) "_storage" else "storage"

    val typeSpecs = mutableListOf<TypeSpec>()

    validateProto3DefaultsExist(type)

    typeSpecs += TypeSpec.structBuilder(structType)
      .addModifiers(PUBLIC)
      .apply {
        if (type.documentation.isNotBlank()) {
          addDoc("%L\n", type.documentation.sanitizeDoc())
        }

        if (type.isHeapAllocated) {
          addAttribute(
            AttributeSpec.builder("dynamicMemberLookup").build(),
          )

          addProperty(
            PropertySpec.varBuilder(storageName, storageType, PRIVATE)
              .addAttribute(AttributeSpec.builder(heap).build())
              .build(),
          )

          generateMessageStoragePropertyDelegates(type, storageName, storageType)
          generateMessageStorageDelegateConstructor(type, storageName, structType, storageType, oneOfEnumNames, fileMembers)

          addFunction(
            FunctionSpec.builder("copyStorage")
              .addModifiers(PRIVATE, MUTATING)
              .beginControlFlow("if", "!isKnownUniquelyReferenced(&_%N)", storageName)
              .addStatement("_%1N = %2T(wrappedValue: %1N)", storageName, heap)
              .endControlFlow("if")
              .build(),
          )
        } else {
          generateMessageProperties(type, oneOfEnumNames)
          generateMessageConstructor(type, structType, oneOfEnumNames, fileMembers)
        }

        generateMessageOneOfs(type, oneOfEnumNames, fileMembers)

        type.nestedTypes.forEach { nestedType ->
          generateType(nestedType, fileMembers).forEach {
            addType(it)
          }
        }
      }
      .build()

    val structEquatableExtension = ExtensionSpec.builder(structType)
      .addSuperType(equatable)
      .build()
    fileMembers += FileMemberSpec.builder(structEquatableExtension)
      .addGuard("!$FLAG_REMOVE_EQUATABLE")
      .build()

    val structHashableExtension = ExtensionSpec.builder(structType)
      .addSuperType(hashable)
      .build()
    fileMembers += FileMemberSpec.builder(structHashableExtension)
      .addGuard("!$FLAG_REMOVE_HASHABLE")
      .build()

    val structSendableType = if (type.isHeapAllocated) {
      uncheckedSendable
    } else {
      sendable
    }
    val structSendableExtension = ExtensionSpec.builder(structType)
      .addSuperType(structSendableType)
      .build()
    fileMembers += FileMemberSpec.builder(structSendableExtension)
      .addGuard("swift(>=5.5)")
      .build()

    val redactionExtension = if (type.fields.any { it.isRedacted }) {
      ExtensionSpec.builder(structType)
        .addSuperType(redactable)
        .addType(
          TypeSpec.enumBuilder("RedactedKeys")
            .addModifiers(PUBLIC)
            .addSuperType(STRING)
            .addSuperType(redactedKey)
            .apply {
              type.fields.forEach { field ->
                if (field.isRedacted) {
                  addEnumCase(field.name)
                }
              }
            }
            .build(),
        )
    } else {
      null
    }

    if (type.isHeapAllocated) {
      val storageExtension = ExtensionSpec.builder(structType)
        .addType(
          TypeSpec.structBuilder(storageType)
            .addModifiers(PUBLIC)
            .apply {
              generateMessageProperties(type, oneOfEnumNames, forStorageType = true)
              generateMessageConstructor(type, storageType, oneOfEnumNames, fileMembers, includeMemberwiseDefaults = false)
            }
            .build(),
        )
        .build()
      fileMembers += FileMemberSpec.builder(storageExtension).build()

      val structProtoCodableExtension = ExtensionSpec.builder(structType)
        .addSuperType(type.protoCodableType)
        .addFunction(
          FunctionSpec.constructorBuilder()
            .addModifiers(PUBLIC)
            .addParameter("from", "reader", protoReader)
            .throws(true)
            .addStatement("self.%N = try %T(from: reader)", storageName, storageType)
            .build(),
        )
        .addFunction(
          FunctionSpec.builder("encode")
            .addModifiers(PUBLIC)
            .addParameter("to", "writer", protoWriter)
            .throws(true)
            .addStatement("try %N.encode(to: writer)", storageName)
            .build(),
        )
        .build()
      fileMembers += FileMemberSpec.builder(structProtoCodableExtension).build()

      val storageMessageConformanceExtension = ExtensionSpec.builder(storageType)
        .messageConformanceExtension(type)
        .build()
      fileMembers += FileMemberSpec.builder(storageMessageConformanceExtension).build()

      val storageProtoCodableExtension = ExtensionSpec.builder(storageType)
        .messageProtoCodableExtension(type, structType, oneOfEnumNames, propertyNames)
        .build()
      fileMembers += FileMemberSpec.builder(storageProtoCodableExtension).build()

      val structCodableExtension = heapCodableExtension(structType, storageName, storageType)
      fileMembers += FileMemberSpec.builder(structCodableExtension)
        .addGuard("!$FLAG_REMOVE_CODABLE")
        .build()

      val storageCodableExtension = messageCodableExtension(type, storageType)
      fileMembers += FileMemberSpec.builder(storageCodableExtension)
        .addGuard("!$FLAG_REMOVE_CODABLE")
        .build()

      val storageEquatableExtension = ExtensionSpec.builder(storageType)
        .addSuperType(equatable)
        .build()
      fileMembers += FileMemberSpec.builder(storageEquatableExtension)
        .addGuard("!$FLAG_REMOVE_EQUATABLE")
        .build()

      val storageHashableExtension = ExtensionSpec.builder(storageType)
        .addSuperType(hashable)
        .build()
      fileMembers += FileMemberSpec.builder(storageHashableExtension)
        .addGuard("!$FLAG_REMOVE_HASHABLE")
        .build()

      val storageSendableExtension = ExtensionSpec.builder(storageType)
        .addSuperType(sendable)
        .build()
      fileMembers += FileMemberSpec.builder(storageSendableExtension)
        .addGuard("swift(>=5.5)")
        .build()

      if (redactionExtension != null) {
        redactionExtension.addProperty(
          PropertySpec.varBuilder("description", STRING)
            .addModifiers(PUBLIC)
            .getter(
              FunctionSpec.getterBuilder()
                .addStatement("return %N.description", storageName)
                .build(),
            )
            .build(),
        )

        val storageRedactableExtension = ExtensionSpec.builder(storageType)
          .addSuperType(redactable)
          .addType(
            TypeAliasSpec.builder("RedactedKeys", structType.nestedType("RedactedKeys"))
              .addModifiers(PUBLIC)
              .build(),
          )
          .build()
        fileMembers += FileMemberSpec.builder(storageRedactableExtension)
          .addGuard("!$FLAG_REMOVE_REDACTABLE")
          .build()
      }
    } else {
      val messageConformanceExtension = ExtensionSpec.builder(structType)
        .messageConformanceExtension(type)
        .build()
      fileMembers += FileMemberSpec.builder(messageConformanceExtension).build()

      val protoCodableExtension = ExtensionSpec.builder(structType)
        .messageProtoCodableExtension(type, structType, oneOfEnumNames, propertyNames)
        .build()
      fileMembers += FileMemberSpec.builder(protoCodableExtension).build()

      if (!type.needsCustomCodable) {
        fileMembers += FileMemberSpec.builder(messageCodableExtension(type, structType))
          .addGuard("!$FLAG_REMOVE_CODABLE")
          .build()
      }
    }

    if (redactionExtension != null) {
      fileMembers += FileMemberSpec.builder(redactionExtension.build())
        .addGuard("!$FLAG_REMOVE_REDACTABLE")
        .build()
    }

    return typeSpecs
  }

  private fun ExtensionSpec.Builder.messageProtoCodableExtension(
    type: MessageType,
    structType: DeclaredTypeName,
    oneOfEnumNames: Map<OneOf, DeclaredTypeName>,
    propertyNames: Collection<String>,
  ): ExtensionSpec.Builder = apply {
    addSuperType(type.protoCodableType)

    val reader = if ("reader" in propertyNames) "_reader" else "reader"
    val token = if ("token" in propertyNames) "_token" else "token"
    val tag = if ("tag" in propertyNames) "_tag" else "tag"
    addFunction(
      FunctionSpec.constructorBuilder()
        .addModifiers(PUBLIC)
        .addParameter("from", reader, protoReader)
        .throws(true)
        .apply {
          // Declare locals into which everything is written before promoting to members.
          type.fields.forEach { field ->
            val localType = when (type.syntax) {
              PROTO_2 -> if (field.isRepeated || field.isMap) {
                field.typeName
              } else {
                field.typeName.makeOptional()
              }
              PROTO_3 -> if (field.isOptional || (field.isEnum && !field.isRepeated)) {
                field.typeName.makeOptional()
              } else {
                field.typeName
              }
            }

            val initializer = when (type.syntax) {
              PROTO_2 -> when {
                field.isMap -> "[:]"
                field.isRepeated -> "[]"
                else -> "nil"
              }
              PROTO_3 -> field.proto3InitialValue
            }

            addStatement("var %N: %T = %L", field.name, localType, initializer)
          }
          type.oneOfs.forEach { oneOf ->
            val enumName = oneOfEnumNames.getValue(oneOf)
            addStatement("var %N: %T = nil", oneOf.name, enumName.makeOptional())
          }
          if (type.fieldsAndOneOfFields.isNotEmpty()) {
            addStatement("")
          }

          addStatement("let $token = try $reader.beginMessage()")
          beginControlFlow("while", "let $tag = try $reader.nextTag(token: $token)")
          beginControlFlow("switch", tag)
          type.fields.forEach { field ->
            val decoder = CodeBlock.Builder()
            if (field.isMap) {
              decoder.add("try $reader.decode(into: &%N", field.name)
              field.keyType.encoding?.let { keyEncoding ->
                decoder.add(", keyEncoding: .%N", keyEncoding)
              }
              field.valueType.encoding?.let { valueEncoding ->
                decoder.add(", valueEncoding: .%N", valueEncoding)
              }
            } else {
              if (field.isRepeated) {
                decoder.add("try $reader.decode(into: &%N", field.name)
              } else {
                val typeName = field.typeName.makeNonOptional()

                decoder.add("%N = try $reader.decode(%T.self", field.name, typeName)
              }
              field.type!!.encoding?.let { encoding ->
                decoder.add(", encoding: .%N", encoding)
              }
            }
            decoder.add(")")
            addStatement("case %L: %L", field.tag, decoder.build())
          }
          type.oneOfs.forEach { oneOf ->
            oneOf.fields.forEach { field ->
              when {
                // ProtoReader.decode() return optional for enums. Handle that specially.
                field.isEnum -> {
                  addStatement(
                    "case %1L: %2N = (try $reader.decode(%4T.self)).flatMap { .%3N(\$0) }",
                    field.tag,
                    oneOf.name,
                    field.name,
                    field.typeName.makeNonOptional(),
                  )
                }
                else -> {
                  addStatement(
                    "case %1L: %2N = .%3N(try $reader.decode(%4T.self))",
                    field.tag,
                    oneOf.name,
                    field.name,
                    field.typeName.makeNonOptional(),
                  )
                }
              }
            }
          }
          addStatement("default: try $reader.readUnknownField(tag: $tag)")
          endControlFlow("switch")
          endControlFlow("while")
          addStatement("self.unknownFields = try $reader.endMessage(token: $token)")

          // Check required and bind members.
          addStatement("")
          type.fields.forEach { field ->
            val initializer = when (type.syntax) {
              PROTO_2 -> if (field.isOptional || field.isRepeated || field.isMap) {
                CodeBlock.of("%N", field.name)
              } else {
                CodeBlock.of("try %1T.checkIfMissing(%2N, %2S)", structType, field.name)
              }
              PROTO_3 -> if (field.isEnum && !field.isRepeated) {
                CodeBlock.of("try %1T.defaultIfMissing(%2N)", field.typeName.makeNonOptional(), field.name)
              } else {
                CodeBlock.of("%N", field.name)
              }
            }
            addStatement(
              if (field.default != null) "_%N.wrappedValue = %L" else { "self.%N = %L" },
              field.name,
              initializer,
            )
          }
          type.oneOfs.forEach { oneOf ->
            addStatement("self.%1N = %1N", oneOf.name)
          }
        }
        .build(),
    )

    val writer = if ("writer" in propertyNames) "_writer" else "writer"
    addFunction(
      FunctionSpec.builder("encode")
        .addModifiers(PUBLIC)
        .addParameter("to", writer, protoWriter)
        .throws(true)
        .apply {
          type.fields.forEach { field ->
            if (field.isMap) {
              addCode("try $writer.encode(tag: %L, value: self.%N", field.tag, field.name)
              field.keyType.encoding?.let { keyEncoding ->
                addCode(", keyEncoding: .%N", keyEncoding)
              }
              field.valueType.encoding?.let { valueEncoding ->
                addCode(", valueEncoding: .%N", valueEncoding)
              }
              addCode(")\n")
            } else {
              addCode("try $writer.encode(tag: %L, value: self.%N", field.tag, field.name)
              field.type!!.encoding?.let { encoding ->
                addCode(", encoding: .%N", encoding)
              }
              if (field.isPacked) {
                addCode(", packed: true")
              }
              addCode(")\n")
            }
          }
          type.oneOfs.forEach { oneOf ->
            beginControlFlow("if", "let %1N = self.%1N", oneOf.name)
            addStatement("try %N.encode(to: $writer)", oneOf.name)
            endControlFlow("if")
          }
        }
        .addStatement("try $writer.writeUnknownFields(unknownFields)")
        .build(),
    )
  }

  private fun ExtensionSpec.Builder.messageConformanceExtension(
    type: MessageType,
  ): ExtensionSpec.Builder = apply {
    addSuperType(protoMessage)
    addFunction(
      FunctionSpec.builder("protoMessageTypeURL")
        .returns(STRING)
        .addModifiers(PUBLIC)
        .addModifiers(STATIC)
        .addStatement("return \"%N\"", type.type.typeUrl!!)
        .build(),
    )
  }

  private val MessageType.protoCodableType: DeclaredTypeName
    get() = when (syntax) {
      PROTO_2 -> proto2Codable
      PROTO_3 -> proto3Codable
    }

  private fun heapCodableExtension(
    structType: DeclaredTypeName,
    storageName: String,
    storageType: DeclaredTypeName,
  ): ExtensionSpec {
    return ExtensionSpec.builder(structType)
      .addSuperType(codable)
      .apply {
        addFunction(
          FunctionSpec.constructorBuilder()
            .addParameter("from", "decoder", decoder)
            .addModifiers(PUBLIC)
            .throws(true)
            .addStatement("let container = try decoder.singleValueContainer()")
            .addStatement("self.%N = try container.decode(%T.self)", storageName, storageType)
            .build(),
        )
        addFunction(
          FunctionSpec.builder("encode")
            .addParameter("to", "encoder", encoder)
            .addModifiers(PUBLIC)
            .throws(true)
            .addStatement("var container = encoder.singleValueContainer()")
            .addStatement("try container.encode(%N)", storageName)
            .build(),
        )
      }
      .build()
  }

  private fun messageCodableExtension(
    type: MessageType,
    structType: DeclaredTypeName,
  ): ExtensionSpec {
    return ExtensionSpec.builder(structType)
      .addSuperType(codable)
      .apply {
        val codingKeys = if (type.fieldsAndOneOfFields.isEmpty()) {
          structType.nestedType("CodingKeys")
        } else {
          stringLiteralCodingKeys
        }

        if (type.fieldsAndOneOfFields.isEmpty()) {
          addType(
            // Coding keys still need to be specified on empty messages in order to prevent `unknownFields` from
            // getting serialized via JSON/Codable. In this case, the keys cannot conform to `RawRepresentable`
            // in order to compile.

            TypeSpec.enumBuilder(codingKeys)
              .addModifiers(PUBLIC)
              .addSuperType(codingKey)
              .build(),
          )
        }

        // We cannot rely upon built-in Codable support since we need to support multiple keys.
        if (type.fieldsAndOneOfFields.isNotEmpty()) {
          addFunction(
            FunctionSpec.constructorBuilder()
              .addParameter("from", "decoder", decoder)
              .addModifiers(PUBLIC)
              .throws(true)
              .addStatement("let container = try decoder.container(keyedBy: %T.self)", codingKeys)
              .apply {
                type.fields.forEach { field ->
                  var typeName: TypeName = field.typeName.makeNonOptional()
                  if (field.isRepeated && typeName is ParameterizedTypeName) {
                    typeName = typeName.typeArguments[0]
                  }

                  var decode = "decode"
                  if (field.isRepeated) {
                    decode += "ProtoArray"
                  } else if (field.isMap) {
                    decode += "ProtoMap"
                  } else if (field.isOptional) {
                    decode += "IfPresent"
                  }

                  val typeArg = if (field.typeName.isStringEncoded) {
                    "stringEncoded: "
                  } else {
                    ""
                  }

                  val forKeys = field.codableName?.let {
                    "firstOfKeys"
                  } ?: "forKey"

                  val keys = listOf(field.codableName, field.name)
                    .filterNotNull()
                    .map { CodeBlock.of("%S", it) }
                    .joinToCode()

                  val prefix = if (field.default != null) { "_%1N.wrappedValue" } else { "self.%1N" }
                  addStatement(
                    "$prefix = try container.$decode($typeArg%2T.self, $forKeys: $keys)",
                    field.name,
                    typeName,
                  )
                }

                type.oneOfs.forEach { oneOf ->
                  oneOf.fields.fold(mutableListOf<Pair<Field, String>>()) { memo, field ->
                    field.codableName?.let { codableName ->
                      memo.add(Pair(field, codableName))
                    }
                    memo.add(Pair(field, field.name))

                    memo
                  }.forEachIndexed { index, pair ->
                    val (field, keyName) = pair

                    val typeName = field.typeName.makeNonOptional()

                    if (index == 0) {
                      beginControlFlow(
                        "if",
                        "let %1N = try container.decodeIfPresent(%2T.self, forKey: %3S)",
                        field.name,
                        typeName,
                        keyName,
                      )
                    } else {
                      nextControlFlow(
                        "else if",
                        "let %1N = try container.decodeIfPresent(%2T.self, forKey: %3S)",
                        field.name,
                        typeName,
                        keyName,
                      )
                    }
                    addStatement("self.%1N = .%2N(%2N)", oneOf.name, field.name)
                  }
                  nextControlFlow("else", "")
                  addStatement("self.%N = nil", oneOf.name)
                  endControlFlow("if")
                }
              }
              .build(),
          )
          addFunction(
            FunctionSpec.builder("encode")
              .addParameter("to", "encoder", encoder)
              .addModifiers(PUBLIC)
              .throws(true)
              .addStatement("var container = encoder.container(keyedBy: %T.self)", codingKeys)
              .apply {
                if (type.fieldsAndOneOfFields.any { it.codableName != null }) {
                  addStatement("let preferCamelCase = encoder.protoKeyNameEncodingStrategy == .camelCase")
                }
                if (type.fields.any { !it.isOptional && (it.isCollection || it.isEnum || it.codableDefaultValue != null) }) {
                  addStatement("let includeDefaults = encoder.protoDefaultValuesEncodingStrategy == .include")
                }
                addStatement("")

                type.fields.forEach { field ->
                  fun addEncode() {
                    var encode = "encode"
                    if (field.isRepeated) {
                      encode += "ProtoArray"
                    } else if (field.isMap) {
                      encode += "ProtoMap"
                    } else if (field.isOptional) {
                      encode += "IfPresent"
                    }

                    val typeArg = if (field.typeName.isStringEncoded) {
                      "stringEncoded: "
                    } else {
                      ""
                    }

                    val (keys, args) = field.codableName?.let { codableName ->
                      Pair(
                        "preferCamelCase ? %2S : %1S",
                        arrayOf(field.name, codableName),
                      )
                    } ?: Pair("%1S", arrayOf(field.name))

                    addStatement(
                      "try container.$encode(${typeArg}self.%1N, forKey: $keys)",
                      *args,
                    )
                  }

                  val defaultValue = field.codableDefaultValue

                  if (field.isOptional) {
                    // A proto3 field that is defined with the optional keyword supports field presence.
                    // Fields that have a value set and that support field presence always include the field value
                    // in the JSON-encoded output, even if it is the default value.
                    addEncode()
                  } else if (field.isCollection) {
                    beginControlFlow("if", "includeDefaults || !self.%N.isEmpty", field.name)
                    addEncode()
                    endControlFlow("if")
                  } else if (field.isEnum) {
                    beginControlFlow("if", "includeDefaults || self.%N.rawValue != 0", field.name)
                    addEncode()
                    endControlFlow("if")
                  } else if (defaultValue != null) {
                    beginControlFlow("if", "includeDefaults || self.%N != $defaultValue", field.name)
                    addEncode()
                    endControlFlow("if")
                  } else {
                    // Message is fundamentally broken right now when it comes to evaluating "isDefault"
                    // We would need to check _every_ value and/or keep track of mutations
                    addEncode()
                  }
                }

                type.oneOfs.forEach { oneOf ->
                  beginControlFlow("switch", "self.%N", oneOf.name)
                  oneOf.fields.forEach { field ->
                    val (keys, args) = field.codableName?.let { codableName ->
                      Pair(
                        "preferCamelCase ? %2S : %1S",
                        arrayOf(field.name, codableName),
                      )
                    } ?: Pair("%1S", arrayOf(field.name))

                    addStatement(
                      "case .%1N(let %1N): try container.encode(%1N, forKey: $keys)",
                      *args,
                    )
                  }
                  addStatement("case %T.none: break", OPTIONAL)
                  endControlFlow("switch")
                }
              }
              .build(),
          )
        }
      }
      .build()
  }

  private fun ParameterSpec.Builder.withFieldDefault(field: Field) = apply {
    when {
      field.isMap -> defaultValue("[:]")
      field.isRepeated -> defaultValue("[]")
      field.isOptional -> defaultValue("nil")
    }
  }

  private fun FunctionSpec.Builder.addParameters(
    type: MessageType,
    oneOfEnumNames: Map<OneOf, DeclaredTypeName>,
    includeDefaults: Boolean = true,
    includeOneOfs: Boolean = true,
    fieldsFilter: (Field) -> Boolean = { true },
  ) = apply {
    type.fields.filter(fieldsFilter).forEach { field ->
      addParameter(
        ParameterSpec.builder(field.name, field.typeName)
          .apply {
            if (includeDefaults) {
              withFieldDefault(field)
            }
          }
          .build(),
      )
    }
    if (includeOneOfs) {
      type.oneOfs.forEach { oneOf ->
        val enumName = oneOfEnumNames.getValue(oneOf).makeOptional()
        addParameter(
          ParameterSpec.builder(oneOf.name, enumName)
            .defaultValue("nil")
            .build(),
        )
      }
    }
  }

  private fun TypeSpec.Builder.generateMessageStorageDelegateConstructor(
    type: MessageType,
    storageName: String,
    structType: DeclaredTypeName,
    storageType: DeclaredTypeName,
    oneOfEnumNames: Map<OneOf, DeclaredTypeName>,
    fileMembers: MutableList<FileMemberSpec>,
  ) {
    val needsConfigure = type.fields.any { !it.isRequiredParameter } || type.oneOfs.isNotEmpty()

    addFunction(
      FunctionSpec.constructorBuilder()
        .addModifiers(PUBLIC)
        .addParameters(
          type,
          oneOfEnumNames,
          includeDefaults = false,
          includeOneOfs = false,
          fieldsFilter = { it.isRequiredParameter },
        )
        .apply {
          if (needsConfigure) {
            val closureType = FunctionTypeName.get(
              TypeVariableName.typeVariable("inout Self.${storageType.simpleName}"),
              returnType = VOID,
            )

            addParameter(
              ParameterSpec.builder("configure", closureType)
                .defaultValue("{ _ in }")
                .build(),
            )
          }
        }
        .apply {
          val storageParams = mutableListOf<CodeBlock>()
          type.fields.filter { it.isRequiredParameter }.forEach { field ->
            storageParams += CodeBlock.of("%1N: %1N", field.name)
          }

          if (needsConfigure) {
            storageParams += CodeBlock.of("%1N: %1N", "configure")
          }

          addStatement(
            "self.%N = %T(\n%L\n)",
            storageName,
            storageType,
            storageParams.joinToCode(separator = ",\n"),
          )
        }
        .build(),
    )

    if (!needsConfigure) {
      return
    }

    // These will be removed in November 2023
    val memberwiseExtension = ExtensionSpec.builder(structType)
      .addFunction(
        FunctionSpec.constructorBuilder()
          .addModifiers(PUBLIC)
          .addParameters(type, oneOfEnumNames, includeDefaults = true)
          .addAttribute(AttributeSpec.builder("_disfavoredOverload").build())
          .addAttribute(deprecated)
          .apply {
            val storageParams = mutableListOf<CodeBlock>()
            type.fields.forEach { field ->
              storageParams += CodeBlock.of("%1N: %1N", field.name)
            }
            type.oneOfs.forEach { oneOf ->
              storageParams += CodeBlock.of("%1N: %1N", oneOf.name)
            }
            addStatement(
              "self.%N = %T(\n%L\n)",
              storageName,
              storageType,
              storageParams.joinToCode(separator = ",\n"),
            )
          }
          .build(),
      )
      .build()

    fileMembers += FileMemberSpec.builder(memberwiseExtension)
      .addGuard("$FLAG_INCLUDE_MEMBERWISE_INITIALIZER")
      .build()
  }

  private fun TypeSpec.Builder.generateMessageConstructor(
    type: MessageType,
    structType: DeclaredTypeName,
    oneOfEnumNames: Map<OneOf, DeclaredTypeName>,
    fileMembers: MutableList<FileMemberSpec>,
    includeMemberwiseDefaults: Boolean = true,
  ) {
    val needsConfigure = type.fields.any { !it.isRequiredParameter } || type.oneOfs.isNotEmpty()

    addFunction(
      FunctionSpec.constructorBuilder()
        .addModifiers(PUBLIC)
        .addParameters(
          type,
          oneOfEnumNames,
          includeDefaults = false,
          includeOneOfs = false,
          fieldsFilter = { it.isRequiredParameter },
        )
        .apply {
          if (needsConfigure) {
            val closureType = FunctionTypeName.get(
              TypeVariableName.typeVariable("inout Self"),
              returnType = VOID,
            )

            addParameter(
              ParameterSpec.builder("configure", closureType)
                .defaultValue("{ _ in }")
                .build(),
            )
          }
        }
        .apply {
          type.fields.filter { it.isRequiredParameter }.forEach { field ->
            addStatement(
              if (field.default != null) "_%1N.wrappedValue = %1N" else { "self.%1N = %1N" },
              field.name,
            )
          }
          if (needsConfigure) {
            addStatement("configure(&self)")
          }
        }
        .build(),
    )

    if (!needsConfigure) {
      return
    }

    // These will be removed in November 2023
    val memberwiseExtension = ExtensionSpec.builder(structType)
      .addFunction(
        FunctionSpec.constructorBuilder()
          .addModifiers(PUBLIC)
          .addParameters(type, oneOfEnumNames, includeDefaults = includeMemberwiseDefaults)
          .addAttribute(AttributeSpec.builder("_disfavoredOverload").build())
          .addAttribute(deprecated)
          .apply {
            type.fields.forEach { field ->
              addStatement(
                if (field.default != null) "_%1N.wrappedValue = %1N" else { "self.%1N = %1N" },
                field.name,
              )
            }
            type.oneOfs.forEach { oneOf ->
              addStatement("self.%1N = %1N", oneOf.name)
            }
          }
          .build(),
      )
      .build()

    fileMembers += FileMemberSpec.builder(memberwiseExtension)
      .addGuard("$FLAG_INCLUDE_MEMBERWISE_INITIALIZER")
      .build()
  }

  private fun TypeSpec.Builder.generateMessageProperties(
    type: MessageType,
    oneOfEnumNames: Map<OneOf, DeclaredTypeName>,
    forStorageType: Boolean = false,
  ) {
    type.fields.forEach { field ->
      val property = PropertySpec.varBuilder(field.name, field.typeName, PUBLIC)
      if (!forStorageType && field.documentation.isNotBlank()) {
        property.addDoc("%L\n", field.documentation.sanitizeDoc())
      }
      if (!forStorageType && field.isDeprecated) {
        property.addAttribute(deprecated)
      }
      if (isIndirect(type, field)) {
        property.addAttribute(AttributeSpec.builder(indirect).build())
      }
      val default = field.default
      if (default != null) {
        val defaultValue = defaultFieldInitializer(field.type!!, default)
        property.addAttribute(AttributeSpec.builder(defaulted).addArgument("defaultValue: $defaultValue").build())
      }

      if (field.isMap) {
        property.initializer("[:]")
      } else if (field.isRepeated) {
        property.initializer("[]")
      }

      addProperty(property.build())
    }

    type.oneOfs.forEach { oneOf ->
      val enumName = oneOfEnumNames.getValue(oneOf)

      addProperty(
        PropertySpec.varBuilder(oneOf.name, enumName.makeOptional(), PUBLIC)
          .apply {
            if (oneOf.documentation.isNotBlank()) {
              addDoc("%N\n", oneOf.documentation.sanitizeDoc())
            }
          }
          .build(),
      )
    }

    addProperty(
      PropertySpec.varBuilder("unknownFields", FOUNDATION_DATA, PUBLIC)
        .initializer(".init()")
        .build(),
    )
  }

  private fun defaultFieldInitializer(
    protoType: ProtoType,
    defaultValue: Any,
  ): CodeBlock {
    val typeName = protoType.typeName
    return when {
      typeName == BOOL -> CodeBlock.of("%L", defaultValue)
      typeName == INT -> defaultValue.toIntFieldInitializer()
      typeName == INT32 -> defaultValue.toInt32FieldInitializer()
      typeName == INT64 -> defaultValue.toInt64FieldInitializer()
      typeName == UINT32 -> defaultValue.toUInt32FieldInitializer()
      typeName == UINT64 -> defaultValue.toUInt64FieldInitializer()
      typeName == FLOAT -> defaultValue.toFloatFieldInitializer()
      typeName == DOUBLE -> defaultValue.toDoubleFieldInitializer()
      typeName == STRING -> CodeBlock.of("%S", stringLiteralWithQuotes2(defaultValue.toString()))
      typeName == DATA -> CodeBlock.of(
        "Foundation.Data(base64Encoded: %S)!",
        defaultValue.toString().encode(charset = Charsets.ISO_8859_1).base64(),
      )
      protoType.isEnum -> CodeBlock.of("%T.%L", typeName, defaultValue)
      else -> throw IllegalStateException("$protoType is not an allowed scalar type")
    }
  }

  private fun Any.toIntFieldInitializer(): CodeBlock = when (val int = optionValueToInt(this)) {
    Int.MIN_VALUE -> CodeBlock.of("%T.min", INT)
    Int.MAX_VALUE -> CodeBlock.of("%T.max", INT)
    else -> CodeBlock.of("%L", int)
  }

  private fun Any.toInt32FieldInitializer(): CodeBlock = when (val int = optionValueToInt(this)) {
    Int.MIN_VALUE -> CodeBlock.of("%T.min", INT32)
    Int.MAX_VALUE -> CodeBlock.of("%T.max", INT32)
    else -> CodeBlock.of("%L", int)
  }

  private fun Any.toInt64FieldInitializer(): CodeBlock = when (val long = optionValueToLong(this)) {
    Long.MIN_VALUE -> CodeBlock.of("%T.min", INT64)
    Long.MAX_VALUE -> CodeBlock.of("%T.max", INT64)
    else -> CodeBlock.of("%L", long)
  }

  private fun Any.toUInt32FieldInitializer(): CodeBlock = when (val int = optionValueToInt(this)) {
    0 -> CodeBlock.of("%T.min", UINT32)
    -1 -> CodeBlock.of("%T.max", UINT32)
    else -> CodeBlock.of("%L", int)
  }

  private fun Any.toUInt64FieldInitializer(): CodeBlock = when (val long = optionValueToLong(this)) {
    0L -> CodeBlock.of("%T.min", UINT64)
    -1L -> CodeBlock.of("%T.max", UINT64)
    else -> CodeBlock.of("%L", long)
  }

  private fun Any.toFloatFieldInitializer(): CodeBlock = when (this) {
    "inf" -> CodeBlock.of("Float.infinity")
    "-inf" -> CodeBlock.of("-Float.infinity")
    "nan" -> CodeBlock.of("Float.nan")
    "-nan" -> CodeBlock.of("Float.nan * -1")
    else -> CodeBlock.of("%L", this.toString())
  }

  private fun Any.toDoubleFieldInitializer(): CodeBlock = when (this) {
    "inf" -> CodeBlock.of("Double.infinity")
    "-inf" -> CodeBlock.of("-Double.infinity")
    "nan" -> CodeBlock.of("Double.nan")
    "-nan" -> CodeBlock.of("Double.nan * -1")
    else -> CodeBlock.of("%L", this.toString().toDouble())
  }

  private fun TypeSpec.Builder.generateMessageStoragePropertyDelegates(
    type: MessageType,
    storageName: String,
    storageType: DeclaredTypeName,
  ) {
    if (!type.isHeapAllocated) {
      println("Generating storage property delegates for a non-heap allocated type?!")
    }

    val propertyVariable = TypeVariableName.typeVariable("Property")

    val subscript = PropertySpec.subscriptBuilder(
      FunctionSignatureSpec.builder()
        .addTypeVariable(propertyVariable)
        .addParameter(
          "dynamicMember",
          "keyPath",
          writableKeyPath.parameterizedBy(storageType, propertyVariable),
        )
        .returns(propertyVariable)
        .build(),
    )
      .addModifiers(PUBLIC)
      .getter(
        FunctionSpec.getterBuilder()
          .addStatement("%N[keyPath: keyPath]", storageName)
          .build(),
      )
      .setter(
        FunctionSpec.setterBuilder()
          .addStatement("copyStorage()")
          .addStatement("%N[keyPath: keyPath] = newValue", storageName)
          .build(),
      )
      .build()

    addProperty(subscript)
  }

  private fun TypeSpec.Builder.generateMessageOneOfs(
    type: MessageType,
    oneOfEnumNames: Map<OneOf, DeclaredTypeName>,
    fileMembers: MutableList<FileMemberSpec>,
  ) {
    type.oneOfs.forEach { oneOf ->
      val enumName = oneOfEnumNames.getValue(oneOf)

      // TODO use a NameAllocator
      val writer = if (oneOf.fields.any { it.name == "writer" }) "_writer" else "writer"
      addType(
        TypeSpec.enumBuilder(enumName)
          .addModifiers(PUBLIC)
          .apply {
            oneOf.fields.forEach { oneOfField ->
              addEnumCase(
                EnumerationCaseSpec.builder(oneOfField.name, oneOfField.typeName.makeNonOptional())
                  .apply {
                    if (oneOfField.documentation.isNotBlank()) {
                      addDoc("%L\n", oneOfField.documentation.sanitizeDoc())
                    }
                    if (oneOfField.isDeprecated) {
                      addAttribute(deprecated)
                    }
                  }
                  .build(),
              )
            }
          }
          .addFunction(
            FunctionSpec.builder("encode")
              .addParameter("to", writer, protoWriter)
              .addModifiers(FILEPRIVATE)
              .throws(true)
              .beginControlFlow("switch", "self")
              .apply {
                oneOf.fields.forEach { field ->
                  addStatement(
                    "case .%1N(let %1N): try $writer.encode(tag: %2L, value: %1N)",
                    field.name,
                    field.tag,
                  )
                }
              }
              .endControlFlow("switch")
              .build(),
          )
          .build(),
      )

      val equatableExtension = ExtensionSpec.builder(enumName)
        .addSuperType(equatable)
        .build()
      fileMembers += FileMemberSpec.builder(equatableExtension)
        .addGuard("!$FLAG_REMOVE_EQUATABLE")
        .build()

      val hashableExtension = ExtensionSpec.builder(enumName)
        .addSuperType(hashable)
        .build()
      fileMembers += FileMemberSpec.builder(hashableExtension)
        .addGuard("!$FLAG_REMOVE_HASHABLE")
        .build()

      val sendableExtension = ExtensionSpec.builder(enumName)
        .addSuperType(sendable)
        .build()
      fileMembers += FileMemberSpec.builder(sendableExtension)
        .addGuard("swift(>=5.5)")
        .build()

      if (oneOf.fields.any { it.isRedacted }) {
        val redactableExtension = ExtensionSpec.builder(enumName)
          .addSuperType(redactable)
          .addType(
            TypeSpec.enumBuilder("RedactedKeys")
              .addModifiers(PUBLIC)
              .addSuperType(STRING)
              .addSuperType(redactedKey)
              .apply {
                oneOf.fields.forEach { field ->
                  if (field.isRedacted) {
                    addEnumCase(field.name)
                  }
                }
              }
              .build(),
          )
          .build()
        fileMembers += FileMemberSpec.builder(redactableExtension)
          .addGuard("!$FLAG_REMOVE_REDACTABLE")
          .build()
      }
    }
  }

  private val ProtoType.encoding: String?
    get() = when (this) {
      ProtoType.SINT32, ProtoType.SINT64 -> "signed"
      ProtoType.FIXED32, ProtoType.FIXED64 -> "fixed"
      else -> null
    }

  private fun generateEnum(
    type: EnumType,
    fileMembers: MutableList<FileMemberSpec>,
  ): TypeSpec {
    val enumName = type.typeName
    return TypeSpec.enumBuilder(enumName)
      .addModifiers(PUBLIC)
      .addSuperType(UINT32)
      .addSuperType(CASE_ITERABLE)
      .addSuperType(protoEnum)
      .apply {
        val sendableExtension = ExtensionSpec.builder(enumName)
          .addSuperType(sendable)
          .build()
        fileMembers += FileMemberSpec.builder(sendableExtension)
          .addGuard("swift(>=5.5)")
          .build()

        if (type.documentation.isNotBlank()) {
          addDoc("%L\n", type.documentation.sanitizeDoc())
        }
        type.constants.forEach { constant ->
          addEnumCase(
            EnumerationCaseSpec.builder(constant.name, constant.tag)
              .apply {
                if (constant.documentation.isNotBlank()) {
                  addDoc("%L\n", constant.documentation.sanitizeDoc())
                }
                if (constant.isDeprecated) {
                  addAttribute(deprecated)
                }
              }
              .build(),
          )
        }

        addProperty(
          PropertySpec.varBuilder("description", STRING)
            .addModifiers(PUBLIC)
            .getter(
              FunctionSpec.getterBuilder()
                .beginControlFlow("switch", "self")
                .apply {
                  type.constants.forEach { constant ->
                    addStatement("case .%1N: return \"%1N\"", constant.name)
                  }
                }
                .endControlFlow("switch")
                .build(),
            )
            .build(),
        )

        // Swift won't synthesize CaseIterable conformance if any constants contain an availability
        // attribute. https://bugs.swift.org/browse/SR-7151
        if (type.constants.any { it.isDeprecated }) {
          addProperty(
            PropertySpec.varBuilder("allCases", ARRAY.parameterizedBy(enumName))
              .addModifiers(PUBLIC, STATIC)
              .getter(
                FunctionSpec.getterBuilder()
                  .addStatement(
                    "return [%L]",
                    type.constants.map { CodeBlock.of(".%N", it.name) }
                      .joinToCode(",%W"),
                  )
                  .build(),
              )
              .build(),
          )
        }
        type.nestedTypes.forEach { nestedType ->
          generateType(nestedType, fileMembers).forEach {
            addType(it)
          }
        }
      }
      .build()
  }

  private fun generateEnclosing(
    type: EnclosingType,
    fileMembers: MutableList<FileMemberSpec>,
  ): TypeSpec {
    return TypeSpec.enumBuilder(type.typeName)
      .addModifiers(PUBLIC)
      .addDoc(
        "%N\n",
        "*Note:* This type only exists to maintain class structure for its nested types and " +
          "is not an actual message.",
      )
      .apply {
        type.nestedTypes.forEach { nestedType ->
          generateType(nestedType, fileMembers).forEach {
            addType(it)
          }
        }
      }
      .build()
  }

  companion object {
    // Always fully-qualified in case a message type is named `Data`.
    private val FOUNDATION_DATA = DeclaredTypeName.typeName("Foundation.Data", true)

    fun builtInType(protoType: ProtoType): Boolean = protoType in BUILT_IN_TYPES.keys

    private val BUILT_IN_TYPES: Map<out ProtoType, DeclaredTypeName> = mapOf(
      ProtoType.BOOL to BOOL,
      ProtoType.BYTES to FOUNDATION_DATA,
      ProtoType.DOUBLE to DOUBLE,
      ProtoType.FLOAT to FLOAT,
      ProtoType.FIXED32 to UINT32,
      ProtoType.FIXED64 to UINT64,
      ProtoType.INT32 to INT32,
      ProtoType.INT64 to INT64,
      ProtoType.SFIXED32 to INT32,
      ProtoType.SFIXED64 to INT64,
      ProtoType.SINT32 to INT32,
      ProtoType.SINT64 to INT64,
      ProtoType.STRING to STRING,
      ProtoType.UINT32 to UINT32,
      ProtoType.UINT64 to UINT64,
      ProtoType.ANY to DeclaredTypeName.typeName("Wire.AnyMessage"),
//        Options.FIELD_OPTIONS to ClassName("com.google.protobuf", "FieldOptions"),
//        Options.MESSAGE_OPTIONS to ClassName("com.google.protobuf", "MessageOptions"),
//        Options.ENUM_OPTIONS to ClassName("com.google.protobuf", "EnumOptions")
    )

    private const val FLAG_REMOVE_CODABLE = "WIRE_REMOVE_CODABLE"
    private const val FLAG_REMOVE_EQUATABLE = "WIRE_REMOVE_EQUATABLE"
    private const val FLAG_REMOVE_HASHABLE = "WIRE_REMOVE_HASHABLE"
    private const val FLAG_REMOVE_REDACTABLE = "WIRE_REMOVE_REDACTABLE"
    private const val FLAG_INCLUDE_MEMBERWISE_INITIALIZER = "WIRE_INCLUDE_MEMBERWISE_INITIALIZER"

    private val NEEDS_CUSTOM_CODABLE = setOf("Duration", "Timestamp")

    @JvmStatic
    @JvmName("get")
    operator fun invoke(
      schema: Schema,
      existingTypeModuleName: Map<ProtoType, String> = emptyMap(),
    ): SwiftGenerator {
      val nameToTypeName = mutableMapOf<ProtoType, DeclaredTypeName>()

      fun putAll(enclosingClassName: DeclaredTypeName?, types: List<Type>) {
        for (type in types) {
          val protoType = type.type
          val name = protoType.simpleName

          val className = if (enclosingClassName != null) {
            // Temporary work around for https://bugs.swift.org/browse/SR-13160.
            val safeName = if (name == "Type") "Type_" else name

            enclosingClassName.nestedType(safeName, alwaysQualify = true)
          } else {
            val moduleName = existingTypeModuleName[protoType] ?: ""

            DeclaredTypeName(moduleName, name)
          }

          nameToTypeName[protoType] = className
          putAll(className, type.nestedTypes)
        }
      }

      for (protoFile in schema.protoFiles) {
        putAll(null, protoFile.types)

        for (service in protoFile.services) {
          val protoType = service.type
          val name = protoType.simpleName

          val moduleName = existingTypeModuleName[protoType] ?: ""
          val className = DeclaredTypeName(moduleName, name)

          nameToTypeName[protoType] = className
        }
      }

      nameToTypeName.putAll(BUILT_IN_TYPES)

      val referenceCycleIndirections =
        computeReferenceCycleIndirections(schema, existingTypeModuleName.keys)

      return SwiftGenerator(schema, nameToTypeName, referenceCycleIndirections)
    }

    private fun computeReferenceCycleIndirections(
      schema: Schema,
      existingTypes: Set<ProtoType>,
    ): Map<ProtoType, Set<ProtoType>> {
      val indirections = mutableMapOf<ProtoType, MutableSet<ProtoType>>()

      var nodes = schema.protoFiles
        .flatMap { it.typesAndNestedTypes() }
        .map { it.type }
        // Ignore types which were already generated. We cannot form a cycle with them.
        .filter { it !in existingTypes }
        .toSet()
      while (true) {
        val dagChecker = DagChecker(nodes) { protoType ->
          when (val type = schema.getType(protoType)!!) {
            is MessageType -> {
              type.fieldsAndOneOfFields.map { it.type!! }
                // Remove edges known to need an indirection to break an already-seen cycle.
                .filter { it !in (indirections[protoType] ?: emptySet<ProtoType>()) }
            }
            is EnumType -> emptyList()
            is EnclosingType -> emptyList()
            else -> throw IllegalArgumentException("Unknown type: $protoType")
          }
        }

        val cycles = dagChecker.check()
        if (cycles.isEmpty()) {
          break
        }
        // Break the first edge in each cycle with an indirection.
        for (cycle in cycles) {
          val other = if (cycle.size > 1) cycle[1] else cycle[0]
          indirections.getOrPut(cycle[0], ::LinkedHashSet) += other
        }

        // We need to ensure that we've successfully broken all of the reported cycles. Feed the set
        // of nodes which were present in a cycle back into the check.
        nodes = cycles.flatten().toSet()
      }

      return indirections
    }
  }

  //region Temporary SwiftPoet bug fix see: https://github.com/outfoxx/swiftpoet/pull/86

  private val Char.isIsoControl: Boolean
    get() {
      return this in '\u0000'..'\u001F' || this in '\u007F'..'\u009F'
    }

  // see https://docs.oracle.com/javase/specs/jls/se7/html/jls-3.html#jls-3.10.6
  private fun characterLiteralWithoutSingleQuotes(c: Char) = when {
    c == '\b' -> "\\b" // \u0008: backspace (BS)
    c == '\t' -> "\\t" // \u0009: horizontal tab (HT)
    c == '\n' -> "\\n" // \u000a: linefeed (LF)
    c == '\r' -> "\\r" // \u000d: carriage return (CR)
    c == '\"' -> "\"" // \u0022: double quote (")
    c == '\'' -> "\\'" // \u0027: single quote (')
    c == '\\' -> "\\\\" // \u005c: backslash (\)
    c.isIsoControl -> String.format("\\u%04x", c.code)
    else -> Character.toString(c)
  }

  private fun stringLiteralWithQuotes2(value: String): String {
    val result = StringBuilder(value.length + 32)
    result.append('"')
    for (i in 0 until value.length) {
      val c = value[i]
      // Trivial case: single quote must not be escaped.
      if (c == '\'') {
        result.append("'")
        continue
      }
      // Trivial case: double quotes must be escaped.
      if (c == '\"') {
        result.append("\\\"")
        continue
      }
      // Default case: just let character literal do its work.
      result.append(characterLiteralWithoutSingleQuotes(c))
      // Need to append indent after linefeed?
    }
    result.append('"')
    return result.toString()
  }

  //endregion
}
