// Code generated by Wire protocol buffer compiler, do not edit.
// Source file: form.proto
package com.squareup.wire.protos.kotlin

import com.squareup.wire.FieldEncoding
import com.squareup.wire.Message
import com.squareup.wire.ProtoAdapter
import com.squareup.wire.ProtoReader
import com.squareup.wire.ProtoWriter
import com.squareup.wire.WireField
import com.squareup.wire.internal.countNonNull
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

class Form(
  @field:WireField(
    tag = 1,
    adapter = "com.squareup.wire.protos.kotlin.Form${'$'}ButtonElement#ADAPTER"
  )
  val button_element: ButtonElement? = null,
  @field:WireField(
    tag = 2,
    adapter = "com.squareup.wire.protos.kotlin.Form${'$'}LocalImageElement#ADAPTER"
  )
  val local_image_element: LocalImageElement? = null,
  @field:WireField(
    tag = 3,
    adapter = "com.squareup.wire.protos.kotlin.Form${'$'}RemoteImageElement#ADAPTER"
  )
  val remote_image_element: RemoteImageElement? = null,
  @field:WireField(
    tag = 4,
    adapter = "com.squareup.wire.protos.kotlin.Form${'$'}MoneyElement#ADAPTER"
  )
  val money_element: MoneyElement? = null,
  @field:WireField(
    tag = 5,
    adapter = "com.squareup.wire.protos.kotlin.Form${'$'}SpacerElement#ADAPTER"
  )
  val spacer_element: SpacerElement? = null,
  @field:WireField(
    tag = 6,
    adapter = "com.squareup.wire.protos.kotlin.Form${'$'}TextElement#ADAPTER"
  )
  val text_element: TextElement? = null,
  @field:WireField(
    tag = 7,
    adapter = "com.squareup.wire.protos.kotlin.Form${'$'}CustomizedCardElement#ADAPTER"
  )
  val customized_card_element: CustomizedCardElement? = null,
  @field:WireField(
    tag = 8,
    adapter = "com.squareup.wire.protos.kotlin.Form${'$'}AddressElement#ADAPTER"
  )
  val address_element: AddressElement? = null,
  @field:WireField(
    tag = 9,
    adapter = "com.squareup.wire.protos.kotlin.Form${'$'}TextInputElement#ADAPTER"
  )
  val text_input_element: TextInputElement? = null,
  @field:WireField(
    tag = 10,
    adapter = "com.squareup.wire.protos.kotlin.Form${'$'}OptionPickerElement#ADAPTER"
  )
  val option_picker_element: OptionPickerElement? = null,
  @field:WireField(
    tag = 11,
    adapter = "com.squareup.wire.protos.kotlin.Form${'$'}DetailRowElement#ADAPTER"
  )
  val detail_row_element: DetailRowElement? = null,
  @field:WireField(
    tag = 12,
    adapter = "com.squareup.wire.protos.kotlin.Form${'$'}CurrencyConversionFlagsElement#ADAPTER"
  )
  val currency_conversion_flags_element: CurrencyConversionFlagsElement? = null,
  unknownFields: ByteString = ByteString.EMPTY
) : Message<Form, Nothing>(ADAPTER, unknownFields) {
  init {
    require(countNonNull(button_element, local_image_element, remote_image_element, money_element,
        spacer_element, text_element, customized_card_element, address_element, text_input_element,
        option_picker_element, detail_row_element, currency_conversion_flags_element) <= 1) {
      "At most one of button_element, local_image_element, remote_image_element, money_element, spacer_element, text_element, customized_card_element, address_element, text_input_element, option_picker_element, detail_row_element, currency_conversion_flags_element may be non-null"
    }
  }

  @Deprecated(
    message = "Shouldn't be used in Kotlin",
    level = DeprecationLevel.HIDDEN
  )
  override fun newBuilder(): Nothing {
    throw AssertionError()
  }

  override fun equals(other: Any?): Boolean {
    if (other === this) return true
    if (other !is Form) return false
    return unknownFields == other.unknownFields
        && button_element == other.button_element
        && local_image_element == other.local_image_element
        && remote_image_element == other.remote_image_element
        && money_element == other.money_element
        && spacer_element == other.spacer_element
        && text_element == other.text_element
        && customized_card_element == other.customized_card_element
        && address_element == other.address_element
        && text_input_element == other.text_input_element
        && option_picker_element == other.option_picker_element
        && detail_row_element == other.detail_row_element
        && currency_conversion_flags_element == other.currency_conversion_flags_element
  }

  override fun hashCode(): Int {
    var result = super.hashCode
    if (result == 0) {
      result = unknownFields.hashCode()
      result = result * 37 + button_element.hashCode()
      result = result * 37 + local_image_element.hashCode()
      result = result * 37 + remote_image_element.hashCode()
      result = result * 37 + money_element.hashCode()
      result = result * 37 + spacer_element.hashCode()
      result = result * 37 + text_element.hashCode()
      result = result * 37 + customized_card_element.hashCode()
      result = result * 37 + address_element.hashCode()
      result = result * 37 + text_input_element.hashCode()
      result = result * 37 + option_picker_element.hashCode()
      result = result * 37 + detail_row_element.hashCode()
      result = result * 37 + currency_conversion_flags_element.hashCode()
      super.hashCode = result
    }
    return result
  }

  override fun toString(): String {
    val result = mutableListOf<String>()
    if (button_element != null) result += """button_element=$button_element"""
    if (local_image_element != null) result += """local_image_element=$local_image_element"""
    if (remote_image_element != null) result += """remote_image_element=$remote_image_element"""
    if (money_element != null) result += """money_element=$money_element"""
    if (spacer_element != null) result += """spacer_element=$spacer_element"""
    if (text_element != null) result += """text_element=$text_element"""
    if (customized_card_element != null) result +=
        """customized_card_element=$customized_card_element"""
    if (address_element != null) result += """address_element=$address_element"""
    if (text_input_element != null) result += """text_input_element=$text_input_element"""
    if (option_picker_element != null) result += """option_picker_element=$option_picker_element"""
    if (detail_row_element != null) result += """detail_row_element=$detail_row_element"""
    if (currency_conversion_flags_element != null) result +=
        """currency_conversion_flags_element=$currency_conversion_flags_element"""
    return result.joinToString(prefix = "Form{", separator = ", ", postfix = "}")
  }

  fun copy(
    button_element: ButtonElement? = this.button_element,
    local_image_element: LocalImageElement? = this.local_image_element,
    remote_image_element: RemoteImageElement? = this.remote_image_element,
    money_element: MoneyElement? = this.money_element,
    spacer_element: SpacerElement? = this.spacer_element,
    text_element: TextElement? = this.text_element,
    customized_card_element: CustomizedCardElement? = this.customized_card_element,
    address_element: AddressElement? = this.address_element,
    text_input_element: TextInputElement? = this.text_input_element,
    option_picker_element: OptionPickerElement? = this.option_picker_element,
    detail_row_element: DetailRowElement? = this.detail_row_element,
    currency_conversion_flags_element: CurrencyConversionFlagsElement? =
        this.currency_conversion_flags_element,
    unknownFields: ByteString = this.unknownFields
  ): Form = Form(button_element, local_image_element, remote_image_element, money_element,
      spacer_element, text_element, customized_card_element, address_element, text_input_element,
      option_picker_element, detail_row_element, currency_conversion_flags_element, unknownFields)

  companion object {
    @JvmField
    val ADAPTER: ProtoAdapter<Form> = object : ProtoAdapter<Form>(
      FieldEncoding.LENGTH_DELIMITED, 
      Form::class
    ) {
      override fun encodedSize(value: Form): Int = 
        ButtonElement.ADAPTER.encodedSizeWithTag(1, value.button_element) +
        LocalImageElement.ADAPTER.encodedSizeWithTag(2, value.local_image_element) +
        RemoteImageElement.ADAPTER.encodedSizeWithTag(3, value.remote_image_element) +
        MoneyElement.ADAPTER.encodedSizeWithTag(4, value.money_element) +
        SpacerElement.ADAPTER.encodedSizeWithTag(5, value.spacer_element) +
        TextElement.ADAPTER.encodedSizeWithTag(6, value.text_element) +
        CustomizedCardElement.ADAPTER.encodedSizeWithTag(7, value.customized_card_element) +
        AddressElement.ADAPTER.encodedSizeWithTag(8, value.address_element) +
        TextInputElement.ADAPTER.encodedSizeWithTag(9, value.text_input_element) +
        OptionPickerElement.ADAPTER.encodedSizeWithTag(10, value.option_picker_element) +
        DetailRowElement.ADAPTER.encodedSizeWithTag(11, value.detail_row_element) +
        CurrencyConversionFlagsElement.ADAPTER.encodedSizeWithTag(12,
            value.currency_conversion_flags_element) +
        value.unknownFields.size

      override fun encode(writer: ProtoWriter, value: Form) {
        ButtonElement.ADAPTER.encodeWithTag(writer, 1, value.button_element)
        LocalImageElement.ADAPTER.encodeWithTag(writer, 2, value.local_image_element)
        RemoteImageElement.ADAPTER.encodeWithTag(writer, 3, value.remote_image_element)
        MoneyElement.ADAPTER.encodeWithTag(writer, 4, value.money_element)
        SpacerElement.ADAPTER.encodeWithTag(writer, 5, value.spacer_element)
        TextElement.ADAPTER.encodeWithTag(writer, 6, value.text_element)
        CustomizedCardElement.ADAPTER.encodeWithTag(writer, 7, value.customized_card_element)
        AddressElement.ADAPTER.encodeWithTag(writer, 8, value.address_element)
        TextInputElement.ADAPTER.encodeWithTag(writer, 9, value.text_input_element)
        OptionPickerElement.ADAPTER.encodeWithTag(writer, 10, value.option_picker_element)
        DetailRowElement.ADAPTER.encodeWithTag(writer, 11, value.detail_row_element)
        CurrencyConversionFlagsElement.ADAPTER.encodeWithTag(writer, 12,
            value.currency_conversion_flags_element)
        writer.writeBytes(value.unknownFields)
      }

      override fun decode(reader: ProtoReader): Form {
        var button_element: ButtonElement? = null
        var local_image_element: LocalImageElement? = null
        var remote_image_element: RemoteImageElement? = null
        var money_element: MoneyElement? = null
        var spacer_element: SpacerElement? = null
        var text_element: TextElement? = null
        var customized_card_element: CustomizedCardElement? = null
        var address_element: AddressElement? = null
        var text_input_element: TextInputElement? = null
        var option_picker_element: OptionPickerElement? = null
        var detail_row_element: DetailRowElement? = null
        var currency_conversion_flags_element: CurrencyConversionFlagsElement? = null
        val unknownFields = reader.forEachTag { tag ->
          when (tag) {
            1 -> button_element = ButtonElement.ADAPTER.decode(reader)
            2 -> local_image_element = LocalImageElement.ADAPTER.decode(reader)
            3 -> remote_image_element = RemoteImageElement.ADAPTER.decode(reader)
            4 -> money_element = MoneyElement.ADAPTER.decode(reader)
            5 -> spacer_element = SpacerElement.ADAPTER.decode(reader)
            6 -> text_element = TextElement.ADAPTER.decode(reader)
            7 -> customized_card_element = CustomizedCardElement.ADAPTER.decode(reader)
            8 -> address_element = AddressElement.ADAPTER.decode(reader)
            9 -> text_input_element = TextInputElement.ADAPTER.decode(reader)
            10 -> option_picker_element = OptionPickerElement.ADAPTER.decode(reader)
            11 -> detail_row_element = DetailRowElement.ADAPTER.decode(reader)
            12 -> currency_conversion_flags_element =
                CurrencyConversionFlagsElement.ADAPTER.decode(reader)
            else -> reader.readUnknownField(tag)
          }
        }
        return Form(
          button_element = button_element,
          local_image_element = local_image_element,
          remote_image_element = remote_image_element,
          money_element = money_element,
          spacer_element = spacer_element,
          text_element = text_element,
          customized_card_element = customized_card_element,
          address_element = address_element,
          text_input_element = text_input_element,
          option_picker_element = option_picker_element,
          detail_row_element = detail_row_element,
          currency_conversion_flags_element = currency_conversion_flags_element,
          unknownFields = unknownFields
        )
      }

      override fun redact(value: Form): Form = value.copy(
        button_element = value.button_element?.let(ButtonElement.ADAPTER::redact),
        local_image_element = value.local_image_element?.let(LocalImageElement.ADAPTER::redact),
        remote_image_element = value.remote_image_element?.let(RemoteImageElement.ADAPTER::redact),
        money_element = value.money_element?.let(MoneyElement.ADAPTER::redact),
        spacer_element = value.spacer_element?.let(SpacerElement.ADAPTER::redact),
        text_element = value.text_element?.let(TextElement.ADAPTER::redact),
        customized_card_element =
            value.customized_card_element?.let(CustomizedCardElement.ADAPTER::redact),
        address_element = value.address_element?.let(AddressElement.ADAPTER::redact),
        text_input_element = value.text_input_element?.let(TextInputElement.ADAPTER::redact),
        option_picker_element =
            value.option_picker_element?.let(OptionPickerElement.ADAPTER::redact),
        detail_row_element = value.detail_row_element?.let(DetailRowElement.ADAPTER::redact),
        currency_conversion_flags_element =
            value.currency_conversion_flags_element?.let(CurrencyConversionFlagsElement.ADAPTER::redact),
        unknownFields = ByteString.EMPTY
      )
    }
  }

  class ButtonElement(
    unknownFields: ByteString = ByteString.EMPTY
  ) : Message<ButtonElement, Nothing>(ADAPTER, unknownFields) {
    @Deprecated(
      message = "Shouldn't be used in Kotlin",
      level = DeprecationLevel.HIDDEN
    )
    override fun newBuilder(): Nothing {
      throw AssertionError()
    }

    override fun equals(other: Any?): Boolean {
      if (other === this) return true
      if (other !is ButtonElement) return false
      return unknownFields == other.unknownFields
    }

    override fun hashCode(): Int = unknownFields.hashCode()

    override fun toString(): String = "ButtonElement{}"

    fun copy(unknownFields: ByteString = this.unknownFields): ButtonElement =
        ButtonElement(unknownFields)

    companion object {
      @JvmField
      val ADAPTER: ProtoAdapter<ButtonElement> = object : ProtoAdapter<ButtonElement>(
        FieldEncoding.LENGTH_DELIMITED, 
        ButtonElement::class
      ) {
        override fun encodedSize(value: ButtonElement): Int = 
          value.unknownFields.size

        override fun encode(writer: ProtoWriter, value: ButtonElement) {
          writer.writeBytes(value.unknownFields)
        }

        override fun decode(reader: ProtoReader): ButtonElement {
          val unknownFields = reader.forEachTag(reader::readUnknownField)
          return ButtonElement(
            unknownFields = unknownFields
          )
        }

        override fun redact(value: ButtonElement): ButtonElement = value.copy(
          unknownFields = ByteString.EMPTY
        )
      }
    }
  }

  class LocalImageElement(
    unknownFields: ByteString = ByteString.EMPTY
  ) : Message<LocalImageElement, Nothing>(ADAPTER, unknownFields) {
    @Deprecated(
      message = "Shouldn't be used in Kotlin",
      level = DeprecationLevel.HIDDEN
    )
    override fun newBuilder(): Nothing {
      throw AssertionError()
    }

    override fun equals(other: Any?): Boolean {
      if (other === this) return true
      if (other !is LocalImageElement) return false
      return unknownFields == other.unknownFields
    }

    override fun hashCode(): Int = unknownFields.hashCode()

    override fun toString(): String = "LocalImageElement{}"

    fun copy(unknownFields: ByteString = this.unknownFields): LocalImageElement =
        LocalImageElement(unknownFields)

    companion object {
      @JvmField
      val ADAPTER: ProtoAdapter<LocalImageElement> = object : ProtoAdapter<LocalImageElement>(
        FieldEncoding.LENGTH_DELIMITED, 
        LocalImageElement::class
      ) {
        override fun encodedSize(value: LocalImageElement): Int = 
          value.unknownFields.size

        override fun encode(writer: ProtoWriter, value: LocalImageElement) {
          writer.writeBytes(value.unknownFields)
        }

        override fun decode(reader: ProtoReader): LocalImageElement {
          val unknownFields = reader.forEachTag(reader::readUnknownField)
          return LocalImageElement(
            unknownFields = unknownFields
          )
        }

        override fun redact(value: LocalImageElement): LocalImageElement = value.copy(
          unknownFields = ByteString.EMPTY
        )
      }
    }
  }

  class RemoteImageElement(
    unknownFields: ByteString = ByteString.EMPTY
  ) : Message<RemoteImageElement, Nothing>(ADAPTER, unknownFields) {
    @Deprecated(
      message = "Shouldn't be used in Kotlin",
      level = DeprecationLevel.HIDDEN
    )
    override fun newBuilder(): Nothing {
      throw AssertionError()
    }

    override fun equals(other: Any?): Boolean {
      if (other === this) return true
      if (other !is RemoteImageElement) return false
      return unknownFields == other.unknownFields
    }

    override fun hashCode(): Int = unknownFields.hashCode()

    override fun toString(): String = "RemoteImageElement{}"

    fun copy(unknownFields: ByteString = this.unknownFields): RemoteImageElement =
        RemoteImageElement(unknownFields)

    companion object {
      @JvmField
      val ADAPTER: ProtoAdapter<RemoteImageElement> = object : ProtoAdapter<RemoteImageElement>(
        FieldEncoding.LENGTH_DELIMITED, 
        RemoteImageElement::class
      ) {
        override fun encodedSize(value: RemoteImageElement): Int = 
          value.unknownFields.size

        override fun encode(writer: ProtoWriter, value: RemoteImageElement) {
          writer.writeBytes(value.unknownFields)
        }

        override fun decode(reader: ProtoReader): RemoteImageElement {
          val unknownFields = reader.forEachTag(reader::readUnknownField)
          return RemoteImageElement(
            unknownFields = unknownFields
          )
        }

        override fun redact(value: RemoteImageElement): RemoteImageElement = value.copy(
          unknownFields = ByteString.EMPTY
        )
      }
    }
  }

  class MoneyElement(
    unknownFields: ByteString = ByteString.EMPTY
  ) : Message<MoneyElement, Nothing>(ADAPTER, unknownFields) {
    @Deprecated(
      message = "Shouldn't be used in Kotlin",
      level = DeprecationLevel.HIDDEN
    )
    override fun newBuilder(): Nothing {
      throw AssertionError()
    }

    override fun equals(other: Any?): Boolean {
      if (other === this) return true
      if (other !is MoneyElement) return false
      return unknownFields == other.unknownFields
    }

    override fun hashCode(): Int = unknownFields.hashCode()

    override fun toString(): String = "MoneyElement{}"

    fun copy(unknownFields: ByteString = this.unknownFields): MoneyElement =
        MoneyElement(unknownFields)

    companion object {
      @JvmField
      val ADAPTER: ProtoAdapter<MoneyElement> = object : ProtoAdapter<MoneyElement>(
        FieldEncoding.LENGTH_DELIMITED, 
        MoneyElement::class
      ) {
        override fun encodedSize(value: MoneyElement): Int = 
          value.unknownFields.size

        override fun encode(writer: ProtoWriter, value: MoneyElement) {
          writer.writeBytes(value.unknownFields)
        }

        override fun decode(reader: ProtoReader): MoneyElement {
          val unknownFields = reader.forEachTag(reader::readUnknownField)
          return MoneyElement(
            unknownFields = unknownFields
          )
        }

        override fun redact(value: MoneyElement): MoneyElement = value.copy(
          unknownFields = ByteString.EMPTY
        )
      }
    }
  }

  class SpacerElement(
    unknownFields: ByteString = ByteString.EMPTY
  ) : Message<SpacerElement, Nothing>(ADAPTER, unknownFields) {
    @Deprecated(
      message = "Shouldn't be used in Kotlin",
      level = DeprecationLevel.HIDDEN
    )
    override fun newBuilder(): Nothing {
      throw AssertionError()
    }

    override fun equals(other: Any?): Boolean {
      if (other === this) return true
      if (other !is SpacerElement) return false
      return unknownFields == other.unknownFields
    }

    override fun hashCode(): Int = unknownFields.hashCode()

    override fun toString(): String = "SpacerElement{}"

    fun copy(unknownFields: ByteString = this.unknownFields): SpacerElement =
        SpacerElement(unknownFields)

    companion object {
      @JvmField
      val ADAPTER: ProtoAdapter<SpacerElement> = object : ProtoAdapter<SpacerElement>(
        FieldEncoding.LENGTH_DELIMITED, 
        SpacerElement::class
      ) {
        override fun encodedSize(value: SpacerElement): Int = 
          value.unknownFields.size

        override fun encode(writer: ProtoWriter, value: SpacerElement) {
          writer.writeBytes(value.unknownFields)
        }

        override fun decode(reader: ProtoReader): SpacerElement {
          val unknownFields = reader.forEachTag(reader::readUnknownField)
          return SpacerElement(
            unknownFields = unknownFields
          )
        }

        override fun redact(value: SpacerElement): SpacerElement = value.copy(
          unknownFields = ByteString.EMPTY
        )
      }
    }
  }

  class TextElement(
    unknownFields: ByteString = ByteString.EMPTY
  ) : Message<TextElement, Nothing>(ADAPTER, unknownFields) {
    @Deprecated(
      message = "Shouldn't be used in Kotlin",
      level = DeprecationLevel.HIDDEN
    )
    override fun newBuilder(): Nothing {
      throw AssertionError()
    }

    override fun equals(other: Any?): Boolean {
      if (other === this) return true
      if (other !is TextElement) return false
      return unknownFields == other.unknownFields
    }

    override fun hashCode(): Int = unknownFields.hashCode()

    override fun toString(): String = "TextElement{}"

    fun copy(unknownFields: ByteString = this.unknownFields): TextElement =
        TextElement(unknownFields)

    companion object {
      @JvmField
      val ADAPTER: ProtoAdapter<TextElement> = object : ProtoAdapter<TextElement>(
        FieldEncoding.LENGTH_DELIMITED, 
        TextElement::class
      ) {
        override fun encodedSize(value: TextElement): Int = 
          value.unknownFields.size

        override fun encode(writer: ProtoWriter, value: TextElement) {
          writer.writeBytes(value.unknownFields)
        }

        override fun decode(reader: ProtoReader): TextElement {
          val unknownFields = reader.forEachTag(reader::readUnknownField)
          return TextElement(
            unknownFields = unknownFields
          )
        }

        override fun redact(value: TextElement): TextElement = value.copy(
          unknownFields = ByteString.EMPTY
        )
      }
    }
  }

  class CustomizedCardElement(
    unknownFields: ByteString = ByteString.EMPTY
  ) : Message<CustomizedCardElement, Nothing>(ADAPTER, unknownFields) {
    @Deprecated(
      message = "Shouldn't be used in Kotlin",
      level = DeprecationLevel.HIDDEN
    )
    override fun newBuilder(): Nothing {
      throw AssertionError()
    }

    override fun equals(other: Any?): Boolean {
      if (other === this) return true
      if (other !is CustomizedCardElement) return false
      return unknownFields == other.unknownFields
    }

    override fun hashCode(): Int = unknownFields.hashCode()

    override fun toString(): String = "CustomizedCardElement{}"

    fun copy(unknownFields: ByteString = this.unknownFields): CustomizedCardElement =
        CustomizedCardElement(unknownFields)

    companion object {
      @JvmField
      val ADAPTER: ProtoAdapter<CustomizedCardElement> = object :
          ProtoAdapter<CustomizedCardElement>(
        FieldEncoding.LENGTH_DELIMITED, 
        CustomizedCardElement::class
      ) {
        override fun encodedSize(value: CustomizedCardElement): Int = 
          value.unknownFields.size

        override fun encode(writer: ProtoWriter, value: CustomizedCardElement) {
          writer.writeBytes(value.unknownFields)
        }

        override fun decode(reader: ProtoReader): CustomizedCardElement {
          val unknownFields = reader.forEachTag(reader::readUnknownField)
          return CustomizedCardElement(
            unknownFields = unknownFields
          )
        }

        override fun redact(value: CustomizedCardElement): CustomizedCardElement = value.copy(
          unknownFields = ByteString.EMPTY
        )
      }
    }
  }

  class AddressElement(
    unknownFields: ByteString = ByteString.EMPTY
  ) : Message<AddressElement, Nothing>(ADAPTER, unknownFields) {
    @Deprecated(
      message = "Shouldn't be used in Kotlin",
      level = DeprecationLevel.HIDDEN
    )
    override fun newBuilder(): Nothing {
      throw AssertionError()
    }

    override fun equals(other: Any?): Boolean {
      if (other === this) return true
      if (other !is AddressElement) return false
      return unknownFields == other.unknownFields
    }

    override fun hashCode(): Int = unknownFields.hashCode()

    override fun toString(): String = "AddressElement{}"

    fun copy(unknownFields: ByteString = this.unknownFields): AddressElement =
        AddressElement(unknownFields)

    companion object {
      @JvmField
      val ADAPTER: ProtoAdapter<AddressElement> = object : ProtoAdapter<AddressElement>(
        FieldEncoding.LENGTH_DELIMITED, 
        AddressElement::class
      ) {
        override fun encodedSize(value: AddressElement): Int = 
          value.unknownFields.size

        override fun encode(writer: ProtoWriter, value: AddressElement) {
          writer.writeBytes(value.unknownFields)
        }

        override fun decode(reader: ProtoReader): AddressElement {
          val unknownFields = reader.forEachTag(reader::readUnknownField)
          return AddressElement(
            unknownFields = unknownFields
          )
        }

        override fun redact(value: AddressElement): AddressElement = value.copy(
          unknownFields = ByteString.EMPTY
        )
      }
    }
  }

  class TextInputElement(
    unknownFields: ByteString = ByteString.EMPTY
  ) : Message<TextInputElement, Nothing>(ADAPTER, unknownFields) {
    @Deprecated(
      message = "Shouldn't be used in Kotlin",
      level = DeprecationLevel.HIDDEN
    )
    override fun newBuilder(): Nothing {
      throw AssertionError()
    }

    override fun equals(other: Any?): Boolean {
      if (other === this) return true
      if (other !is TextInputElement) return false
      return unknownFields == other.unknownFields
    }

    override fun hashCode(): Int = unknownFields.hashCode()

    override fun toString(): String = "TextInputElement{}"

    fun copy(unknownFields: ByteString = this.unknownFields): TextInputElement =
        TextInputElement(unknownFields)

    companion object {
      @JvmField
      val ADAPTER: ProtoAdapter<TextInputElement> = object : ProtoAdapter<TextInputElement>(
        FieldEncoding.LENGTH_DELIMITED, 
        TextInputElement::class
      ) {
        override fun encodedSize(value: TextInputElement): Int = 
          value.unknownFields.size

        override fun encode(writer: ProtoWriter, value: TextInputElement) {
          writer.writeBytes(value.unknownFields)
        }

        override fun decode(reader: ProtoReader): TextInputElement {
          val unknownFields = reader.forEachTag(reader::readUnknownField)
          return TextInputElement(
            unknownFields = unknownFields
          )
        }

        override fun redact(value: TextInputElement): TextInputElement = value.copy(
          unknownFields = ByteString.EMPTY
        )
      }
    }
  }

  class OptionPickerElement(
    unknownFields: ByteString = ByteString.EMPTY
  ) : Message<OptionPickerElement, Nothing>(ADAPTER, unknownFields) {
    @Deprecated(
      message = "Shouldn't be used in Kotlin",
      level = DeprecationLevel.HIDDEN
    )
    override fun newBuilder(): Nothing {
      throw AssertionError()
    }

    override fun equals(other: Any?): Boolean {
      if (other === this) return true
      if (other !is OptionPickerElement) return false
      return unknownFields == other.unknownFields
    }

    override fun hashCode(): Int = unknownFields.hashCode()

    override fun toString(): String = "OptionPickerElement{}"

    fun copy(unknownFields: ByteString = this.unknownFields): OptionPickerElement =
        OptionPickerElement(unknownFields)

    companion object {
      @JvmField
      val ADAPTER: ProtoAdapter<OptionPickerElement> = object : ProtoAdapter<OptionPickerElement>(
        FieldEncoding.LENGTH_DELIMITED, 
        OptionPickerElement::class
      ) {
        override fun encodedSize(value: OptionPickerElement): Int = 
          value.unknownFields.size

        override fun encode(writer: ProtoWriter, value: OptionPickerElement) {
          writer.writeBytes(value.unknownFields)
        }

        override fun decode(reader: ProtoReader): OptionPickerElement {
          val unknownFields = reader.forEachTag(reader::readUnknownField)
          return OptionPickerElement(
            unknownFields = unknownFields
          )
        }

        override fun redact(value: OptionPickerElement): OptionPickerElement = value.copy(
          unknownFields = ByteString.EMPTY
        )
      }
    }
  }

  class DetailRowElement(
    unknownFields: ByteString = ByteString.EMPTY
  ) : Message<DetailRowElement, Nothing>(ADAPTER, unknownFields) {
    @Deprecated(
      message = "Shouldn't be used in Kotlin",
      level = DeprecationLevel.HIDDEN
    )
    override fun newBuilder(): Nothing {
      throw AssertionError()
    }

    override fun equals(other: Any?): Boolean {
      if (other === this) return true
      if (other !is DetailRowElement) return false
      return unknownFields == other.unknownFields
    }

    override fun hashCode(): Int = unknownFields.hashCode()

    override fun toString(): String = "DetailRowElement{}"

    fun copy(unknownFields: ByteString = this.unknownFields): DetailRowElement =
        DetailRowElement(unknownFields)

    companion object {
      @JvmField
      val ADAPTER: ProtoAdapter<DetailRowElement> = object : ProtoAdapter<DetailRowElement>(
        FieldEncoding.LENGTH_DELIMITED, 
        DetailRowElement::class
      ) {
        override fun encodedSize(value: DetailRowElement): Int = 
          value.unknownFields.size

        override fun encode(writer: ProtoWriter, value: DetailRowElement) {
          writer.writeBytes(value.unknownFields)
        }

        override fun decode(reader: ProtoReader): DetailRowElement {
          val unknownFields = reader.forEachTag(reader::readUnknownField)
          return DetailRowElement(
            unknownFields = unknownFields
          )
        }

        override fun redact(value: DetailRowElement): DetailRowElement = value.copy(
          unknownFields = ByteString.EMPTY
        )
      }
    }
  }

  class CurrencyConversionFlagsElement(
    unknownFields: ByteString = ByteString.EMPTY
  ) : Message<CurrencyConversionFlagsElement, Nothing>(ADAPTER, unknownFields) {
    @Deprecated(
      message = "Shouldn't be used in Kotlin",
      level = DeprecationLevel.HIDDEN
    )
    override fun newBuilder(): Nothing {
      throw AssertionError()
    }

    override fun equals(other: Any?): Boolean {
      if (other === this) return true
      if (other !is CurrencyConversionFlagsElement) return false
      return unknownFields == other.unknownFields
    }

    override fun hashCode(): Int = unknownFields.hashCode()

    override fun toString(): String = "CurrencyConversionFlagsElement{}"

    fun copy(unknownFields: ByteString = this.unknownFields): CurrencyConversionFlagsElement =
        CurrencyConversionFlagsElement(unknownFields)

    companion object {
      @JvmField
      val ADAPTER: ProtoAdapter<CurrencyConversionFlagsElement> = object :
          ProtoAdapter<CurrencyConversionFlagsElement>(
        FieldEncoding.LENGTH_DELIMITED, 
        CurrencyConversionFlagsElement::class
      ) {
        override fun encodedSize(value: CurrencyConversionFlagsElement): Int = 
          value.unknownFields.size

        override fun encode(writer: ProtoWriter, value: CurrencyConversionFlagsElement) {
          writer.writeBytes(value.unknownFields)
        }

        override fun decode(reader: ProtoReader): CurrencyConversionFlagsElement {
          val unknownFields = reader.forEachTag(reader::readUnknownField)
          return CurrencyConversionFlagsElement(
            unknownFields = unknownFields
          )
        }

        override fun redact(value: CurrencyConversionFlagsElement): CurrencyConversionFlagsElement =
            value.copy(
          unknownFields = ByteString.EMPTY
        )
      }
    }
  }
}
