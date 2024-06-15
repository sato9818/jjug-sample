import com.linecorp.armeria.client.grpc.GrpcClients;
import com.linecorp.armeria.common.logging.LogLevel;
import com.linecorp.armeria.common.logging.LogWriter;

import io.grpc.StatusRuntimeException;
import sample.UserServiceGrpc.UserServiceBlockingStub;
import sample.UserServiceOuterClass.GetUserRequest;
import sample.UserServiceOuterClass.GetUserResponse;

public class SampleArmeriaClient {
    public static void main(String[] args) {
        final LogWriter logWriter = LogWriter.builder()
                                             .requestLogLevel(LogLevel.INFO)
                                             .successfulResponseLogLevel(LogLevel.INFO)
                                             .failureResponseLogLevel(LogLevel.ERROR)
                                             .build();

        // Retryの設定やCircuit Breakerの設定も簡単にできる。
        final UserServiceBlockingStub userService = GrpcClients.builder("http://127.0.0.1:6565/")
//                                                               .decorator(LoggingClient.builder()
//                                                                                       .logWriter(logWriter)
//                                                                                       .newDecorator())
                                                               .build(UserServiceBlockingStub.class);

        final int id = 1;

        final GetUserRequest getUserRequest = GetUserRequest.newBuilder()
                                                            .setId(id)
                                                            .build();

        try {
            final GetUserResponse getUserResponse = userService.getUser(getUserRequest);
            System.out.println(getUserResponse);
        } catch (final StatusRuntimeException e) {
            System.err.println(e.getMessage());
        }
    }
}
