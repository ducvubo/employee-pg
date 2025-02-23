package EmployeeProto;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.65.1)",
    comments = "Source: employee.proto")
@io.grpc.stub.annotations.GrpcGenerated
public final class EmployeeServiceGprcGrpc {

  private EmployeeServiceGprcGrpc() {}

  public static final java.lang.String SERVICE_NAME = "EmployeeProto.EmployeeServiceGprc";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<EmployeeProto.Employee.ReqFindOneEmployById,
      api.Api.IBackendGRPC> getFindOneEmployeeByIdMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "findOneEmployeeById",
      requestType = EmployeeProto.Employee.ReqFindOneEmployById.class,
      responseType = api.Api.IBackendGRPC.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<EmployeeProto.Employee.ReqFindOneEmployById,
      api.Api.IBackendGRPC> getFindOneEmployeeByIdMethod() {
    io.grpc.MethodDescriptor<EmployeeProto.Employee.ReqFindOneEmployById, api.Api.IBackendGRPC> getFindOneEmployeeByIdMethod;
    if ((getFindOneEmployeeByIdMethod = EmployeeServiceGprcGrpc.getFindOneEmployeeByIdMethod) == null) {
      synchronized (EmployeeServiceGprcGrpc.class) {
        if ((getFindOneEmployeeByIdMethod = EmployeeServiceGprcGrpc.getFindOneEmployeeByIdMethod) == null) {
          EmployeeServiceGprcGrpc.getFindOneEmployeeByIdMethod = getFindOneEmployeeByIdMethod =
              io.grpc.MethodDescriptor.<EmployeeProto.Employee.ReqFindOneEmployById, api.Api.IBackendGRPC>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "findOneEmployeeById"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  EmployeeProto.Employee.ReqFindOneEmployById.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  api.Api.IBackendGRPC.getDefaultInstance()))
              .setSchemaDescriptor(new EmployeeServiceGprcMethodDescriptorSupplier("findOneEmployeeById"))
              .build();
        }
      }
    }
    return getFindOneEmployeeByIdMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static EmployeeServiceGprcStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<EmployeeServiceGprcStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<EmployeeServiceGprcStub>() {
        @java.lang.Override
        public EmployeeServiceGprcStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new EmployeeServiceGprcStub(channel, callOptions);
        }
      };
    return EmployeeServiceGprcStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static EmployeeServiceGprcBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<EmployeeServiceGprcBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<EmployeeServiceGprcBlockingStub>() {
        @java.lang.Override
        public EmployeeServiceGprcBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new EmployeeServiceGprcBlockingStub(channel, callOptions);
        }
      };
    return EmployeeServiceGprcBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static EmployeeServiceGprcFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<EmployeeServiceGprcFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<EmployeeServiceGprcFutureStub>() {
        @java.lang.Override
        public EmployeeServiceGprcFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new EmployeeServiceGprcFutureStub(channel, callOptions);
        }
      };
    return EmployeeServiceGprcFutureStub.newStub(factory, channel);
  }

  /**
   */
  public interface AsyncService {

    /**
     */
    default void findOneEmployeeById(EmployeeProto.Employee.ReqFindOneEmployById request,
        io.grpc.stub.StreamObserver<api.Api.IBackendGRPC> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getFindOneEmployeeByIdMethod(), responseObserver);
    }
  }

  /**
   * Base class for the server implementation of the service EmployeeServiceGprc.
   */
  public static abstract class EmployeeServiceGprcImplBase
      implements io.grpc.BindableService, AsyncService {

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return EmployeeServiceGprcGrpc.bindService(this);
    }
  }

  /**
   * A stub to allow clients to do asynchronous rpc calls to service EmployeeServiceGprc.
   */
  public static final class EmployeeServiceGprcStub
      extends io.grpc.stub.AbstractAsyncStub<EmployeeServiceGprcStub> {
    private EmployeeServiceGprcStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected EmployeeServiceGprcStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new EmployeeServiceGprcStub(channel, callOptions);
    }

    /**
     */
    public void findOneEmployeeById(EmployeeProto.Employee.ReqFindOneEmployById request,
        io.grpc.stub.StreamObserver<api.Api.IBackendGRPC> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getFindOneEmployeeByIdMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   * A stub to allow clients to do synchronous rpc calls to service EmployeeServiceGprc.
   */
  public static final class EmployeeServiceGprcBlockingStub
      extends io.grpc.stub.AbstractBlockingStub<EmployeeServiceGprcBlockingStub> {
    private EmployeeServiceGprcBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected EmployeeServiceGprcBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new EmployeeServiceGprcBlockingStub(channel, callOptions);
    }

    /**
     */
    public api.Api.IBackendGRPC findOneEmployeeById(EmployeeProto.Employee.ReqFindOneEmployById request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getFindOneEmployeeByIdMethod(), getCallOptions(), request);
    }
  }

  /**
   * A stub to allow clients to do ListenableFuture-style rpc calls to service EmployeeServiceGprc.
   */
  public static final class EmployeeServiceGprcFutureStub
      extends io.grpc.stub.AbstractFutureStub<EmployeeServiceGprcFutureStub> {
    private EmployeeServiceGprcFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected EmployeeServiceGprcFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new EmployeeServiceGprcFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<api.Api.IBackendGRPC> findOneEmployeeById(
        EmployeeProto.Employee.ReqFindOneEmployById request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getFindOneEmployeeByIdMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_FIND_ONE_EMPLOYEE_BY_ID = 0;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final AsyncService serviceImpl;
    private final int methodId;

    MethodHandlers(AsyncService serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_FIND_ONE_EMPLOYEE_BY_ID:
          serviceImpl.findOneEmployeeById((EmployeeProto.Employee.ReqFindOneEmployById) request,
              (io.grpc.stub.StreamObserver<api.Api.IBackendGRPC>) responseObserver);
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

  public static final io.grpc.ServerServiceDefinition bindService(AsyncService service) {
    return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
        .addMethod(
          getFindOneEmployeeByIdMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              EmployeeProto.Employee.ReqFindOneEmployById,
              api.Api.IBackendGRPC>(
                service, METHODID_FIND_ONE_EMPLOYEE_BY_ID)))
        .build();
  }

  private static abstract class EmployeeServiceGprcBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    EmployeeServiceGprcBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return EmployeeProto.Employee.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("EmployeeServiceGprc");
    }
  }

  private static final class EmployeeServiceGprcFileDescriptorSupplier
      extends EmployeeServiceGprcBaseDescriptorSupplier {
    EmployeeServiceGprcFileDescriptorSupplier() {}
  }

  private static final class EmployeeServiceGprcMethodDescriptorSupplier
      extends EmployeeServiceGprcBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final java.lang.String methodName;

    EmployeeServiceGprcMethodDescriptorSupplier(java.lang.String methodName) {
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
      synchronized (EmployeeServiceGprcGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new EmployeeServiceGprcFileDescriptorSupplier())
              .addMethod(getFindOneEmployeeByIdMethod())
              .build();
        }
      }
    }
    return result;
  }
}
