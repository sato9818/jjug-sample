import io.grpc.stub.StreamObserver;
import sample.UserServiceGrpc;
import sample.UserServiceOuterClass.GetUserRequest;
import sample.UserServiceOuterClass.GetUserResponse;
import sample.UserServiceOuterClass.User;

public class UserService extends UserServiceGrpc.UserServiceImplBase {
    @Override
    public void getUser(GetUserRequest request, StreamObserver<GetUserResponse> responseObserver) {
        // Sample grpcurl command: grpcurl -plaintext -d '{"id": 1000}' localhost:6565 sample.UserService.GetUser
        final GetUserResponse res = GetUserResponse.newBuilder().setUser(
                User.newBuilder().setEmail("test@exmaple.com").setName("Koki Sato").setId(request.getId()).build()
        ).build();
        responseObserver.onNext(res);
        responseObserver.onCompleted();
    }
}
