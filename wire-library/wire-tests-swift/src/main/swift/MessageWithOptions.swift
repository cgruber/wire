// Code generated by Wire protocol buffer compiler, do not edit.
// Source file: custom_options.proto
import Foundation
import Wire

public struct MessageWithOptions : Equatable, Proto2Codable, Codable {

  public let unknownFields: Data

  public init(from reader: ProtoReader) throws {

    let unknownFields = try reader.forEachTag { tag in
      switch tag {
        default: try reader.readUnknownField(tag: tag)
      }
    }

    self.unknownFields = unknownFields
  }

  public func encode(to writer: ProtoWriter) throws {
  }

}
