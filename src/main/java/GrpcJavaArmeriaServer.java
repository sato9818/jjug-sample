import com.linecorp.armeria.server.Server;
import com.linecorp.armeria.server.docs.DocService;
import com.linecorp.armeria.server.grpc.GrpcService;

import io.grpc.protobuf.services.ProtoReflectionService;

public class GrpcJavaArmeriaServer {
    public static void main(String[] args) throws Exception {
        final GrpcService grpcService = GrpcService.builder()
                                                   .addService(new UserService())
                                                   .addService(ProtoReflectionService.newInstance())
                                                   .enableUnframedRequests(true)
                                                   .build();

        final Server server = Server.builder()
                                    .http(6565)
                                    .service(grpcService)
                                    .serviceUnder("/internal/docs", new DocService())
                                    .build();

        server.start().join();
    }
}
