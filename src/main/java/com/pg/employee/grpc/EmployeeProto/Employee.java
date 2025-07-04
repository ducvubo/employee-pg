// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: employee.proto

package com.pg.employee.grpc.EmployeeProto;

import com.pg.employee.grpc.api.Api;

public final class Employee {
  private Employee() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistryLite registry) {
  }

  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
    registerAllExtensions(
        (com.google.protobuf.ExtensionRegistryLite) registry);
  }
  public interface ReqFindOneEmployByIdOrBuilder extends
      // @@protoc_insertion_point(interface_extends:EmployeeProto.ReqFindOneEmployById)
      com.google.protobuf.MessageOrBuilder {

    /**
     * <code>string id = 1;</code>
     * @return The id.
     */
    String getId();
    /**
     * <code>string id = 1;</code>
     * @return The bytes for id.
     */
    com.google.protobuf.ByteString
        getIdBytes();

    /**
     * <code>string eplResId = 2;</code>
     * @return The eplResId.
     */
    String getEplResId();
    /**
     * <code>string eplResId = 2;</code>
     * @return The bytes for eplResId.
     */
    com.google.protobuf.ByteString
        getEplResIdBytes();
  }
  /**
   * Protobuf type {@code EmployeeProto.ReqFindOneEmployById}
   */
  public static final class ReqFindOneEmployById extends
      com.google.protobuf.GeneratedMessageV3 implements
      // @@protoc_insertion_point(message_implements:EmployeeProto.ReqFindOneEmployById)
      ReqFindOneEmployByIdOrBuilder {
  private static final long serialVersionUID = 0L;
    // Use ReqFindOneEmployById.newBuilder() to construct.
    private ReqFindOneEmployById(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
      super(builder);
    }
    private ReqFindOneEmployById() {
      id_ = "";
      eplResId_ = "";
    }

    @Override
    @SuppressWarnings({"unused"})
    protected Object newInstance(
        UnusedPrivateParameter unused) {
      return new ReqFindOneEmployById();
    }

    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return Employee.internal_static_EmployeeProto_ReqFindOneEmployById_descriptor;
    }

    @Override
    protected FieldAccessorTable
        internalGetFieldAccessorTable() {
      return Employee.internal_static_EmployeeProto_ReqFindOneEmployById_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              ReqFindOneEmployById.class, Builder.class);
    }

    public static final int ID_FIELD_NUMBER = 1;
    @SuppressWarnings("serial")
    private volatile Object id_ = "";
    /**
     * <code>string id = 1;</code>
     * @return The id.
     */
    @Override
    public String getId() {
      Object ref = id_;
      if (ref instanceof String) {
        return (String) ref;
      } else {
        com.google.protobuf.ByteString bs = 
            (com.google.protobuf.ByteString) ref;
        String s = bs.toStringUtf8();
        id_ = s;
        return s;
      }
    }
    /**
     * <code>string id = 1;</code>
     * @return The bytes for id.
     */
    @Override
    public com.google.protobuf.ByteString
        getIdBytes() {
      Object ref = id_;
      if (ref instanceof String) {
        com.google.protobuf.ByteString b = 
            com.google.protobuf.ByteString.copyFromUtf8(
                (String) ref);
        id_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }

    public static final int EPLRESID_FIELD_NUMBER = 2;
    @SuppressWarnings("serial")
    private volatile Object eplResId_ = "";
    /**
     * <code>string eplResId = 2;</code>
     * @return The eplResId.
     */
    @Override
    public String getEplResId() {
      Object ref = eplResId_;
      if (ref instanceof String) {
        return (String) ref;
      } else {
        com.google.protobuf.ByteString bs = 
            (com.google.protobuf.ByteString) ref;
        String s = bs.toStringUtf8();
        eplResId_ = s;
        return s;
      }
    }
    /**
     * <code>string eplResId = 2;</code>
     * @return The bytes for eplResId.
     */
    @Override
    public com.google.protobuf.ByteString
        getEplResIdBytes() {
      Object ref = eplResId_;
      if (ref instanceof String) {
        com.google.protobuf.ByteString b = 
            com.google.protobuf.ByteString.copyFromUtf8(
                (String) ref);
        eplResId_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }

    private byte memoizedIsInitialized = -1;
    @Override
    public final boolean isInitialized() {
      byte isInitialized = memoizedIsInitialized;
      if (isInitialized == 1) return true;
      if (isInitialized == 0) return false;

      memoizedIsInitialized = 1;
      return true;
    }

    @Override
    public void writeTo(com.google.protobuf.CodedOutputStream output)
                        throws java.io.IOException {
      if (!com.google.protobuf.GeneratedMessageV3.isStringEmpty(id_)) {
        com.google.protobuf.GeneratedMessageV3.writeString(output, 1, id_);
      }
      if (!com.google.protobuf.GeneratedMessageV3.isStringEmpty(eplResId_)) {
        com.google.protobuf.GeneratedMessageV3.writeString(output, 2, eplResId_);
      }
      getUnknownFields().writeTo(output);
    }

    @Override
    public int getSerializedSize() {
      int size = memoizedSize;
      if (size != -1) return size;

      size = 0;
      if (!com.google.protobuf.GeneratedMessageV3.isStringEmpty(id_)) {
        size += com.google.protobuf.GeneratedMessageV3.computeStringSize(1, id_);
      }
      if (!com.google.protobuf.GeneratedMessageV3.isStringEmpty(eplResId_)) {
        size += com.google.protobuf.GeneratedMessageV3.computeStringSize(2, eplResId_);
      }
      size += getUnknownFields().getSerializedSize();
      memoizedSize = size;
      return size;
    }

    @Override
    public boolean equals(final Object obj) {
      if (obj == this) {
       return true;
      }
      if (!(obj instanceof ReqFindOneEmployById)) {
        return super.equals(obj);
      }
      ReqFindOneEmployById other = (ReqFindOneEmployById) obj;

      if (!getId()
          .equals(other.getId())) return false;
      if (!getEplResId()
          .equals(other.getEplResId())) return false;
      if (!getUnknownFields().equals(other.getUnknownFields())) return false;
      return true;
    }

    @Override
    public int hashCode() {
      if (memoizedHashCode != 0) {
        return memoizedHashCode;
      }
      int hash = 41;
      hash = (19 * hash) + getDescriptor().hashCode();
      hash = (37 * hash) + ID_FIELD_NUMBER;
      hash = (53 * hash) + getId().hashCode();
      hash = (37 * hash) + EPLRESID_FIELD_NUMBER;
      hash = (53 * hash) + getEplResId().hashCode();
      hash = (29 * hash) + getUnknownFields().hashCode();
      memoizedHashCode = hash;
      return hash;
    }

    public static ReqFindOneEmployById parseFrom(
        java.nio.ByteBuffer data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static ReqFindOneEmployById parseFrom(
        java.nio.ByteBuffer data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static ReqFindOneEmployById parseFrom(
        com.google.protobuf.ByteString data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static ReqFindOneEmployById parseFrom(
        com.google.protobuf.ByteString data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static ReqFindOneEmployById parseFrom(byte[] data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static ReqFindOneEmployById parseFrom(
        byte[] data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static ReqFindOneEmployById parseFrom(java.io.InputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input);
    }
    public static ReqFindOneEmployById parseFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input, extensionRegistry);
    }

    public static ReqFindOneEmployById parseDelimitedFrom(java.io.InputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseDelimitedWithIOException(PARSER, input);
    }

    public static ReqFindOneEmployById parseDelimitedFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
    }
    public static ReqFindOneEmployById parseFrom(
        com.google.protobuf.CodedInputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input);
    }
    public static ReqFindOneEmployById parseFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input, extensionRegistry);
    }

    @Override
    public Builder newBuilderForType() { return newBuilder(); }
    public static Builder newBuilder() {
      return DEFAULT_INSTANCE.toBuilder();
    }
    public static Builder newBuilder(ReqFindOneEmployById prototype) {
      return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
    }
    @Override
    public Builder toBuilder() {
      return this == DEFAULT_INSTANCE
          ? new Builder() : new Builder().mergeFrom(this);
    }

    @Override
    protected Builder newBuilderForType(
        BuilderParent parent) {
      Builder builder = new Builder(parent);
      return builder;
    }
    /**
     * Protobuf type {@code EmployeeProto.ReqFindOneEmployById}
     */
    public static final class Builder extends
        com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
        // @@protoc_insertion_point(builder_implements:EmployeeProto.ReqFindOneEmployById)
        ReqFindOneEmployByIdOrBuilder {
      public static final com.google.protobuf.Descriptors.Descriptor
          getDescriptor() {
        return Employee.internal_static_EmployeeProto_ReqFindOneEmployById_descriptor;
      }

      @Override
      protected FieldAccessorTable
          internalGetFieldAccessorTable() {
        return Employee.internal_static_EmployeeProto_ReqFindOneEmployById_fieldAccessorTable
            .ensureFieldAccessorsInitialized(
                ReqFindOneEmployById.class, Builder.class);
      }

      // Construct using EmployeeProto.Employee.ReqFindOneEmployById.newBuilder()
      private Builder() {

      }

      private Builder(
          BuilderParent parent) {
        super(parent);

      }
      @Override
      public Builder clear() {
        super.clear();
        bitField0_ = 0;
        id_ = "";
        eplResId_ = "";
        return this;
      }

      @Override
      public com.google.protobuf.Descriptors.Descriptor
          getDescriptorForType() {
        return Employee.internal_static_EmployeeProto_ReqFindOneEmployById_descriptor;
      }

      @Override
      public ReqFindOneEmployById getDefaultInstanceForType() {
        return ReqFindOneEmployById.getDefaultInstance();
      }

      @Override
      public ReqFindOneEmployById build() {
        ReqFindOneEmployById result = buildPartial();
        if (!result.isInitialized()) {
          throw newUninitializedMessageException(result);
        }
        return result;
      }

      @Override
      public ReqFindOneEmployById buildPartial() {
        ReqFindOneEmployById result = new ReqFindOneEmployById(this);
        if (bitField0_ != 0) { buildPartial0(result); }
        onBuilt();
        return result;
      }

      private void buildPartial0(ReqFindOneEmployById result) {
        int from_bitField0_ = bitField0_;
        if (((from_bitField0_ & 0x00000001) != 0)) {
          result.id_ = id_;
        }
        if (((from_bitField0_ & 0x00000002) != 0)) {
          result.eplResId_ = eplResId_;
        }
      }

      @Override
      public Builder clone() {
        return super.clone();
      }
      @Override
      public Builder setField(
          com.google.protobuf.Descriptors.FieldDescriptor field,
          Object value) {
        return super.setField(field, value);
      }
      @Override
      public Builder clearField(
          com.google.protobuf.Descriptors.FieldDescriptor field) {
        return super.clearField(field);
      }
      @Override
      public Builder clearOneof(
          com.google.protobuf.Descriptors.OneofDescriptor oneof) {
        return super.clearOneof(oneof);
      }
      @Override
      public Builder setRepeatedField(
          com.google.protobuf.Descriptors.FieldDescriptor field,
          int index, Object value) {
        return super.setRepeatedField(field, index, value);
      }
      @Override
      public Builder addRepeatedField(
          com.google.protobuf.Descriptors.FieldDescriptor field,
          Object value) {
        return super.addRepeatedField(field, value);
      }
      @Override
      public Builder mergeFrom(com.google.protobuf.Message other) {
        if (other instanceof ReqFindOneEmployById) {
          return mergeFrom((ReqFindOneEmployById)other);
        } else {
          super.mergeFrom(other);
          return this;
        }
      }

      public Builder mergeFrom(ReqFindOneEmployById other) {
        if (other == ReqFindOneEmployById.getDefaultInstance()) return this;
        if (!other.getId().isEmpty()) {
          id_ = other.id_;
          bitField0_ |= 0x00000001;
          onChanged();
        }
        if (!other.getEplResId().isEmpty()) {
          eplResId_ = other.eplResId_;
          bitField0_ |= 0x00000002;
          onChanged();
        }
        this.mergeUnknownFields(other.getUnknownFields());
        onChanged();
        return this;
      }

      @Override
      public final boolean isInitialized() {
        return true;
      }

      @Override
      public Builder mergeFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws java.io.IOException {
        if (extensionRegistry == null) {
          throw new NullPointerException();
        }
        try {
          boolean done = false;
          while (!done) {
            int tag = input.readTag();
            switch (tag) {
              case 0:
                done = true;
                break;
              case 10: {
                id_ = input.readStringRequireUtf8();
                bitField0_ |= 0x00000001;
                break;
              } // case 10
              case 18: {
                eplResId_ = input.readStringRequireUtf8();
                bitField0_ |= 0x00000002;
                break;
              } // case 18
              default: {
                if (!super.parseUnknownField(input, extensionRegistry, tag)) {
                  done = true; // was an endgroup tag
                }
                break;
              } // default:
            } // switch (tag)
          } // while (!done)
        } catch (com.google.protobuf.InvalidProtocolBufferException e) {
          throw e.unwrapIOException();
        } finally {
          onChanged();
        } // finally
        return this;
      }
      private int bitField0_;

      private Object id_ = "";
      /**
       * <code>string id = 1;</code>
       * @return The id.
       */
      public String getId() {
        Object ref = id_;
        if (!(ref instanceof String)) {
          com.google.protobuf.ByteString bs =
              (com.google.protobuf.ByteString) ref;
          String s = bs.toStringUtf8();
          id_ = s;
          return s;
        } else {
          return (String) ref;
        }
      }
      /**
       * <code>string id = 1;</code>
       * @return The bytes for id.
       */
      public com.google.protobuf.ByteString
          getIdBytes() {
        Object ref = id_;
        if (ref instanceof String) {
          com.google.protobuf.ByteString b = 
              com.google.protobuf.ByteString.copyFromUtf8(
                  (String) ref);
          id_ = b;
          return b;
        } else {
          return (com.google.protobuf.ByteString) ref;
        }
      }
      /**
       * <code>string id = 1;</code>
       * @param value The id to set.
       * @return This builder for chaining.
       */
      public Builder setId(
          String value) {
        if (value == null) { throw new NullPointerException(); }
        id_ = value;
        bitField0_ |= 0x00000001;
        onChanged();
        return this;
      }
      /**
       * <code>string id = 1;</code>
       * @return This builder for chaining.
       */
      public Builder clearId() {
        id_ = getDefaultInstance().getId();
        bitField0_ = (bitField0_ & ~0x00000001);
        onChanged();
        return this;
      }
      /**
       * <code>string id = 1;</code>
       * @param value The bytes for id to set.
       * @return This builder for chaining.
       */
      public Builder setIdBytes(
          com.google.protobuf.ByteString value) {
        if (value == null) { throw new NullPointerException(); }
        checkByteStringIsUtf8(value);
        id_ = value;
        bitField0_ |= 0x00000001;
        onChanged();
        return this;
      }

      private Object eplResId_ = "";
      /**
       * <code>string eplResId = 2;</code>
       * @return The eplResId.
       */
      public String getEplResId() {
        Object ref = eplResId_;
        if (!(ref instanceof String)) {
          com.google.protobuf.ByteString bs =
              (com.google.protobuf.ByteString) ref;
          String s = bs.toStringUtf8();
          eplResId_ = s;
          return s;
        } else {
          return (String) ref;
        }
      }
      /**
       * <code>string eplResId = 2;</code>
       * @return The bytes for eplResId.
       */
      public com.google.protobuf.ByteString
          getEplResIdBytes() {
        Object ref = eplResId_;
        if (ref instanceof String) {
          com.google.protobuf.ByteString b = 
              com.google.protobuf.ByteString.copyFromUtf8(
                  (String) ref);
          eplResId_ = b;
          return b;
        } else {
          return (com.google.protobuf.ByteString) ref;
        }
      }
      /**
       * <code>string eplResId = 2;</code>
       * @param value The eplResId to set.
       * @return This builder for chaining.
       */
      public Builder setEplResId(
          String value) {
        if (value == null) { throw new NullPointerException(); }
        eplResId_ = value;
        bitField0_ |= 0x00000002;
        onChanged();
        return this;
      }
      /**
       * <code>string eplResId = 2;</code>
       * @return This builder for chaining.
       */
      public Builder clearEplResId() {
        eplResId_ = getDefaultInstance().getEplResId();
        bitField0_ = (bitField0_ & ~0x00000002);
        onChanged();
        return this;
      }
      /**
       * <code>string eplResId = 2;</code>
       * @param value The bytes for eplResId to set.
       * @return This builder for chaining.
       */
      public Builder setEplResIdBytes(
          com.google.protobuf.ByteString value) {
        if (value == null) { throw new NullPointerException(); }
        checkByteStringIsUtf8(value);
        eplResId_ = value;
        bitField0_ |= 0x00000002;
        onChanged();
        return this;
      }
      @Override
      public final Builder setUnknownFields(
          final com.google.protobuf.UnknownFieldSet unknownFields) {
        return super.setUnknownFields(unknownFields);
      }

      @Override
      public final Builder mergeUnknownFields(
          final com.google.protobuf.UnknownFieldSet unknownFields) {
        return super.mergeUnknownFields(unknownFields);
      }


      // @@protoc_insertion_point(builder_scope:EmployeeProto.ReqFindOneEmployById)
    }

    // @@protoc_insertion_point(class_scope:EmployeeProto.ReqFindOneEmployById)
    private static final ReqFindOneEmployById DEFAULT_INSTANCE;
    static {
      DEFAULT_INSTANCE = new ReqFindOneEmployById();
    }

    public static ReqFindOneEmployById getDefaultInstance() {
      return DEFAULT_INSTANCE;
    }

    private static final com.google.protobuf.Parser<ReqFindOneEmployById>
        PARSER = new com.google.protobuf.AbstractParser<ReqFindOneEmployById>() {
      @Override
      public ReqFindOneEmployById parsePartialFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws com.google.protobuf.InvalidProtocolBufferException {
        Builder builder = newBuilder();
        try {
          builder.mergeFrom(input, extensionRegistry);
        } catch (com.google.protobuf.InvalidProtocolBufferException e) {
          throw e.setUnfinishedMessage(builder.buildPartial());
        } catch (com.google.protobuf.UninitializedMessageException e) {
          throw e.asInvalidProtocolBufferException().setUnfinishedMessage(builder.buildPartial());
        } catch (java.io.IOException e) {
          throw new com.google.protobuf.InvalidProtocolBufferException(e)
              .setUnfinishedMessage(builder.buildPartial());
        }
        return builder.buildPartial();
      }
    };

    public static com.google.protobuf.Parser<ReqFindOneEmployById> parser() {
      return PARSER;
    }

    @Override
    public com.google.protobuf.Parser<ReqFindOneEmployById> getParserForType() {
      return PARSER;
    }

    @Override
    public ReqFindOneEmployById getDefaultInstanceForType() {
      return DEFAULT_INSTANCE;
    }

  }

  private static final com.google.protobuf.Descriptors.Descriptor
    internal_static_EmployeeProto_ReqFindOneEmployById_descriptor;
  private static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_EmployeeProto_ReqFindOneEmployById_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static  com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    String[] descriptorData = {
      "\n\016employee.proto\022\rEmployeeProto\032\tapi.pro" +
      "to\"4\n\024ReqFindOneEmployById\022\n\n\002id\030\001 \001(\t\022\020" +
      "\n\010eplResId\030\002 \001(\t2f\n\023EmployeeServiceGprc\022" +
      "O\n\023findOneEmployeeById\022#.EmployeeProto.R" +
      "eqFindOneEmployById\032\021.api.IBackendGRPC\"\000" +
      "b\006proto3"
    };
    descriptor = com.google.protobuf.Descriptors.FileDescriptor
      .internalBuildGeneratedFileFrom(descriptorData,
        new com.google.protobuf.Descriptors.FileDescriptor[] {
          Api.getDescriptor(),
        });
    internal_static_EmployeeProto_ReqFindOneEmployById_descriptor =
      getDescriptor().getMessageTypes().get(0);
    internal_static_EmployeeProto_ReqFindOneEmployById_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_EmployeeProto_ReqFindOneEmployById_descriptor,
        new String[] { "Id", "EplResId", });
    Api.getDescriptor();
  }

  // @@protoc_insertion_point(outer_class_scope)
}
