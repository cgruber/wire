/*
 * Copyright (C) 2013 Square, Inc.
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
package com.squareup.wire.schema.internal.parser

import com.squareup.wire.schema.internal.appendIndented
import com.squareup.wire.schema.internal.appendOptions
import com.squareup.wire.schema.internal.parser.OptionElement.Kind.BOOLEAN
import com.squareup.wire.schema.internal.parser.OptionElement.Kind.ENUM
import com.squareup.wire.schema.internal.parser.OptionElement.Kind.LIST
import com.squareup.wire.schema.internal.parser.OptionElement.Kind.MAP
import com.squareup.wire.schema.internal.parser.OptionElement.Kind.NUMBER
import com.squareup.wire.schema.internal.parser.OptionElement.Kind.OPTION
import com.squareup.wire.schema.internal.parser.OptionElement.Kind.STRING
import kotlin.jvm.JvmOverloads

data class OptionElement(
  val name: String,
  val kind: Kind,
  val value: Any,
  /** If true, this [OptionElement] is a custom option. */
  val isParenthesized: Boolean,
) {
  enum class Kind {
    STRING,
    BOOLEAN,
    NUMBER,
    ENUM,
    MAP,
    LIST,
    OPTION,
  }

  /** An internal representation of the Option primitive types. */
  data class OptionPrimitive(val kind: Kind, val value: Any)

  private val formattedName = if (isParenthesized) "($name)" else name

  fun toSchema(): String = buildString {
    when (kind) {
      STRING -> append("""$formattedName = "$value"""")
      BOOLEAN,
      NUMBER,
      ENUM,
      -> append("$formattedName = $value")
      OPTION -> {
        // Treat nested options as non-parenthesized always, prevents double parentheses.
        val optionValue = (value as OptionElement).copy()
        append("$formattedName.${optionValue.toSchema()}")
      }
      MAP -> {
        append("$formattedName = {\n")
        @Suppress("UNCHECKED_CAST")
        formatOptionMap(this, value as Map<String, *>)
        append('}')
      }
      LIST -> {
        append("$formattedName = ")
        @Suppress("UNCHECKED_CAST")
        appendOptions(value as List<OptionElement>)
      }
    }
  }

  fun toSchemaDeclaration() = "option ${toSchema()};\n"

  private fun formatOptionMap(
    builder: StringBuilder,
    valueMap: Map<String, *>,
  ) {
    val lastIndex = valueMap.size - 1
    valueMap.entries.forEachIndexed { index, entry ->
      val endl = if (index != lastIndex) "," else ""
      builder.appendIndented("${entry.key}: ${formatOptionMapValue(entry.value!!)}$endl")
    }
  }

  private fun formatOptionMapValue(value: Any): String = buildString {
    when (value) {
      is String -> {
        append(""""$value"""")
      }
      is OptionPrimitive -> {
        when (value.kind) {
          BOOLEAN,
          NUMBER,
          ENUM,
          -> {
            append("${value.value}")
          }
          else -> append(formatOptionMapValue(value.value))
        }
      }
      is Map<*, *> -> {
        append("{\n")
        @Suppress("UNCHECKED_CAST")
        formatOptionMap(this, value as Map<String, *>)
        append('}')
      }
      is List<*> -> {
        append("[\n")
        val lastIndex = value.size - 1
        value.forEachIndexed { index, item ->
          val endl = if (index != lastIndex) "," else ""
          appendIndented("${formatOptionMapValue(item!!)}$endl")
        }
        append("]")
      }
      else -> {
        append(value)
      }
    }
  }

  companion object {
    internal val PACKED_OPTION_ELEMENT =
      OptionElement("packed", BOOLEAN, value = "true", isParenthesized = false)

    internal val USE_ARRAY_OPTION_ELEMENT =
      OptionElement("wire.use_array", BOOLEAN, value = "true", isParenthesized = false)

    @JvmOverloads
    fun create(
      name: String,
      kind: Kind,
      value: Any,
      isParenthesized: Boolean = false,
    ) = OptionElement(name, kind, value, isParenthesized)
  }
}
