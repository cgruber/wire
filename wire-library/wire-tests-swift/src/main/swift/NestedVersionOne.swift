// Code generated by Wire protocol buffer compiler, do not edit.
// Source file: unknown_fields.proto
import Foundation
import Wire

public struct NestedVersionOne : Equatable, Proto2Codable, Codable {

  public var i: Int32?
  public let unknownFields: Data

  public init(from reader: ProtoReader) throws {
    var i: Int32? = nil

    let unknownFields = try reader.forEachTag { tag in
      switch tag {
        case 1: i = try reader.decode(Int32.self)
        default: try reader.readUnknownField(tag: tag)
      }
    }

    self.i = i
    self.unknownFields = unknownFields
  }

  public func encode(to writer: ProtoWriter) throws {
    try writer.encode(tag: 1, value: i)
  }

}
