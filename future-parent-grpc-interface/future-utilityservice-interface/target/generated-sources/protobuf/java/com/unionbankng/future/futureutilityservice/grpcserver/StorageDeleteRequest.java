// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: BlobStorageService.proto

package com.unionbankng.future.futureutilityservice.grpcserver;

/**
 * Protobuf type {@code com.unionbankng.future.futureutilityservice.grpcserver.StorageDeleteRequest}
 */
public final class StorageDeleteRequest extends
    com.google.protobuf.GeneratedMessageV3 implements
    // @@protoc_insertion_point(message_implements:com.unionbankng.future.futureutilityservice.grpcserver.StorageDeleteRequest)
    StorageDeleteRequestOrBuilder {
private static final long serialVersionUID = 0L;
  // Use StorageDeleteRequest.newBuilder() to construct.
  private StorageDeleteRequest(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
    super(builder);
  }
  private StorageDeleteRequest() {
    identifier_ = "";
    blobType_ = 0;
  }

  @java.lang.Override
  @SuppressWarnings({"unused"})
  protected java.lang.Object newInstance(
      UnusedPrivateParameter unused) {
    return new StorageDeleteRequest();
  }

  @java.lang.Override
  public final com.google.protobuf.UnknownFieldSet
  getUnknownFields() {
    return this.unknownFields;
  }
  private StorageDeleteRequest(
      com.google.protobuf.CodedInputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    this();
    if (extensionRegistry == null) {
      throw new java.lang.NullPointerException();
    }
    com.google.protobuf.UnknownFieldSet.Builder unknownFields =
        com.google.protobuf.UnknownFieldSet.newBuilder();
    try {
      boolean done = false;
      while (!done) {
        int tag = input.readTag();
        switch (tag) {
          case 0:
            done = true;
            break;
          case 10: {
            java.lang.String s = input.readStringRequireUtf8();

            identifier_ = s;
            break;
          }
          case 16: {
            int rawValue = input.readEnum();

            blobType_ = rawValue;
            break;
          }
          default: {
            if (!parseUnknownField(
                input, unknownFields, extensionRegistry, tag)) {
              done = true;
            }
            break;
          }
        }
      }
    } catch (com.google.protobuf.InvalidProtocolBufferException e) {
      throw e.setUnfinishedMessage(this);
    } catch (java.io.IOException e) {
      throw new com.google.protobuf.InvalidProtocolBufferException(
          e).setUnfinishedMessage(this);
    } finally {
      this.unknownFields = unknownFields.build();
      makeExtensionsImmutable();
    }
  }
  public static final com.google.protobuf.Descriptors.Descriptor
      getDescriptor() {
    return com.unionbankng.future.futureutilityservice.grpcserver.BlobStorageServiceOuterClass.internal_static_com_unionbankng_future_futureutilityservice_grpcserver_StorageDeleteRequest_descriptor;
  }

  @java.lang.Override
  protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internalGetFieldAccessorTable() {
    return com.unionbankng.future.futureutilityservice.grpcserver.BlobStorageServiceOuterClass.internal_static_com_unionbankng_future_futureutilityservice_grpcserver_StorageDeleteRequest_fieldAccessorTable
        .ensureFieldAccessorsInitialized(
            com.unionbankng.future.futureutilityservice.grpcserver.StorageDeleteRequest.class, com.unionbankng.future.futureutilityservice.grpcserver.StorageDeleteRequest.Builder.class);
  }

  public static final int IDENTIFIER_FIELD_NUMBER = 1;
  private volatile java.lang.Object identifier_;
  /**
   * <code>string identifier = 1;</code>
   * @return The identifier.
   */
  @java.lang.Override
  public java.lang.String getIdentifier() {
    java.lang.Object ref = identifier_;
    if (ref instanceof java.lang.String) {
      return (java.lang.String) ref;
    } else {
      com.google.protobuf.ByteString bs = 
          (com.google.protobuf.ByteString) ref;
      java.lang.String s = bs.toStringUtf8();
      identifier_ = s;
      return s;
    }
  }
  /**
   * <code>string identifier = 1;</code>
   * @return The bytes for identifier.
   */
  @java.lang.Override
  public com.google.protobuf.ByteString
      getIdentifierBytes() {
    java.lang.Object ref = identifier_;
    if (ref instanceof java.lang.String) {
      com.google.protobuf.ByteString b = 
          com.google.protobuf.ByteString.copyFromUtf8(
              (java.lang.String) ref);
      identifier_ = b;
      return b;
    } else {
      return (com.google.protobuf.ByteString) ref;
    }
  }

  public static final int BLOBTYPE_FIELD_NUMBER = 2;
  private int blobType_;
  /**
   * <code>.com.unionbankng.future.futureutilityservice.grpcserver.BlobType blobType = 2;</code>
   * @return The enum numeric value on the wire for blobType.
   */
  @java.lang.Override public int getBlobTypeValue() {
    return blobType_;
  }
  /**
   * <code>.com.unionbankng.future.futureutilityservice.grpcserver.BlobType blobType = 2;</code>
   * @return The blobType.
   */
  @java.lang.Override public com.unionbankng.future.futureutilityservice.grpcserver.BlobType getBlobType() {
    @SuppressWarnings("deprecation")
    com.unionbankng.future.futureutilityservice.grpcserver.BlobType result = com.unionbankng.future.futureutilityservice.grpcserver.BlobType.valueOf(blobType_);
    return result == null ? com.unionbankng.future.futureutilityservice.grpcserver.BlobType.UNRECOGNIZED : result;
  }

  private byte memoizedIsInitialized = -1;
  @java.lang.Override
  public final boolean isInitialized() {
    byte isInitialized = memoizedIsInitialized;
    if (isInitialized == 1) return true;
    if (isInitialized == 0) return false;

    memoizedIsInitialized = 1;
    return true;
  }

  @java.lang.Override
  public void writeTo(com.google.protobuf.CodedOutputStream output)
                      throws java.io.IOException {
    if (!getIdentifierBytes().isEmpty()) {
      com.google.protobuf.GeneratedMessageV3.writeString(output, 1, identifier_);
    }
    if (blobType_ != com.unionbankng.future.futureutilityservice.grpcserver.BlobType.VIDEO.getNumber()) {
      output.writeEnum(2, blobType_);
    }
    unknownFields.writeTo(output);
  }

  @java.lang.Override
  public int getSerializedSize() {
    int size = memoizedSize;
    if (size != -1) return size;

    size = 0;
    if (!getIdentifierBytes().isEmpty()) {
      size += com.google.protobuf.GeneratedMessageV3.computeStringSize(1, identifier_);
    }
    if (blobType_ != com.unionbankng.future.futureutilityservice.grpcserver.BlobType.VIDEO.getNumber()) {
      size += com.google.protobuf.CodedOutputStream
        .computeEnumSize(2, blobType_);
    }
    size += unknownFields.getSerializedSize();
    memoizedSize = size;
    return size;
  }

  @java.lang.Override
  public boolean equals(final java.lang.Object obj) {
    if (obj == this) {
     return true;
    }
    if (!(obj instanceof com.unionbankng.future.futureutilityservice.grpcserver.StorageDeleteRequest)) {
      return super.equals(obj);
    }
    com.unionbankng.future.futureutilityservice.grpcserver.StorageDeleteRequest other = (com.unionbankng.future.futureutilityservice.grpcserver.StorageDeleteRequest) obj;

    if (!getIdentifier()
        .equals(other.getIdentifier())) return false;
    if (blobType_ != other.blobType_) return false;
    if (!unknownFields.equals(other.unknownFields)) return false;
    return true;
  }

  @java.lang.Override
  public int hashCode() {
    if (memoizedHashCode != 0) {
      return memoizedHashCode;
    }
    int hash = 41;
    hash = (19 * hash) + getDescriptor().hashCode();
    hash = (37 * hash) + IDENTIFIER_FIELD_NUMBER;
    hash = (53 * hash) + getIdentifier().hashCode();
    hash = (37 * hash) + BLOBTYPE_FIELD_NUMBER;
    hash = (53 * hash) + blobType_;
    hash = (29 * hash) + unknownFields.hashCode();
    memoizedHashCode = hash;
    return hash;
  }

  public static com.unionbankng.future.futureutilityservice.grpcserver.StorageDeleteRequest parseFrom(
      java.nio.ByteBuffer data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static com.unionbankng.future.futureutilityservice.grpcserver.StorageDeleteRequest parseFrom(
      java.nio.ByteBuffer data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static com.unionbankng.future.futureutilityservice.grpcserver.StorageDeleteRequest parseFrom(
      com.google.protobuf.ByteString data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static com.unionbankng.future.futureutilityservice.grpcserver.StorageDeleteRequest parseFrom(
      com.google.protobuf.ByteString data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static com.unionbankng.future.futureutilityservice.grpcserver.StorageDeleteRequest parseFrom(byte[] data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static com.unionbankng.future.futureutilityservice.grpcserver.StorageDeleteRequest parseFrom(
      byte[] data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static com.unionbankng.future.futureutilityservice.grpcserver.StorageDeleteRequest parseFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static com.unionbankng.future.futureutilityservice.grpcserver.StorageDeleteRequest parseFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input, extensionRegistry);
  }
  public static com.unionbankng.future.futureutilityservice.grpcserver.StorageDeleteRequest parseDelimitedFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input);
  }
  public static com.unionbankng.future.futureutilityservice.grpcserver.StorageDeleteRequest parseDelimitedFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
  }
  public static com.unionbankng.future.futureutilityservice.grpcserver.StorageDeleteRequest parseFrom(
      com.google.protobuf.CodedInputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static com.unionbankng.future.futureutilityservice.grpcserver.StorageDeleteRequest parseFrom(
      com.google.protobuf.CodedInputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input, extensionRegistry);
  }

  @java.lang.Override
  public Builder newBuilderForType() { return newBuilder(); }
  public static Builder newBuilder() {
    return DEFAULT_INSTANCE.toBuilder();
  }
  public static Builder newBuilder(com.unionbankng.future.futureutilityservice.grpcserver.StorageDeleteRequest prototype) {
    return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
  }
  @java.lang.Override
  public Builder toBuilder() {
    return this == DEFAULT_INSTANCE
        ? new Builder() : new Builder().mergeFrom(this);
  }

  @java.lang.Override
  protected Builder newBuilderForType(
      com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
    Builder builder = new Builder(parent);
    return builder;
  }
  /**
   * Protobuf type {@code com.unionbankng.future.futureutilityservice.grpcserver.StorageDeleteRequest}
   */
  public static final class Builder extends
      com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
      // @@protoc_insertion_point(builder_implements:com.unionbankng.future.futureutilityservice.grpcserver.StorageDeleteRequest)
      com.unionbankng.future.futureutilityservice.grpcserver.StorageDeleteRequestOrBuilder {
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return com.unionbankng.future.futureutilityservice.grpcserver.BlobStorageServiceOuterClass.internal_static_com_unionbankng_future_futureutilityservice_grpcserver_StorageDeleteRequest_descriptor;
    }

    @java.lang.Override
    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return com.unionbankng.future.futureutilityservice.grpcserver.BlobStorageServiceOuterClass.internal_static_com_unionbankng_future_futureutilityservice_grpcserver_StorageDeleteRequest_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              com.unionbankng.future.futureutilityservice.grpcserver.StorageDeleteRequest.class, com.unionbankng.future.futureutilityservice.grpcserver.StorageDeleteRequest.Builder.class);
    }

    // Construct using com.unionbankng.future.futureutilityservice.grpcserver.StorageDeleteRequest.newBuilder()
    private Builder() {
      maybeForceBuilderInitialization();
    }

    private Builder(
        com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
      super(parent);
      maybeForceBuilderInitialization();
    }
    private void maybeForceBuilderInitialization() {
      if (com.google.protobuf.GeneratedMessageV3
              .alwaysUseFieldBuilders) {
      }
    }
    @java.lang.Override
    public Builder clear() {
      super.clear();
      identifier_ = "";

      blobType_ = 0;

      return this;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.Descriptor
        getDescriptorForType() {
      return com.unionbankng.future.futureutilityservice.grpcserver.BlobStorageServiceOuterClass.internal_static_com_unionbankng_future_futureutilityservice_grpcserver_StorageDeleteRequest_descriptor;
    }

    @java.lang.Override
    public com.unionbankng.future.futureutilityservice.grpcserver.StorageDeleteRequest getDefaultInstanceForType() {
      return com.unionbankng.future.futureutilityservice.grpcserver.StorageDeleteRequest.getDefaultInstance();
    }

    @java.lang.Override
    public com.unionbankng.future.futureutilityservice.grpcserver.StorageDeleteRequest build() {
      com.unionbankng.future.futureutilityservice.grpcserver.StorageDeleteRequest result = buildPartial();
      if (!result.isInitialized()) {
        throw newUninitializedMessageException(result);
      }
      return result;
    }

    @java.lang.Override
    public com.unionbankng.future.futureutilityservice.grpcserver.StorageDeleteRequest buildPartial() {
      com.unionbankng.future.futureutilityservice.grpcserver.StorageDeleteRequest result = new com.unionbankng.future.futureutilityservice.grpcserver.StorageDeleteRequest(this);
      result.identifier_ = identifier_;
      result.blobType_ = blobType_;
      onBuilt();
      return result;
    }

    @java.lang.Override
    public Builder clone() {
      return super.clone();
    }
    @java.lang.Override
    public Builder setField(
        com.google.protobuf.Descriptors.FieldDescriptor field,
        java.lang.Object value) {
      return super.setField(field, value);
    }
    @java.lang.Override
    public Builder clearField(
        com.google.protobuf.Descriptors.FieldDescriptor field) {
      return super.clearField(field);
    }
    @java.lang.Override
    public Builder clearOneof(
        com.google.protobuf.Descriptors.OneofDescriptor oneof) {
      return super.clearOneof(oneof);
    }
    @java.lang.Override
    public Builder setRepeatedField(
        com.google.protobuf.Descriptors.FieldDescriptor field,
        int index, java.lang.Object value) {
      return super.setRepeatedField(field, index, value);
    }
    @java.lang.Override
    public Builder addRepeatedField(
        com.google.protobuf.Descriptors.FieldDescriptor field,
        java.lang.Object value) {
      return super.addRepeatedField(field, value);
    }
    @java.lang.Override
    public Builder mergeFrom(com.google.protobuf.Message other) {
      if (other instanceof com.unionbankng.future.futureutilityservice.grpcserver.StorageDeleteRequest) {
        return mergeFrom((com.unionbankng.future.futureutilityservice.grpcserver.StorageDeleteRequest)other);
      } else {
        super.mergeFrom(other);
        return this;
      }
    }

    public Builder mergeFrom(com.unionbankng.future.futureutilityservice.grpcserver.StorageDeleteRequest other) {
      if (other == com.unionbankng.future.futureutilityservice.grpcserver.StorageDeleteRequest.getDefaultInstance()) return this;
      if (!other.getIdentifier().isEmpty()) {
        identifier_ = other.identifier_;
        onChanged();
      }
      if (other.blobType_ != 0) {
        setBlobTypeValue(other.getBlobTypeValue());
      }
      this.mergeUnknownFields(other.unknownFields);
      onChanged();
      return this;
    }

    @java.lang.Override
    public final boolean isInitialized() {
      return true;
    }

    @java.lang.Override
    public Builder mergeFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      com.unionbankng.future.futureutilityservice.grpcserver.StorageDeleteRequest parsedMessage = null;
      try {
        parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
        parsedMessage = (com.unionbankng.future.futureutilityservice.grpcserver.StorageDeleteRequest) e.getUnfinishedMessage();
        throw e.unwrapIOException();
      } finally {
        if (parsedMessage != null) {
          mergeFrom(parsedMessage);
        }
      }
      return this;
    }

    private java.lang.Object identifier_ = "";
    /**
     * <code>string identifier = 1;</code>
     * @return The identifier.
     */
    public java.lang.String getIdentifier() {
      java.lang.Object ref = identifier_;
      if (!(ref instanceof java.lang.String)) {
        com.google.protobuf.ByteString bs =
            (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        identifier_ = s;
        return s;
      } else {
        return (java.lang.String) ref;
      }
    }
    /**
     * <code>string identifier = 1;</code>
     * @return The bytes for identifier.
     */
    public com.google.protobuf.ByteString
        getIdentifierBytes() {
      java.lang.Object ref = identifier_;
      if (ref instanceof String) {
        com.google.protobuf.ByteString b = 
            com.google.protobuf.ByteString.copyFromUtf8(
                (java.lang.String) ref);
        identifier_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }
    /**
     * <code>string identifier = 1;</code>
     * @param value The identifier to set.
     * @return This builder for chaining.
     */
    public Builder setIdentifier(
        java.lang.String value) {
      if (value == null) {
    throw new NullPointerException();
  }
  
      identifier_ = value;
      onChanged();
      return this;
    }
    /**
     * <code>string identifier = 1;</code>
     * @return This builder for chaining.
     */
    public Builder clearIdentifier() {
      
      identifier_ = getDefaultInstance().getIdentifier();
      onChanged();
      return this;
    }
    /**
     * <code>string identifier = 1;</code>
     * @param value The bytes for identifier to set.
     * @return This builder for chaining.
     */
    public Builder setIdentifierBytes(
        com.google.protobuf.ByteString value) {
      if (value == null) {
    throw new NullPointerException();
  }
  checkByteStringIsUtf8(value);
      
      identifier_ = value;
      onChanged();
      return this;
    }

    private int blobType_ = 0;
    /**
     * <code>.com.unionbankng.future.futureutilityservice.grpcserver.BlobType blobType = 2;</code>
     * @return The enum numeric value on the wire for blobType.
     */
    @java.lang.Override public int getBlobTypeValue() {
      return blobType_;
    }
    /**
     * <code>.com.unionbankng.future.futureutilityservice.grpcserver.BlobType blobType = 2;</code>
     * @param value The enum numeric value on the wire for blobType to set.
     * @return This builder for chaining.
     */
    public Builder setBlobTypeValue(int value) {
      
      blobType_ = value;
      onChanged();
      return this;
    }
    /**
     * <code>.com.unionbankng.future.futureutilityservice.grpcserver.BlobType blobType = 2;</code>
     * @return The blobType.
     */
    @java.lang.Override
    public com.unionbankng.future.futureutilityservice.grpcserver.BlobType getBlobType() {
      @SuppressWarnings("deprecation")
      com.unionbankng.future.futureutilityservice.grpcserver.BlobType result = com.unionbankng.future.futureutilityservice.grpcserver.BlobType.valueOf(blobType_);
      return result == null ? com.unionbankng.future.futureutilityservice.grpcserver.BlobType.UNRECOGNIZED : result;
    }
    /**
     * <code>.com.unionbankng.future.futureutilityservice.grpcserver.BlobType blobType = 2;</code>
     * @param value The blobType to set.
     * @return This builder for chaining.
     */
    public Builder setBlobType(com.unionbankng.future.futureutilityservice.grpcserver.BlobType value) {
      if (value == null) {
        throw new NullPointerException();
      }
      
      blobType_ = value.getNumber();
      onChanged();
      return this;
    }
    /**
     * <code>.com.unionbankng.future.futureutilityservice.grpcserver.BlobType blobType = 2;</code>
     * @return This builder for chaining.
     */
    public Builder clearBlobType() {
      
      blobType_ = 0;
      onChanged();
      return this;
    }
    @java.lang.Override
    public final Builder setUnknownFields(
        final com.google.protobuf.UnknownFieldSet unknownFields) {
      return super.setUnknownFields(unknownFields);
    }

    @java.lang.Override
    public final Builder mergeUnknownFields(
        final com.google.protobuf.UnknownFieldSet unknownFields) {
      return super.mergeUnknownFields(unknownFields);
    }


    // @@protoc_insertion_point(builder_scope:com.unionbankng.future.futureutilityservice.grpcserver.StorageDeleteRequest)
  }

  // @@protoc_insertion_point(class_scope:com.unionbankng.future.futureutilityservice.grpcserver.StorageDeleteRequest)
  private static final com.unionbankng.future.futureutilityservice.grpcserver.StorageDeleteRequest DEFAULT_INSTANCE;
  static {
    DEFAULT_INSTANCE = new com.unionbankng.future.futureutilityservice.grpcserver.StorageDeleteRequest();
  }

  public static com.unionbankng.future.futureutilityservice.grpcserver.StorageDeleteRequest getDefaultInstance() {
    return DEFAULT_INSTANCE;
  }

  private static final com.google.protobuf.Parser<StorageDeleteRequest>
      PARSER = new com.google.protobuf.AbstractParser<StorageDeleteRequest>() {
    @java.lang.Override
    public StorageDeleteRequest parsePartialFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return new StorageDeleteRequest(input, extensionRegistry);
    }
  };

  public static com.google.protobuf.Parser<StorageDeleteRequest> parser() {
    return PARSER;
  }

  @java.lang.Override
  public com.google.protobuf.Parser<StorageDeleteRequest> getParserForType() {
    return PARSER;
  }

  @java.lang.Override
  public com.unionbankng.future.futureutilityservice.grpcserver.StorageDeleteRequest getDefaultInstanceForType() {
    return DEFAULT_INSTANCE;
  }

}

