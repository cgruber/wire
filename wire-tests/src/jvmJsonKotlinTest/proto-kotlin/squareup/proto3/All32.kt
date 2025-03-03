// Code generated by Wire protocol buffer compiler, do not edit.
// Source: squareup.proto3.All32 in all32.proto
package squareup.proto3

import com.squareup.wire.FieldEncoding
import com.squareup.wire.Message
import com.squareup.wire.ProtoAdapter
import com.squareup.wire.ProtoReader
import com.squareup.wire.ProtoWriter
import com.squareup.wire.ReverseProtoWriter
import com.squareup.wire.Syntax.PROTO_3
import com.squareup.wire.WireField
import com.squareup.wire.`internal`.JvmField
import com.squareup.wire.`internal`.checkElementsNotNull
import com.squareup.wire.`internal`.countNonNull
import com.squareup.wire.`internal`.immutableCopyOf
import kotlin.Any
import kotlin.Boolean
import kotlin.Int
import kotlin.Long
import kotlin.String
import kotlin.collections.List
import kotlin.collections.Map
import kotlin.lazy
import okio.ByteString

public class All32(
  /**
   * Prefixing so the generated code doesn't rename it weirdly.
   */
  @field:WireField(
    tag = 1,
    adapter = "com.squareup.wire.ProtoAdapter#INT32",
    label = WireField.Label.OMIT_IDENTITY,
    jsonName = "myInt32",
    schemaIndex = 0,
  )
  @JvmField
  public val my_int32: Int = 0,
  @field:WireField(
    tag = 2,
    adapter = "com.squareup.wire.ProtoAdapter#UINT32",
    label = WireField.Label.OMIT_IDENTITY,
    jsonName = "myUint32",
    schemaIndex = 1,
  )
  @JvmField
  public val my_uint32: Int = 0,
  @field:WireField(
    tag = 3,
    adapter = "com.squareup.wire.ProtoAdapter#SINT32",
    label = WireField.Label.OMIT_IDENTITY,
    jsonName = "mySint32",
    schemaIndex = 2,
  )
  @JvmField
  public val my_sint32: Int = 0,
  @field:WireField(
    tag = 4,
    adapter = "com.squareup.wire.ProtoAdapter#FIXED32",
    label = WireField.Label.OMIT_IDENTITY,
    jsonName = "myFixed32",
    schemaIndex = 3,
  )
  @JvmField
  public val my_fixed32: Int = 0,
  @field:WireField(
    tag = 5,
    adapter = "com.squareup.wire.ProtoAdapter#SFIXED32",
    label = WireField.Label.OMIT_IDENTITY,
    jsonName = "mySfixed32",
    schemaIndex = 4,
  )
  @JvmField
  public val my_sfixed32: Int = 0,
  rep_int32: List<Int> = emptyList(),
  rep_uint32: List<Int> = emptyList(),
  rep_sint32: List<Int> = emptyList(),
  rep_fixed32: List<Int> = emptyList(),
  rep_sfixed32: List<Int> = emptyList(),
  pack_int32: List<Int> = emptyList(),
  pack_uint32: List<Int> = emptyList(),
  pack_sint32: List<Int> = emptyList(),
  pack_fixed32: List<Int> = emptyList(),
  pack_sfixed32: List<Int> = emptyList(),
  @field:WireField(
    tag = 401,
    adapter = "com.squareup.wire.ProtoAdapter#INT32",
    jsonName = "oneofInt32",
    oneofName = "choice",
    schemaIndex = 15,
  )
  @JvmField
  public val oneof_int32: Int? = null,
  @field:WireField(
    tag = 402,
    adapter = "com.squareup.wire.ProtoAdapter#SFIXED32",
    jsonName = "oneofSfixed32",
    oneofName = "choice",
    schemaIndex = 16,
  )
  @JvmField
  public val oneof_sfixed32: Int? = null,
  map_int32_int32: Map<Int, Int> = emptyMap(),
  map_int32_uint32: Map<Int, Int> = emptyMap(),
  map_int32_sint32: Map<Int, Int> = emptyMap(),
  map_int32_fixed32: Map<Int, Int> = emptyMap(),
  map_int32_sfixed32: Map<Int, Int> = emptyMap(),
  unknownFields: ByteString = ByteString.EMPTY,
) : Message<All32, All32.Builder>(ADAPTER, unknownFields) {
  @field:WireField(
    tag = 201,
    adapter = "com.squareup.wire.ProtoAdapter#INT32",
    label = WireField.Label.REPEATED,
    jsonName = "repInt32",
    schemaIndex = 5,
  )
  @JvmField
  public val rep_int32: List<Int> = immutableCopyOf("rep_int32", rep_int32)

  @field:WireField(
    tag = 202,
    adapter = "com.squareup.wire.ProtoAdapter#UINT32",
    label = WireField.Label.REPEATED,
    jsonName = "repUint32",
    schemaIndex = 6,
  )
  @JvmField
  public val rep_uint32: List<Int> = immutableCopyOf("rep_uint32", rep_uint32)

  @field:WireField(
    tag = 203,
    adapter = "com.squareup.wire.ProtoAdapter#SINT32",
    label = WireField.Label.REPEATED,
    jsonName = "repSint32",
    schemaIndex = 7,
  )
  @JvmField
  public val rep_sint32: List<Int> = immutableCopyOf("rep_sint32", rep_sint32)

  @field:WireField(
    tag = 204,
    adapter = "com.squareup.wire.ProtoAdapter#FIXED32",
    label = WireField.Label.REPEATED,
    jsonName = "repFixed32",
    schemaIndex = 8,
  )
  @JvmField
  public val rep_fixed32: List<Int> = immutableCopyOf("rep_fixed32", rep_fixed32)

  @field:WireField(
    tag = 205,
    adapter = "com.squareup.wire.ProtoAdapter#SFIXED32",
    label = WireField.Label.REPEATED,
    jsonName = "repSfixed32",
    schemaIndex = 9,
  )
  @JvmField
  public val rep_sfixed32: List<Int> = immutableCopyOf("rep_sfixed32", rep_sfixed32)

  @field:WireField(
    tag = 301,
    adapter = "com.squareup.wire.ProtoAdapter#INT32",
    label = WireField.Label.PACKED,
    jsonName = "packInt32",
    schemaIndex = 10,
  )
  @JvmField
  public val pack_int32: List<Int> = immutableCopyOf("pack_int32", pack_int32)

  @field:WireField(
    tag = 302,
    adapter = "com.squareup.wire.ProtoAdapter#UINT32",
    label = WireField.Label.PACKED,
    jsonName = "packUint32",
    schemaIndex = 11,
  )
  @JvmField
  public val pack_uint32: List<Int> = immutableCopyOf("pack_uint32", pack_uint32)

  @field:WireField(
    tag = 303,
    adapter = "com.squareup.wire.ProtoAdapter#SINT32",
    label = WireField.Label.PACKED,
    jsonName = "packSint32",
    schemaIndex = 12,
  )
  @JvmField
  public val pack_sint32: List<Int> = immutableCopyOf("pack_sint32", pack_sint32)

  @field:WireField(
    tag = 304,
    adapter = "com.squareup.wire.ProtoAdapter#FIXED32",
    label = WireField.Label.PACKED,
    jsonName = "packFixed32",
    schemaIndex = 13,
  )
  @JvmField
  public val pack_fixed32: List<Int> = immutableCopyOf("pack_fixed32", pack_fixed32)

  @field:WireField(
    tag = 305,
    adapter = "com.squareup.wire.ProtoAdapter#SFIXED32",
    label = WireField.Label.PACKED,
    jsonName = "packSfixed32",
    schemaIndex = 14,
  )
  @JvmField
  public val pack_sfixed32: List<Int> = immutableCopyOf("pack_sfixed32", pack_sfixed32)

  @field:WireField(
    tag = 501,
    keyAdapter = "com.squareup.wire.ProtoAdapter#INT32",
    adapter = "com.squareup.wire.ProtoAdapter#INT32",
    jsonName = "mapInt32Int32",
    schemaIndex = 17,
  )
  @JvmField
  public val map_int32_int32: Map<Int, Int> = immutableCopyOf("map_int32_int32", map_int32_int32)

  @field:WireField(
    tag = 502,
    keyAdapter = "com.squareup.wire.ProtoAdapter#INT32",
    adapter = "com.squareup.wire.ProtoAdapter#UINT32",
    jsonName = "mapInt32Uint32",
    schemaIndex = 18,
  )
  @JvmField
  public val map_int32_uint32: Map<Int, Int> = immutableCopyOf("map_int32_uint32", map_int32_uint32)

  @field:WireField(
    tag = 503,
    keyAdapter = "com.squareup.wire.ProtoAdapter#INT32",
    adapter = "com.squareup.wire.ProtoAdapter#SINT32",
    jsonName = "mapInt32Sint32",
    schemaIndex = 19,
  )
  @JvmField
  public val map_int32_sint32: Map<Int, Int> = immutableCopyOf("map_int32_sint32", map_int32_sint32)

  @field:WireField(
    tag = 504,
    keyAdapter = "com.squareup.wire.ProtoAdapter#INT32",
    adapter = "com.squareup.wire.ProtoAdapter#FIXED32",
    jsonName = "mapInt32Fixed32",
    schemaIndex = 20,
  )
  @JvmField
  public val map_int32_fixed32: Map<Int, Int> = immutableCopyOf("map_int32_fixed32",
      map_int32_fixed32)

  @field:WireField(
    tag = 505,
    keyAdapter = "com.squareup.wire.ProtoAdapter#INT32",
    adapter = "com.squareup.wire.ProtoAdapter#SFIXED32",
    jsonName = "mapInt32Sfixed32",
    schemaIndex = 21,
  )
  @JvmField
  public val map_int32_sfixed32: Map<Int, Int> = immutableCopyOf("map_int32_sfixed32",
      map_int32_sfixed32)

  init {
    require(countNonNull(oneof_int32, oneof_sfixed32) <= 1) {
      "At most one of oneof_int32, oneof_sfixed32 may be non-null"
    }
  }

  override fun newBuilder(): Builder {
    val builder = Builder()
    builder.my_int32 = my_int32
    builder.my_uint32 = my_uint32
    builder.my_sint32 = my_sint32
    builder.my_fixed32 = my_fixed32
    builder.my_sfixed32 = my_sfixed32
    builder.rep_int32 = rep_int32
    builder.rep_uint32 = rep_uint32
    builder.rep_sint32 = rep_sint32
    builder.rep_fixed32 = rep_fixed32
    builder.rep_sfixed32 = rep_sfixed32
    builder.pack_int32 = pack_int32
    builder.pack_uint32 = pack_uint32
    builder.pack_sint32 = pack_sint32
    builder.pack_fixed32 = pack_fixed32
    builder.pack_sfixed32 = pack_sfixed32
    builder.oneof_int32 = oneof_int32
    builder.oneof_sfixed32 = oneof_sfixed32
    builder.map_int32_int32 = map_int32_int32
    builder.map_int32_uint32 = map_int32_uint32
    builder.map_int32_sint32 = map_int32_sint32
    builder.map_int32_fixed32 = map_int32_fixed32
    builder.map_int32_sfixed32 = map_int32_sfixed32
    builder.addUnknownFields(unknownFields)
    return builder
  }

  override fun equals(other: Any?): Boolean {
    if (other === this) return true
    if (other !is All32) return false
    if (unknownFields != other.unknownFields) return false
    if (my_int32 != other.my_int32) return false
    if (my_uint32 != other.my_uint32) return false
    if (my_sint32 != other.my_sint32) return false
    if (my_fixed32 != other.my_fixed32) return false
    if (my_sfixed32 != other.my_sfixed32) return false
    if (rep_int32 != other.rep_int32) return false
    if (rep_uint32 != other.rep_uint32) return false
    if (rep_sint32 != other.rep_sint32) return false
    if (rep_fixed32 != other.rep_fixed32) return false
    if (rep_sfixed32 != other.rep_sfixed32) return false
    if (pack_int32 != other.pack_int32) return false
    if (pack_uint32 != other.pack_uint32) return false
    if (pack_sint32 != other.pack_sint32) return false
    if (pack_fixed32 != other.pack_fixed32) return false
    if (pack_sfixed32 != other.pack_sfixed32) return false
    if (oneof_int32 != other.oneof_int32) return false
    if (oneof_sfixed32 != other.oneof_sfixed32) return false
    if (map_int32_int32 != other.map_int32_int32) return false
    if (map_int32_uint32 != other.map_int32_uint32) return false
    if (map_int32_sint32 != other.map_int32_sint32) return false
    if (map_int32_fixed32 != other.map_int32_fixed32) return false
    if (map_int32_sfixed32 != other.map_int32_sfixed32) return false
    return true
  }

  override fun hashCode(): Int {
    var result = super.hashCode
    if (result == 0) {
      result = unknownFields.hashCode()
      result = result * 37 + my_int32.hashCode()
      result = result * 37 + my_uint32.hashCode()
      result = result * 37 + my_sint32.hashCode()
      result = result * 37 + my_fixed32.hashCode()
      result = result * 37 + my_sfixed32.hashCode()
      result = result * 37 + rep_int32.hashCode()
      result = result * 37 + rep_uint32.hashCode()
      result = result * 37 + rep_sint32.hashCode()
      result = result * 37 + rep_fixed32.hashCode()
      result = result * 37 + rep_sfixed32.hashCode()
      result = result * 37 + pack_int32.hashCode()
      result = result * 37 + pack_uint32.hashCode()
      result = result * 37 + pack_sint32.hashCode()
      result = result * 37 + pack_fixed32.hashCode()
      result = result * 37 + pack_sfixed32.hashCode()
      result = result * 37 + (oneof_int32?.hashCode() ?: 0)
      result = result * 37 + (oneof_sfixed32?.hashCode() ?: 0)
      result = result * 37 + map_int32_int32.hashCode()
      result = result * 37 + map_int32_uint32.hashCode()
      result = result * 37 + map_int32_sint32.hashCode()
      result = result * 37 + map_int32_fixed32.hashCode()
      result = result * 37 + map_int32_sfixed32.hashCode()
      super.hashCode = result
    }
    return result
  }

  override fun toString(): String {
    val result = mutableListOf<String>()
    result += """my_int32=$my_int32"""
    result += """my_uint32=$my_uint32"""
    result += """my_sint32=$my_sint32"""
    result += """my_fixed32=$my_fixed32"""
    result += """my_sfixed32=$my_sfixed32"""
    if (rep_int32.isNotEmpty()) result += """rep_int32=$rep_int32"""
    if (rep_uint32.isNotEmpty()) result += """rep_uint32=$rep_uint32"""
    if (rep_sint32.isNotEmpty()) result += """rep_sint32=$rep_sint32"""
    if (rep_fixed32.isNotEmpty()) result += """rep_fixed32=$rep_fixed32"""
    if (rep_sfixed32.isNotEmpty()) result += """rep_sfixed32=$rep_sfixed32"""
    if (pack_int32.isNotEmpty()) result += """pack_int32=$pack_int32"""
    if (pack_uint32.isNotEmpty()) result += """pack_uint32=$pack_uint32"""
    if (pack_sint32.isNotEmpty()) result += """pack_sint32=$pack_sint32"""
    if (pack_fixed32.isNotEmpty()) result += """pack_fixed32=$pack_fixed32"""
    if (pack_sfixed32.isNotEmpty()) result += """pack_sfixed32=$pack_sfixed32"""
    if (oneof_int32 != null) result += """oneof_int32=$oneof_int32"""
    if (oneof_sfixed32 != null) result += """oneof_sfixed32=$oneof_sfixed32"""
    if (map_int32_int32.isNotEmpty()) result += """map_int32_int32=$map_int32_int32"""
    if (map_int32_uint32.isNotEmpty()) result += """map_int32_uint32=$map_int32_uint32"""
    if (map_int32_sint32.isNotEmpty()) result += """map_int32_sint32=$map_int32_sint32"""
    if (map_int32_fixed32.isNotEmpty()) result += """map_int32_fixed32=$map_int32_fixed32"""
    if (map_int32_sfixed32.isNotEmpty()) result += """map_int32_sfixed32=$map_int32_sfixed32"""
    return result.joinToString(prefix = "All32{", separator = ", ", postfix = "}")
  }

  public fun copy(
    my_int32: Int = this.my_int32,
    my_uint32: Int = this.my_uint32,
    my_sint32: Int = this.my_sint32,
    my_fixed32: Int = this.my_fixed32,
    my_sfixed32: Int = this.my_sfixed32,
    rep_int32: List<Int> = this.rep_int32,
    rep_uint32: List<Int> = this.rep_uint32,
    rep_sint32: List<Int> = this.rep_sint32,
    rep_fixed32: List<Int> = this.rep_fixed32,
    rep_sfixed32: List<Int> = this.rep_sfixed32,
    pack_int32: List<Int> = this.pack_int32,
    pack_uint32: List<Int> = this.pack_uint32,
    pack_sint32: List<Int> = this.pack_sint32,
    pack_fixed32: List<Int> = this.pack_fixed32,
    pack_sfixed32: List<Int> = this.pack_sfixed32,
    oneof_int32: Int? = this.oneof_int32,
    oneof_sfixed32: Int? = this.oneof_sfixed32,
    map_int32_int32: Map<Int, Int> = this.map_int32_int32,
    map_int32_uint32: Map<Int, Int> = this.map_int32_uint32,
    map_int32_sint32: Map<Int, Int> = this.map_int32_sint32,
    map_int32_fixed32: Map<Int, Int> = this.map_int32_fixed32,
    map_int32_sfixed32: Map<Int, Int> = this.map_int32_sfixed32,
    unknownFields: ByteString = this.unknownFields,
  ): All32 = All32(my_int32, my_uint32, my_sint32, my_fixed32, my_sfixed32, rep_int32, rep_uint32,
      rep_sint32, rep_fixed32, rep_sfixed32, pack_int32, pack_uint32, pack_sint32, pack_fixed32,
      pack_sfixed32, oneof_int32, oneof_sfixed32, map_int32_int32, map_int32_uint32,
      map_int32_sint32, map_int32_fixed32, map_int32_sfixed32, unknownFields)

  public class Builder : Message.Builder<All32, Builder>() {
    @JvmField
    public var my_int32: Int = 0

    @JvmField
    public var my_uint32: Int = 0

    @JvmField
    public var my_sint32: Int = 0

    @JvmField
    public var my_fixed32: Int = 0

    @JvmField
    public var my_sfixed32: Int = 0

    @JvmField
    public var rep_int32: List<Int> = emptyList()

    @JvmField
    public var rep_uint32: List<Int> = emptyList()

    @JvmField
    public var rep_sint32: List<Int> = emptyList()

    @JvmField
    public var rep_fixed32: List<Int> = emptyList()

    @JvmField
    public var rep_sfixed32: List<Int> = emptyList()

    @JvmField
    public var pack_int32: List<Int> = emptyList()

    @JvmField
    public var pack_uint32: List<Int> = emptyList()

    @JvmField
    public var pack_sint32: List<Int> = emptyList()

    @JvmField
    public var pack_fixed32: List<Int> = emptyList()

    @JvmField
    public var pack_sfixed32: List<Int> = emptyList()

    @JvmField
    public var oneof_int32: Int? = null

    @JvmField
    public var oneof_sfixed32: Int? = null

    @JvmField
    public var map_int32_int32: Map<Int, Int> = emptyMap()

    @JvmField
    public var map_int32_uint32: Map<Int, Int> = emptyMap()

    @JvmField
    public var map_int32_sint32: Map<Int, Int> = emptyMap()

    @JvmField
    public var map_int32_fixed32: Map<Int, Int> = emptyMap()

    @JvmField
    public var map_int32_sfixed32: Map<Int, Int> = emptyMap()

    /**
     * Prefixing so the generated code doesn't rename it weirdly.
     */
    public fun my_int32(my_int32: Int): Builder {
      this.my_int32 = my_int32
      return this
    }

    public fun my_uint32(my_uint32: Int): Builder {
      this.my_uint32 = my_uint32
      return this
    }

    public fun my_sint32(my_sint32: Int): Builder {
      this.my_sint32 = my_sint32
      return this
    }

    public fun my_fixed32(my_fixed32: Int): Builder {
      this.my_fixed32 = my_fixed32
      return this
    }

    public fun my_sfixed32(my_sfixed32: Int): Builder {
      this.my_sfixed32 = my_sfixed32
      return this
    }

    public fun rep_int32(rep_int32: List<Int>): Builder {
      checkElementsNotNull(rep_int32)
      this.rep_int32 = rep_int32
      return this
    }

    public fun rep_uint32(rep_uint32: List<Int>): Builder {
      checkElementsNotNull(rep_uint32)
      this.rep_uint32 = rep_uint32
      return this
    }

    public fun rep_sint32(rep_sint32: List<Int>): Builder {
      checkElementsNotNull(rep_sint32)
      this.rep_sint32 = rep_sint32
      return this
    }

    public fun rep_fixed32(rep_fixed32: List<Int>): Builder {
      checkElementsNotNull(rep_fixed32)
      this.rep_fixed32 = rep_fixed32
      return this
    }

    public fun rep_sfixed32(rep_sfixed32: List<Int>): Builder {
      checkElementsNotNull(rep_sfixed32)
      this.rep_sfixed32 = rep_sfixed32
      return this
    }

    public fun pack_int32(pack_int32: List<Int>): Builder {
      checkElementsNotNull(pack_int32)
      this.pack_int32 = pack_int32
      return this
    }

    public fun pack_uint32(pack_uint32: List<Int>): Builder {
      checkElementsNotNull(pack_uint32)
      this.pack_uint32 = pack_uint32
      return this
    }

    public fun pack_sint32(pack_sint32: List<Int>): Builder {
      checkElementsNotNull(pack_sint32)
      this.pack_sint32 = pack_sint32
      return this
    }

    public fun pack_fixed32(pack_fixed32: List<Int>): Builder {
      checkElementsNotNull(pack_fixed32)
      this.pack_fixed32 = pack_fixed32
      return this
    }

    public fun pack_sfixed32(pack_sfixed32: List<Int>): Builder {
      checkElementsNotNull(pack_sfixed32)
      this.pack_sfixed32 = pack_sfixed32
      return this
    }

    public fun map_int32_int32(map_int32_int32: Map<Int, Int>): Builder {
      this.map_int32_int32 = map_int32_int32
      return this
    }

    public fun map_int32_uint32(map_int32_uint32: Map<Int, Int>): Builder {
      this.map_int32_uint32 = map_int32_uint32
      return this
    }

    public fun map_int32_sint32(map_int32_sint32: Map<Int, Int>): Builder {
      this.map_int32_sint32 = map_int32_sint32
      return this
    }

    public fun map_int32_fixed32(map_int32_fixed32: Map<Int, Int>): Builder {
      this.map_int32_fixed32 = map_int32_fixed32
      return this
    }

    public fun map_int32_sfixed32(map_int32_sfixed32: Map<Int, Int>): Builder {
      this.map_int32_sfixed32 = map_int32_sfixed32
      return this
    }

    public fun oneof_int32(oneof_int32: Int?): Builder {
      this.oneof_int32 = oneof_int32
      this.oneof_sfixed32 = null
      return this
    }

    public fun oneof_sfixed32(oneof_sfixed32: Int?): Builder {
      this.oneof_sfixed32 = oneof_sfixed32
      this.oneof_int32 = null
      return this
    }

    override fun build(): All32 = All32(
      my_int32 = my_int32,
      my_uint32 = my_uint32,
      my_sint32 = my_sint32,
      my_fixed32 = my_fixed32,
      my_sfixed32 = my_sfixed32,
      rep_int32 = rep_int32,
      rep_uint32 = rep_uint32,
      rep_sint32 = rep_sint32,
      rep_fixed32 = rep_fixed32,
      rep_sfixed32 = rep_sfixed32,
      pack_int32 = pack_int32,
      pack_uint32 = pack_uint32,
      pack_sint32 = pack_sint32,
      pack_fixed32 = pack_fixed32,
      pack_sfixed32 = pack_sfixed32,
      oneof_int32 = oneof_int32,
      oneof_sfixed32 = oneof_sfixed32,
      map_int32_int32 = map_int32_int32,
      map_int32_uint32 = map_int32_uint32,
      map_int32_sint32 = map_int32_sint32,
      map_int32_fixed32 = map_int32_fixed32,
      map_int32_sfixed32 = map_int32_sfixed32,
      unknownFields = buildUnknownFields()
    )
  }

  public companion object {
    @JvmField
    public val ADAPTER: ProtoAdapter<All32> = object : ProtoAdapter<All32>(
      FieldEncoding.LENGTH_DELIMITED, 
      All32::class, 
      "type.googleapis.com/squareup.proto3.All32", 
      PROTO_3, 
      null, 
      "all32.proto"
    ) {
      private val map_int32_int32Adapter: ProtoAdapter<Map<Int, Int>> by lazy {
          ProtoAdapter.newMapAdapter(ProtoAdapter.INT32, ProtoAdapter.INT32) }

      private val map_int32_uint32Adapter: ProtoAdapter<Map<Int, Int>> by lazy {
          ProtoAdapter.newMapAdapter(ProtoAdapter.INT32, ProtoAdapter.UINT32) }

      private val map_int32_sint32Adapter: ProtoAdapter<Map<Int, Int>> by lazy {
          ProtoAdapter.newMapAdapter(ProtoAdapter.INT32, ProtoAdapter.SINT32) }

      private val map_int32_fixed32Adapter: ProtoAdapter<Map<Int, Int>> by lazy {
          ProtoAdapter.newMapAdapter(ProtoAdapter.INT32, ProtoAdapter.FIXED32) }

      private val map_int32_sfixed32Adapter: ProtoAdapter<Map<Int, Int>> by lazy {
          ProtoAdapter.newMapAdapter(ProtoAdapter.INT32, ProtoAdapter.SFIXED32) }

      override fun encodedSize(`value`: All32): Int {
        var size = value.unknownFields.size
        if (value.my_int32 != 0) size += ProtoAdapter.INT32.encodedSizeWithTag(1, value.my_int32)
        if (value.my_uint32 != 0) size += ProtoAdapter.UINT32.encodedSizeWithTag(2, value.my_uint32)
        if (value.my_sint32 != 0) size += ProtoAdapter.SINT32.encodedSizeWithTag(3, value.my_sint32)
        if (value.my_fixed32 != 0) size += ProtoAdapter.FIXED32.encodedSizeWithTag(4,
            value.my_fixed32)
        if (value.my_sfixed32 != 0) size += ProtoAdapter.SFIXED32.encodedSizeWithTag(5,
            value.my_sfixed32)
        size += ProtoAdapter.INT32.asRepeated().encodedSizeWithTag(201, value.rep_int32)
        size += ProtoAdapter.UINT32.asRepeated().encodedSizeWithTag(202, value.rep_uint32)
        size += ProtoAdapter.SINT32.asRepeated().encodedSizeWithTag(203, value.rep_sint32)
        size += ProtoAdapter.FIXED32.asRepeated().encodedSizeWithTag(204, value.rep_fixed32)
        size += ProtoAdapter.SFIXED32.asRepeated().encodedSizeWithTag(205, value.rep_sfixed32)
        size += ProtoAdapter.INT32.asPacked().encodedSizeWithTag(301, value.pack_int32)
        size += ProtoAdapter.UINT32.asPacked().encodedSizeWithTag(302, value.pack_uint32)
        size += ProtoAdapter.SINT32.asPacked().encodedSizeWithTag(303, value.pack_sint32)
        size += ProtoAdapter.FIXED32.asPacked().encodedSizeWithTag(304, value.pack_fixed32)
        size += ProtoAdapter.SFIXED32.asPacked().encodedSizeWithTag(305, value.pack_sfixed32)
        size += ProtoAdapter.INT32.encodedSizeWithTag(401, value.oneof_int32)
        size += ProtoAdapter.SFIXED32.encodedSizeWithTag(402, value.oneof_sfixed32)
        size += map_int32_int32Adapter.encodedSizeWithTag(501, value.map_int32_int32)
        size += map_int32_uint32Adapter.encodedSizeWithTag(502, value.map_int32_uint32)
        size += map_int32_sint32Adapter.encodedSizeWithTag(503, value.map_int32_sint32)
        size += map_int32_fixed32Adapter.encodedSizeWithTag(504, value.map_int32_fixed32)
        size += map_int32_sfixed32Adapter.encodedSizeWithTag(505, value.map_int32_sfixed32)
        return size
      }

      override fun encode(writer: ProtoWriter, `value`: All32) {
        if (value.my_int32 != 0) ProtoAdapter.INT32.encodeWithTag(writer, 1, value.my_int32)
        if (value.my_uint32 != 0) ProtoAdapter.UINT32.encodeWithTag(writer, 2, value.my_uint32)
        if (value.my_sint32 != 0) ProtoAdapter.SINT32.encodeWithTag(writer, 3, value.my_sint32)
        if (value.my_fixed32 != 0) ProtoAdapter.FIXED32.encodeWithTag(writer, 4, value.my_fixed32)
        if (value.my_sfixed32 != 0) ProtoAdapter.SFIXED32.encodeWithTag(writer, 5,
            value.my_sfixed32)
        ProtoAdapter.INT32.asRepeated().encodeWithTag(writer, 201, value.rep_int32)
        ProtoAdapter.UINT32.asRepeated().encodeWithTag(writer, 202, value.rep_uint32)
        ProtoAdapter.SINT32.asRepeated().encodeWithTag(writer, 203, value.rep_sint32)
        ProtoAdapter.FIXED32.asRepeated().encodeWithTag(writer, 204, value.rep_fixed32)
        ProtoAdapter.SFIXED32.asRepeated().encodeWithTag(writer, 205, value.rep_sfixed32)
        ProtoAdapter.INT32.asPacked().encodeWithTag(writer, 301, value.pack_int32)
        ProtoAdapter.UINT32.asPacked().encodeWithTag(writer, 302, value.pack_uint32)
        ProtoAdapter.SINT32.asPacked().encodeWithTag(writer, 303, value.pack_sint32)
        ProtoAdapter.FIXED32.asPacked().encodeWithTag(writer, 304, value.pack_fixed32)
        ProtoAdapter.SFIXED32.asPacked().encodeWithTag(writer, 305, value.pack_sfixed32)
        map_int32_int32Adapter.encodeWithTag(writer, 501, value.map_int32_int32)
        map_int32_uint32Adapter.encodeWithTag(writer, 502, value.map_int32_uint32)
        map_int32_sint32Adapter.encodeWithTag(writer, 503, value.map_int32_sint32)
        map_int32_fixed32Adapter.encodeWithTag(writer, 504, value.map_int32_fixed32)
        map_int32_sfixed32Adapter.encodeWithTag(writer, 505, value.map_int32_sfixed32)
        ProtoAdapter.INT32.encodeWithTag(writer, 401, value.oneof_int32)
        ProtoAdapter.SFIXED32.encodeWithTag(writer, 402, value.oneof_sfixed32)
        writer.writeBytes(value.unknownFields)
      }

      override fun encode(writer: ReverseProtoWriter, `value`: All32) {
        writer.writeBytes(value.unknownFields)
        ProtoAdapter.SFIXED32.encodeWithTag(writer, 402, value.oneof_sfixed32)
        ProtoAdapter.INT32.encodeWithTag(writer, 401, value.oneof_int32)
        map_int32_sfixed32Adapter.encodeWithTag(writer, 505, value.map_int32_sfixed32)
        map_int32_fixed32Adapter.encodeWithTag(writer, 504, value.map_int32_fixed32)
        map_int32_sint32Adapter.encodeWithTag(writer, 503, value.map_int32_sint32)
        map_int32_uint32Adapter.encodeWithTag(writer, 502, value.map_int32_uint32)
        map_int32_int32Adapter.encodeWithTag(writer, 501, value.map_int32_int32)
        ProtoAdapter.SFIXED32.asPacked().encodeWithTag(writer, 305, value.pack_sfixed32)
        ProtoAdapter.FIXED32.asPacked().encodeWithTag(writer, 304, value.pack_fixed32)
        ProtoAdapter.SINT32.asPacked().encodeWithTag(writer, 303, value.pack_sint32)
        ProtoAdapter.UINT32.asPacked().encodeWithTag(writer, 302, value.pack_uint32)
        ProtoAdapter.INT32.asPacked().encodeWithTag(writer, 301, value.pack_int32)
        ProtoAdapter.SFIXED32.asRepeated().encodeWithTag(writer, 205, value.rep_sfixed32)
        ProtoAdapter.FIXED32.asRepeated().encodeWithTag(writer, 204, value.rep_fixed32)
        ProtoAdapter.SINT32.asRepeated().encodeWithTag(writer, 203, value.rep_sint32)
        ProtoAdapter.UINT32.asRepeated().encodeWithTag(writer, 202, value.rep_uint32)
        ProtoAdapter.INT32.asRepeated().encodeWithTag(writer, 201, value.rep_int32)
        if (value.my_sfixed32 != 0) ProtoAdapter.SFIXED32.encodeWithTag(writer, 5,
            value.my_sfixed32)
        if (value.my_fixed32 != 0) ProtoAdapter.FIXED32.encodeWithTag(writer, 4, value.my_fixed32)
        if (value.my_sint32 != 0) ProtoAdapter.SINT32.encodeWithTag(writer, 3, value.my_sint32)
        if (value.my_uint32 != 0) ProtoAdapter.UINT32.encodeWithTag(writer, 2, value.my_uint32)
        if (value.my_int32 != 0) ProtoAdapter.INT32.encodeWithTag(writer, 1, value.my_int32)
      }

      override fun decode(reader: ProtoReader): All32 {
        var my_int32: Int = 0
        var my_uint32: Int = 0
        var my_sint32: Int = 0
        var my_fixed32: Int = 0
        var my_sfixed32: Int = 0
        val rep_int32 = mutableListOf<Int>()
        val rep_uint32 = mutableListOf<Int>()
        val rep_sint32 = mutableListOf<Int>()
        val rep_fixed32 = mutableListOf<Int>()
        val rep_sfixed32 = mutableListOf<Int>()
        var pack_int32: MutableList<Int>? = null
        var pack_uint32: MutableList<Int>? = null
        var pack_sint32: MutableList<Int>? = null
        var pack_fixed32: MutableList<Int>? = null
        var pack_sfixed32: MutableList<Int>? = null
        var oneof_int32: Int? = null
        var oneof_sfixed32: Int? = null
        val map_int32_int32 = mutableMapOf<Int, Int>()
        val map_int32_uint32 = mutableMapOf<Int, Int>()
        val map_int32_sint32 = mutableMapOf<Int, Int>()
        val map_int32_fixed32 = mutableMapOf<Int, Int>()
        val map_int32_sfixed32 = mutableMapOf<Int, Int>()
        val unknownFields = reader.forEachTag { tag ->
          when (tag) {
            1 -> my_int32 = ProtoAdapter.INT32.decode(reader)
            2 -> my_uint32 = ProtoAdapter.UINT32.decode(reader)
            3 -> my_sint32 = ProtoAdapter.SINT32.decode(reader)
            4 -> my_fixed32 = ProtoAdapter.FIXED32.decode(reader)
            5 -> my_sfixed32 = ProtoAdapter.SFIXED32.decode(reader)
            201 -> rep_int32.add(ProtoAdapter.INT32.decode(reader))
            202 -> rep_uint32.add(ProtoAdapter.UINT32.decode(reader))
            203 -> rep_sint32.add(ProtoAdapter.SINT32.decode(reader))
            204 -> rep_fixed32.add(ProtoAdapter.FIXED32.decode(reader))
            205 -> rep_sfixed32.add(ProtoAdapter.SFIXED32.decode(reader))
            301 -> {
              if (pack_int32 == null) {
                val minimumByteSize = 1
                val initialCapacity = (reader.nextFieldMinLengthInBytes() / minimumByteSize)
                  .coerceAtMost(Int.MAX_VALUE.toLong())
                  .toInt()
                pack_int32 = ArrayList(initialCapacity)
              }
              pack_int32!!.add(ProtoAdapter.INT32.decode(reader))
            }
            302 -> {
              if (pack_uint32 == null) {
                val minimumByteSize = 1
                val initialCapacity = (reader.nextFieldMinLengthInBytes() / minimumByteSize)
                  .coerceAtMost(Int.MAX_VALUE.toLong())
                  .toInt()
                pack_uint32 = ArrayList(initialCapacity)
              }
              pack_uint32!!.add(ProtoAdapter.UINT32.decode(reader))
            }
            303 -> {
              if (pack_sint32 == null) {
                val minimumByteSize = 1
                val initialCapacity = (reader.nextFieldMinLengthInBytes() / minimumByteSize)
                  .coerceAtMost(Int.MAX_VALUE.toLong())
                  .toInt()
                pack_sint32 = ArrayList(initialCapacity)
              }
              pack_sint32!!.add(ProtoAdapter.SINT32.decode(reader))
            }
            304 -> {
              if (pack_fixed32 == null) {
                val minimumByteSize = 4
                val initialCapacity = (reader.nextFieldMinLengthInBytes() / minimumByteSize)
                  .coerceAtMost(Int.MAX_VALUE.toLong())
                  .toInt()
                pack_fixed32 = ArrayList(initialCapacity)
              }
              pack_fixed32!!.add(ProtoAdapter.FIXED32.decode(reader))
            }
            305 -> {
              if (pack_sfixed32 == null) {
                val minimumByteSize = 4
                val initialCapacity = (reader.nextFieldMinLengthInBytes() / minimumByteSize)
                  .coerceAtMost(Int.MAX_VALUE.toLong())
                  .toInt()
                pack_sfixed32 = ArrayList(initialCapacity)
              }
              pack_sfixed32!!.add(ProtoAdapter.SFIXED32.decode(reader))
            }
            401 -> oneof_int32 = ProtoAdapter.INT32.decode(reader)
            402 -> oneof_sfixed32 = ProtoAdapter.SFIXED32.decode(reader)
            501 -> map_int32_int32.putAll(map_int32_int32Adapter.decode(reader))
            502 -> map_int32_uint32.putAll(map_int32_uint32Adapter.decode(reader))
            503 -> map_int32_sint32.putAll(map_int32_sint32Adapter.decode(reader))
            504 -> map_int32_fixed32.putAll(map_int32_fixed32Adapter.decode(reader))
            505 -> map_int32_sfixed32.putAll(map_int32_sfixed32Adapter.decode(reader))
            else -> reader.readUnknownField(tag)
          }
        }
        return All32(
          my_int32 = my_int32,
          my_uint32 = my_uint32,
          my_sint32 = my_sint32,
          my_fixed32 = my_fixed32,
          my_sfixed32 = my_sfixed32,
          rep_int32 = rep_int32,
          rep_uint32 = rep_uint32,
          rep_sint32 = rep_sint32,
          rep_fixed32 = rep_fixed32,
          rep_sfixed32 = rep_sfixed32,
          pack_int32 = pack_int32 ?: listOf(),
          pack_uint32 = pack_uint32 ?: listOf(),
          pack_sint32 = pack_sint32 ?: listOf(),
          pack_fixed32 = pack_fixed32 ?: listOf(),
          pack_sfixed32 = pack_sfixed32 ?: listOf(),
          oneof_int32 = oneof_int32,
          oneof_sfixed32 = oneof_sfixed32,
          map_int32_int32 = map_int32_int32,
          map_int32_uint32 = map_int32_uint32,
          map_int32_sint32 = map_int32_sint32,
          map_int32_fixed32 = map_int32_fixed32,
          map_int32_sfixed32 = map_int32_sfixed32,
          unknownFields = unknownFields
        )
      }

      override fun redact(`value`: All32): All32 = value.copy(
        unknownFields = ByteString.EMPTY
      )
    }

    private const val serialVersionUID: Long = 0L
  }
}
