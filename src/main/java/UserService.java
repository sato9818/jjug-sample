import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import io.grpc.stub.StreamObserver;
import sample.UserServiceGrpc;
import sample.UserServiceOuterClass.GetUserRequest;
import sample.UserServiceOuterClass.GetUserResponse;

public class UserService extends UserServiceGrpc.UserServiceImplBase {
    @Override
    public void getUser(GetUserRequest request, StreamObserver<GetUserResponse> responseObserver) {
        if (request.getId() == 1000) {
            responseObserver.onError(new StatusRuntimeException(Status.UNIMPLEMENTED));
            return;
        }
        final GetUserResponse res = GetUserResponse.newBuilder()
                                                   .setEmail("test@exmaple.com")
                                                   .setName("Koki Sato")
                                                   .setId(request.getId())
                                                   .build();
        responseObserver.onNext(res);
        responseObserver.onCompleted();
    }
}
