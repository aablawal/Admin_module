// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: BlobStorageService.proto

package com.unionbankng.future.futureutilityservice.grpcserver;

public final class BlobStorageServiceOuterClass {
  private BlobStorageServiceOuterClass() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistryLite registry) {
  }

  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
    registerAllExtensions(
        (com.google.protobuf.ExtensionRegistryLite) registry);
  }
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_com_unionbankng_future_futureutilityservice_grpcserver_StorageUploadRequest_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_com_unionbankng_future_futureutilityservice_grpcserver_StorageUploadRequest_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_com_unionbankng_future_futureutilityservice_grpcserver_StorageDeleteRequest_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_com_unionbankng_future_futureutilityservice_grpcserver_StorageDeleteRequest_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_com_unionbankng_future_futureutilityservice_grpcserver_StorageUploadResponse_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_com_unionbankng_future_futureutilityservice_grpcserver_StorageUploadResponse_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_com_unionbankng_future_futureutilityservice_grpcserver_StorageDeleteResponse_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_com_unionbankng_future_futureutilityservice_grpcserver_StorageDeleteResponse_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static  com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    java.lang.String[] descriptorData = {
      "\n\030BlobStorageService.proto\0226com.unionban" +
      "kng.future.futureutilityservice.grpcserv" +
      "er\"\216\001\n\024StorageUploadRequest\022\020\n\010fileName\030" +
      "\001 \001(\t\022\020\n\010fileByte\030\002 \001(\014\022R\n\010blobType\030\003 \001(" +
      "\0162@.com.unionbankng.future.futureutility" +
      "service.grpcserver.BlobType\"~\n\024StorageDe" +
      "leteRequest\022\022\n\nidentifier\030\001 \001(\t\022R\n\010blobT" +
      "ype\030\002 \001(\0162@.com.unionbankng.future.futur" +
      "eutilityservice.grpcserver.BlobType\"$\n\025S" +
      "torageUploadResponse\022\013\n\003url\030\001 \001(\t\"%\n\025Sto" +
      "rageDeleteResponse\022\014\n\004code\030\001 \001(\005* \n\010Blob" +
      "Type\022\t\n\005VIDEO\020\000\022\t\n\005IMAGE\020\0012\344\002\n\022BlobStora" +
      "geService\022\245\001\n\006upload\022L.com.unionbankng.f" +
      "uture.futureutilityservice.grpcserver.St" +
      "orageUploadRequest\032M.com.unionbankng.fut" +
      "ure.futureutilityservice.grpcserver.Stor" +
      "ageUploadResponse\022\245\001\n\006delete\022L.com.union" +
      "bankng.future.futureutilityservice.grpcs" +
      "erver.StorageDeleteRequest\032M.com.unionba" +
      "nkng.future.futureutilityservice.grpcser" +
      "ver.StorageDeleteResponseB\002P\001b\006proto3"
    };
    descriptor = com.google.protobuf.Descriptors.FileDescriptor
      .internalBuildGeneratedFileFrom(descriptorData,
        new com.google.protobuf.Descriptors.FileDescriptor[] {
        });
    internal_static_com_unionbankng_future_futureutilityservice_grpcserver_StorageUploadRequest_descriptor =
      getDescriptor().getMessageTypes().get(0);
    internal_static_com_unionbankng_future_futureutilityservice_grpcserver_StorageUploadRequest_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_com_unionbankng_future_futureutilityservice_grpcserver_StorageUploadRequest_descriptor,
        new java.lang.String[] { "FileName", "FileByte", "BlobType", });
    internal_static_com_unionbankng_future_futureutilityservice_grpcserver_StorageDeleteRequest_descriptor =
      getDescriptor().getMessageTypes().get(1);
    internal_static_com_unionbankng_future_futureutilityservice_grpcserver_StorageDeleteRequest_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_com_unionbankng_future_futureutilityservice_grpcserver_StorageDeleteRequest_descriptor,
        new java.lang.String[] { "Identifier", "BlobType", });
    internal_static_com_unionbankng_future_futureutilityservice_grpcserver_StorageUploadResponse_descriptor =
      getDescriptor().getMessageTypes().get(2);
    internal_static_com_unionbankng_future_futureutilityservice_grpcserver_StorageUploadResponse_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_com_unionbankng_future_futureutilityservice_grpcserver_StorageUploadResponse_descriptor,
        new java.lang.String[] { "Url", });
    internal_static_com_unionbankng_future_futureutilityservice_grpcserver_StorageDeleteResponse_descriptor =
      getDescriptor().getMessageTypes().get(3);
    internal_static_com_unionbankng_future_futureutilityservice_grpcserver_StorageDeleteResponse_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_com_unionbankng_future_futureutilityservice_grpcserver_StorageDeleteResponse_descriptor,
        new java.lang.String[] { "Code", });
  }

  // @@protoc_insertion_point(outer_class_scope)
}