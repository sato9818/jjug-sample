import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.protobuf.services.ProtoReflectionService;

public final class GrpcJavaServer {
    public static void main(String[] args) throws Exception {
        Server server = ServerBuilder.forPort(6565)
                                     .addService(new UserService())
                                     .addService(ProtoReflectionService.newInstance())
                                     .build();

        server.start();
        server.awaitTermination();
    }
}
