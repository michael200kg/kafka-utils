// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: schema.proto

package com.michael200kg.test.simpleproducer.model.generated;

public final class Schema {
  private Schema() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistryLite registry) {
  }

  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
    registerAllExtensions(
        (com.google.protobuf.ExtensionRegistryLite) registry);
  }
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_tutorial_ProtobufRecord_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_tutorial_ProtobufRecord_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static  com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    String[] descriptorData = {
      "\n\014schema.proto\022\010tutorial\"\\\n\016ProtobufReco" +
      "rd\022\023\n\013operationId\030\001 \002(\t\022\r\n\005docId\030\002 \002(\005\022\022" +
      "\n\nmetadataTt\030\003 \001(\t\022\022\n\nmetadataBt\030\004 \001(\tB\037" +
      "\n\033com.example.tutorial.protosP\001"
    };
    descriptor = com.google.protobuf.Descriptors.FileDescriptor
      .internalBuildGeneratedFileFrom(descriptorData,
        new com.google.protobuf.Descriptors.FileDescriptor[] {
        });
    internal_static_tutorial_ProtobufRecord_descriptor =
      getDescriptor().getMessageTypes().get(0);
    internal_static_tutorial_ProtobufRecord_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_tutorial_ProtobufRecord_descriptor,
        new String[] { "OperationId", "DocId", "MetadataTt", "MetadataBt", });
  }

  // @@protoc_insertion_point(outer_class_scope)
}
