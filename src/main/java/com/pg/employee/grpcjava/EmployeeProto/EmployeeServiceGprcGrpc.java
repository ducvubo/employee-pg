package com.pg.employee.grpcjava.EmployeeProto;

import com.pg.employee.grpcjava.api.Api;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 */

@io.grpc.stub.annotations.GrpcGenerated
public final class EmployeeServiceGprcGrpc {

  private EmployeeServiceGprcGrpc() {}

  public static final String SERVICE_NAME = "EmployeeProto.EmployeeServiceGprc";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<Employee.ReqFindOneEmployById,
      Api.IBackendGRPC> getFindOneEmployeeByIdMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "findOneEmployeeById",
      requestType = Employee.ReqFindOneEmployById.class,
      responseType = Api.IBackendGRPC.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<Employee.ReqFindOneEmployById,
      Api.IBackendGRPC> getFindOneEmployeeByIdMethod() {
    io.grpc.MethodDescriptor<Employee.ReqFindOneEmployById, Api.IBackendGRPC> getFindOneEmployeeByIdMethod;
    if ((getFindOneEmployeeByIdMethod = EmployeeServiceGprcGrpc.getFindOneEmployeeByIdMethod) == null) {
      synchronized (EmployeeServiceGprcGrpc.class) {
        if ((getFindOneEmployeeByIdMethod = EmployeeServiceGprcGrpc.getFindOneEmployeeByIdMethod) == null) {
          EmployeeServiceGprcGrpc.getFindOneEmployeeByIdMethod = getFindOneEmployeeByIdMethod =
              io.grpc.MethodDescriptor.<Employee.ReqFindOneEmployById, Api.IBackendGRPC>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "findOneEmployeeById"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  Employee.ReqFindOneEmployById.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  Api.IBackendGRPC.getDefaultInstance()))
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
        @Override
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
        @Override
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
        @Override
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
    default void findOneEmployeeById(Employee.ReqFindOneEmployById request,
                                     io.grpc.stub.StreamObserver<Api.IBackendGRPC> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getFindOneEmployeeByIdMethod(), responseObserver);
    }
  }

  /**
   * Base class for the server implementation of the service EmployeeServiceGprc.
   */
  public static abstract class EmployeeServiceGprcImplBase
      implements io.grpc.BindableService, AsyncService {

    @Override public final io.grpc.ServerServiceDefinition bindService() {
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

    @Override
    protected EmployeeServiceGprcStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new EmployeeServiceGprcStub(channel, callOptions);
    }

    /**
     */
    public void findOneEmployeeById(Employee.ReqFindOneEmployById request,
                                    io.grpc.stub.StreamObserver<Api.IBackendGRPC> responseObserver) {
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

    @Override
    protected EmployeeServiceGprcBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new EmployeeServiceGprcBlockingStub(channel, callOptions);
    }

    /**
     */
    public Api.IBackendGRPC findOneEmployeeById(Employee.ReqFindOneEmployById request) {
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

    @Override
    protected EmployeeServiceGprcFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new EmployeeServiceGprcFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<Api.IBackendGRPC> findOneEmployeeById(
        Employee.ReqFindOneEmployById request) {
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

    @Override
    @SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_FIND_ONE_EMPLOYEE_BY_ID:
          serviceImpl.findOneEmployeeById((Employee.ReqFindOneEmployById) request,
              (io.grpc.stub.StreamObserver<Api.IBackendGRPC>) responseObserver);
          break;
        default:
          throw new AssertionError();
      }
    }

    @Override
    @SuppressWarnings("unchecked")
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
              Employee.ReqFindOneEmployById,
              Api.IBackendGRPC>(
                service, METHODID_FIND_ONE_EMPLOYEE_BY_ID)))
        .build();
  }

  private static abstract class EmployeeServiceGprcBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    EmployeeServiceGprcBaseDescriptorSupplier() {}

    @Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return Employee.getDescriptor();
    }

    @Override
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
    private final String methodName;

    EmployeeServiceGprcMethodDescriptorSupplier(String methodName) {
      this.methodName = methodName;
    }

    @Override
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
