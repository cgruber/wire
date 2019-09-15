// Code generated by Wire protocol buffer compiler, do not edit.
// Source file: redacted_test.proto
package com.squareup.wire.protos.kotlin.redacted

import com.squareup.wire.FieldEncoding
import com.squareup.wire.Message
import com.squareup.wire.ProtoAdapter
import com.squareup.wire.ProtoReader
import com.squareup.wire.ProtoWriter
import com.squareup.wire.WireField
import kotlin.Any
import kotlin.AssertionError
import kotlin.Boolean
import kotlin.Deprecated
import kotlin.DeprecationLevel
import kotlin.Int
import kotlin.Nothing
import kotlin.String
import kotlin.hashCode
import kotlin.jvm.JvmField
import okio.ByteString

class RedactedCycleA(
  @field:WireField(
    tag = 1,
    adapter = "com.squareup.wire.protos.kotlin.redacted.RedactedCycleB#ADAPTER"
  )
  val b: RedactedCycleB? = null,
  unknownFields: ByteString = ByteString.EMPTY
) : Message<RedactedCycleA, Nothing>(ADAPTER, unknownFields) {
  @Deprecated(
    message = "Shouldn't be used in Kotlin",
    level = DeprecationLevel.HIDDEN
  )
  override fun newBuilder(): Nothing {
    throw AssertionError()
  }

  override fun equals(other: Any?): Boolean {
    if (other === this) return true
    if (other !is RedactedCycleA) return false
    return unknownFields == other.unknownFields
        && b == other.b
  }

  override fun hashCode(): Int {
    var result = super.hashCode
    if (result == 0) {
      result = unknownFields.hashCode()
      result = result * 37 + b.hashCode()
      super.hashCode = result
    }
    return result
  }

  override fun toString(): String {
    val result = mutableListOf<String>()
    if (b != null) result += """b=$b"""
    return result.joinToString(prefix = "RedactedCycleA{", separator = ", ", postfix = "}")
  }

  fun copy(b: RedactedCycleB? = this.b, unknownFields: ByteString = this.unknownFields):
      RedactedCycleA = RedactedCycleA(b, unknownFields)

  companion object {
    @JvmField
    val ADAPTER: ProtoAdapter<RedactedCycleA> = object : ProtoAdapter<RedactedCycleA>(
      FieldEncoding.LENGTH_DELIMITED, 
      RedactedCycleA::class
    ) {
      override fun encodedSize(value: RedactedCycleA): Int = 
        RedactedCycleB.ADAPTER.encodedSizeWithTag(1, value.b) +
        value.unknownFields.size

      override fun encode(writer: ProtoWriter, value: RedactedCycleA) {
        RedactedCycleB.ADAPTER.encodeWithTag(writer, 1, value.b)
        writer.writeBytes(value.unknownFields)
      }

      override fun decode(reader: ProtoReader): RedactedCycleA {
        var b: RedactedCycleB? = null
        val unknownFields = reader.forEachTag { tag ->
          when (tag) {
            1 -> b = RedactedCycleB.ADAPTER.decode(reader)
            else -> reader.readUnknownField(tag)
          }
        }
        return RedactedCycleA(
          b = b,
          unknownFields = unknownFields
        )
      }

      override fun redact(value: RedactedCycleA): RedactedCycleA = value.copy(
        b = value.b?.let(RedactedCycleB.ADAPTER::redact),
        unknownFields = ByteString.EMPTY
      )
    }
  }
}
