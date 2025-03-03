// Code generated by Wire protocol buffer compiler, do not edit.
// Source: squareup.protos.kotlin.DeprecatedProto in deprecated.proto
import Foundation
import Wire

public struct DeprecatedProto {

    @available(*, deprecated)
    public var foo: String?
    public var unknownFields: Foundation.Data = .init()

    public init(configure: (inout Self) -> Void = { _ in }) {
        configure(&self)
    }

}

#if WIRE_INCLUDE_MEMBERWISE_INITIALIZER
extension DeprecatedProto {

    @_disfavoredOverload
    @available(*, deprecated)
    public init(foo: Swift.String? = nil) {
        self.foo = foo
    }

}
#endif

#if !WIRE_REMOVE_EQUATABLE
extension DeprecatedProto : Equatable {
}
#endif

#if !WIRE_REMOVE_HASHABLE
extension DeprecatedProto : Hashable {
}
#endif

#if swift(>=5.5)
extension DeprecatedProto : Sendable {
}
#endif

extension DeprecatedProto : ProtoMessage {

    public static func protoMessageTypeURL() -> Swift.String {
        return "type.googleapis.com/squareup.protos.kotlin.DeprecatedProto"
    }

}

extension DeprecatedProto : Proto2Codable {

    public init(from reader: Wire.ProtoReader) throws {
        var foo: Swift.String? = nil

        let token = try reader.beginMessage()
        while let tag = try reader.nextTag(token: token) {
            switch tag {
            case 1: foo = try reader.decode(Swift.String.self)
            default: try reader.readUnknownField(tag: tag)
            }
        }
        self.unknownFields = try reader.endMessage(token: token)

        self.foo = foo
    }

    public func encode(to writer: Wire.ProtoWriter) throws {
        try writer.encode(tag: 1, value: self.foo)
        try writer.writeUnknownFields(unknownFields)
    }

}

#if !WIRE_REMOVE_CODABLE
extension DeprecatedProto : Codable {

    public init(from decoder: Swift.Decoder) throws {
        let container = try decoder.container(keyedBy: Wire.StringLiteralCodingKeys.self)
        self.foo = try container.decodeIfPresent(Swift.String.self, forKey: "foo")
    }

    public func encode(to encoder: Swift.Encoder) throws {
        var container = encoder.container(keyedBy: Wire.StringLiteralCodingKeys.self)

        try container.encodeIfPresent(self.foo, forKey: "foo")
    }

}
#endif
