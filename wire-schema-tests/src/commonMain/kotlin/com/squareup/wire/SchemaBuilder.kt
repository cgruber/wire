/*
 * Copyright (C) 2022 Square, Inc.
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
package com.squareup.wire

import com.squareup.wire.schema.Location
import com.squareup.wire.schema.Schema
import com.squareup.wire.schema.SchemaLoader
import okio.FileSystem
import okio.IOException
import okio.Path
import okio.Path.Companion.toPath
import okio.fakefilesystem.FakeFileSystem

/** Builds a schema out of written `.proto` files. */
class SchemaBuilder {
  private val sourcePath: Path = "/sourcePath".toPath()
  private val protoPath: Path = "/protoPath".toPath()
  private val fileSystem: FileSystem = FakeFileSystem()

  init {
    fileSystem.createDirectories(sourcePath)
    fileSystem.createDirectories(protoPath)
  }

  /**
   * Add a file to be loaded into the schema.
   *
   * @param name The qualified name of the file.
   * @param protoFile The content of the file.
   */
  fun add(name: Path, protoFile: String): SchemaBuilder {
    return add(name, protoFile, sourcePath)
  }

  /**
   * Add a file to be loaded into the schema.
   *
   * @param name The qualified name of the file.
   * @param protoFile The content of the file.
   * @param path The path on which [name] is based.
   */
  fun add(name: Path, protoFile: String, path: Path): SchemaBuilder {
    require(name.toString().endsWith(".proto")) {
      "unexpected file extension for $name. Proto files should use the '.proto' extension"
    }

    try {
      val resolvedPath = path / name
      val parent = resolvedPath.parent
      if (parent != null) {
        fileSystem.createDirectories(parent)
      }
      fileSystem.write(resolvedPath) {
        writeUtf8(protoFile)
      }
    } catch (e: IOException) {
      throw AssertionError(e)
    }
    return this
  }

  /** Add a file to be linked against, but not used to generate artifacts. */
  fun addProtoPath(name: Path, protoFile: String): SchemaBuilder {
    return add(name, protoFile, protoPath)
  }

  fun build(): Schema {
    val schemaLoader = SchemaLoader(fileSystem)
    schemaLoader.initRoots(
      sourcePath = listOf(Location.get(sourcePath.toString())),
      protoPath = listOf(Location.get(protoPath.toString())),
    )
    return schemaLoader.loadSchema()
  }
}

/** Builds a schema out of written `.proto` files. */
inline fun buildSchema(builderAction: SchemaBuilder.() -> Unit): Schema {
  return SchemaBuilder().apply(builderAction).build()
}
