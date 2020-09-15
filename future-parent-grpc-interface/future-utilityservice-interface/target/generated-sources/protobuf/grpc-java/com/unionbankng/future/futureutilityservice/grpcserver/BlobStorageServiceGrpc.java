package com.unionbankng.future.futureutilityservice.grpcserver;

import static io.grpc.MethodDescriptor.generateFullMethodName;
import static io.grpc.stub.ClientCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ClientCalls.asyncClientStreamingCall;
import static io.grpc.stub.ClientCalls.asyncServerStreamingCall;
import static io.grpc.stub.ClientCalls.asyncUnaryCall;
import static io.grpc.stub.ClientCalls.blockingServerStreamingCall;
import static io.grpc.stub.ClientCalls.blockingUnaryCall;
import static io.grpc.stub.ClientCalls.futureUnaryCall;
import static io.grpc.stub.ServerCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ServerCalls.asyncClientStreamingCall;
import static io.grpc.stub.ServerCalls.asyncServerStreamingCall;
import static io.grpc.stub.ServerCalls.asyncUnaryCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedStreamingCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.32.1)",
    comments = "Source: BlobStorageService.proto")
public final class BlobStorageServiceGrpc {

  private BlobStorageServiceGrpc() {}

  public static final String SERVICE_NAME = "com.unionbankng.future.futureutilityservice.grpcserver.BlobStorageService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<com.unionbankng.future.futureutilityservice.grpcserver.StorageUploadRequest,
      com.unionbankng.future.futureutilityservice.grpcserver.StorageUploadResponse> getUploadMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "upload",
      requestType = com.unionbankng.future.futureutilityservice.grpcserver.StorageUploadRequest.class,
      responseType = com.unionbankng.future.futureutilityservice.grpcserver.StorageUploadResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.unionbankng.future.futureutilityservice.grpcserver.StorageUploadRequest,
      com.unionbankng.future.futureutilityservice.grpcserver.StorageUploadResponse> getUploadMethod() {
    io.grpc.MethodDescriptor<com.unionbankng.future.futureutilityservice.grpcserver.StorageUploadRequest, com.unionbankng.future.futureutilityservice.grpcserver.StorageUploadResponse> getUploadMethod;
    if ((getUploadMethod = BlobStorageServiceGrpc.getUploadMethod) == null) {
      synchronized (BlobStorageServiceGrpc.class) {
        if ((getUploadMethod = BlobStorageServiceGrpc.getUploadMethod) == null) {
          BlobStorageServiceGrpc.getUploadMethod = getUploadMethod =
              io.grpc.MethodDescriptor.<com.unionbankng.future.futureutilityservice.grpcserver.StorageUploadRequest, com.unionbankng.future.futureutilityservice.grpcserver.StorageUploadResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "upload"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.unionbankng.future.futureutilityservice.grpcserver.StorageUploadRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.unionbankng.future.futureutilityservice.grpcserver.StorageUploadResponse.getDefaultInstance()))
              .setSchemaDescriptor(new BlobStorageServiceMethodDescriptorSupplier("upload"))
              .build();
        }
      }
    }
    return getUploadMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.unionbankng.future.futureutilityservice.grpcserver.StorageDeleteRequest,
      com.unionbankng.future.futureutilityservice.grpcserver.StorageDeleteResponse> getDeleteMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "delete",
      requestType = com.unionbankng.future.futureutilityservice.grpcserver.StorageDeleteRequest.class,
      responseType = com.unionbankng.future.futureutilityservice.grpcserver.StorageDeleteResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.unionbankng.future.futureutilityservice.grpcserver.StorageDeleteRequest,
      com.unionbankng.future.futureutilityservice.grpcserver.StorageDeleteResponse> getDeleteMethod() {
    io.grpc.MethodDescriptor<com.unionbankng.future.futureutilityservice.grpcserver.StorageDeleteRequest, com.unionbankng.future.futureutilityservice.grpcserver.StorageDeleteResponse> getDeleteMethod;
    if ((getDeleteMethod = BlobStorageServiceGrpc.getDeleteMethod) == null) {
      synchronized (BlobStorageServiceGrpc.class) {
        if ((getDeleteMethod = BlobStorageServiceGrpc.getDeleteMethod) == null) {
          BlobStorageServiceGrpc.getDeleteMethod = getDeleteMethod =
              io.grpc.MethodDescriptor.<com.unionbankng.future.futureutilityservice.grpcserver.StorageDeleteRequest, com.unionbankng.future.futureutilityservice.grpcserver.StorageDeleteResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "delete"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.unionbankng.future.futureutilityservice.grpcserver.StorageDeleteRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.unionbankng.future.futureutilityservice.grpcserver.StorageDeleteResponse.getDefaultInstance()))
              .setSchemaDescriptor(new BlobStorageServiceMethodDescriptorSupplier("delete"))
              .build();
        }
      }
    }
    return getDeleteMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static BlobStorageServiceStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<BlobStorageServiceStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<BlobStorageServiceStub>() {
        @java.lang.Override
        public BlobStorageServiceStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new BlobStorageServiceStub(channel, callOptions);
        }
      };
    return BlobStorageServiceStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static BlobStorageServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<BlobStorageServiceBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<BlobStorageServiceBlockingStub>() {
        @java.lang.Override
        public BlobStorageServiceBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new BlobStorageServiceBlockingStub(channel, callOptions);
        }
      };
    return BlobStorageServiceBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static BlobStorageServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<BlobStorageServiceFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<BlobStorageServiceFutureStub>() {
        @java.lang.Override
        public BlobStorageServiceFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new BlobStorageServiceFutureStub(channel, callOptions);
        }
      };
    return BlobStorageServiceFutureStub.newStub(factory, channel);
  }

  /**
   */
  public static abstract class BlobStorageServiceImplBase implements io.grpc.BindableService {

    /**
     */
    public void upload(com.unionbankng.future.futureutilityservice.grpcserver.StorageUploadRequest request,
        io.grpc.stub.StreamObserver<com.unionbankng.future.futureutilityservice.grpcserver.StorageUploadResponse> responseObserver) {
      asyncUnimplementedUnaryCall(getUploadMethod(), responseObserver);
    }

    /**
     */
    public void delete(com.unionbankng.future.futureutilityservice.grpcserver.StorageDeleteRequest request,
        io.grpc.stub.StreamObserver<com.unionbankng.future.futureutilityservice.grpcserver.StorageDeleteResponse> responseObserver) {
      asyncUnimplementedUnaryCall(getDeleteMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getUploadMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                com.unionbankng.future.futureutilityservice.grpcserver.StorageUploadRequest,
                com.unionbankng.future.futureutilityservice.grpcserver.StorageUploadResponse>(
                  this, METHODID_UPLOAD)))
          .addMethod(
            getDeleteMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                com.unionbankng.future.futureutilityservice.grpcserver.StorageDeleteRequest,
                com.unionbankng.future.futureutilityservice.grpcserver.StorageDeleteResponse>(
                  this, METHODID_DELETE)))
          .build();
    }
  }

  /**
   */
  public static final class BlobStorageServiceStub extends io.grpc.stub.AbstractAsyncStub<BlobStorageServiceStub> {
    private BlobStorageServiceStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected BlobStorageServiceStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new BlobStorageServiceStub(channel, callOptions);
    }

    /**
     */
    public void upload(com.unionbankng.future.futureutilityservice.grpcserver.StorageUploadRequest request,
        io.grpc.stub.StreamObserver<com.unionbankng.future.futureutilityservice.grpcserver.StorageUploadResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getUploadMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void delete(com.unionbankng.future.futureutilityservice.grpcserver.StorageDeleteRequest request,
        io.grpc.stub.StreamObserver<com.unionbankng.future.futureutilityservice.grpcserver.StorageDeleteResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getDeleteMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class BlobStorageServiceBlockingStub extends io.grpc.stub.AbstractBlockingStub<BlobStorageServiceBlockingStub> {
    private BlobStorageServiceBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected BlobStorageServiceBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new BlobStorageServiceBlockingStub(channel, callOptions);
    }

    /**
     */
    public com.unionbankng.future.futureutilityservice.grpcserver.StorageUploadResponse upload(com.unionbankng.future.futureutilityservice.grpcserver.StorageUploadRequest request) {
      return blockingUnaryCall(
          getChannel(), getUploadMethod(), getCallOptions(), request);
    }

    /**
     */
    public com.unionbankng.future.futureutilityservice.grpcserver.StorageDeleteResponse delete(com.unionbankng.future.futureutilityservice.grpcserver.StorageDeleteRequest request) {
      return blockingUnaryCall(
          getChannel(), getDeleteMethod(), getCallOptions(), request);
    }
  }

  /**
   */
  public static final class BlobStorageServiceFutureStub extends io.grpc.stub.AbstractFutureStub<BlobStorageServiceFutureStub> {
    private BlobStorageServiceFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected BlobStorageServiceFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new BlobStorageServiceFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.unionbankng.future.futureutilityservice.grpcserver.StorageUploadResponse> upload(
        com.unionbankng.future.futureutilityservice.grpcserver.StorageUploadRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getUploadMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.unionbankng.future.futureutilityservice.grpcserver.StorageDeleteResponse> delete(
        com.unionbankng.future.futureutilityservice.grpcserver.StorageDeleteRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getDeleteMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_UPLOAD = 0;
  private static final int METHODID_DELETE = 1;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final BlobStorageServiceImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(BlobStorageServiceImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_UPLOAD:
          serviceImpl.upload((com.unionbankng.future.futureutilityservice.grpcserver.StorageUploadRequest) request,
              (io.grpc.stub.StreamObserver<com.unionbankng.future.futureutilityservice.grpcserver.StorageUploadResponse>) responseObserver);
          break;
        case METHODID_DELETE:
          serviceImpl.delete((com.unionbankng.future.futureutilityservice.grpcserver.StorageDeleteRequest) request,
              (io.grpc.stub.StreamObserver<com.unionbankng.future.futureutilityservice.grpcserver.StorageDeleteResponse>) responseObserver);
          break;
        default:
          throw new AssertionError();
      }
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        default:
          throw new AssertionError();
      }
    }
  }

  private static abstract class BlobStorageServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    BlobStorageServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return com.unionbankng.future.futureutilityservice.grpcserver.BlobStorageServiceOuterClass.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("BlobStorageService");
    }
  }

  private static final class BlobStorageServiceFileDescriptorSupplier
      extends BlobStorageServiceBaseDescriptorSupplier {
    BlobStorageServiceFileDescriptorSupplier() {}
  }

  private static final class BlobStorageServiceMethodDescriptorSupplier
      extends BlobStorageServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    BlobStorageServiceMethodDescriptorSupplier(String methodName) {
      this.methodName = methodName;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.MethodDescriptor getMethodDescriptor() {
      return getServiceDescriptor().findMethodByName(methodName);
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (BlobStorageServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new BlobStorageServiceFileDescriptorSupplier())
              .addMethod(getUploadMethod())
              .addMethod(getDeleteMethod())
              .build();
        }
      }
    }
    return result;
  }
}
