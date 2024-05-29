import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;
import sample.UserServiceGrpc;
import sample.UserServiceGrpc.UserServiceBlockingStub;
import sample.UserServiceOuterClass.GetUserRequest;
import sample.UserServiceOuterClass.GetUserResponse;

public final class SampleClient {
    public static void main(String[] args) {
        final ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 6565)
                                                            .usePlaintext()
                                                            .build();
        final UserServiceBlockingStub stub = UserServiceGrpc.newBlockingStub(channel);

        final int id = 1;

        final GetUserRequest getUserRequest = GetUserRequest.newBuilder()
                                                            .setId(id)
                                                            .build();

        try {
            final GetUserResponse getUserResponse = stub.getUser(getUserRequest);
            System.out.println(getUserResponse);
        } catch (final StatusRuntimeException e) {
            System.err.println(e.getMessage());
        }
    }
}
