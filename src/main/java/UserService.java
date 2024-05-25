import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.grpc.stub.StreamObserver;
import sample.UserServiceGrpc;
import sample.UserServiceOuterClass.GetUserRequest;
import sample.UserServiceOuterClass.GetUserResponse;

public class UserService extends UserServiceGrpc.UserServiceImplBase {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Override
    public void getUser(GetUserRequest request, StreamObserver<GetUserResponse> responseObserver) {
        final GetUserResponse res = GetUserResponse.newBuilder()
                                                   .setEmail("test@exmaple.com")
                                                   .setName("Koki Sato")
                                                   .setId(request.getId())
                                                   .build();
//        try {
//            Thread.sleep(1000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        logger.info("Got response: {}", res);
        responseObserver.onNext(res);
        responseObserver.onCompleted();
    }
}
