import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.protobuf.services.ProtoReflectionService;

public final class SampleAppMain {
    public static void main(String[] args) throws Exception {
        final Server server = ServerBuilder.forPort(6565)
                .addService(new UserService())
                .addService(ProtoReflectionService.newInstance())
                .build();

        server.start();
        server.awaitTermination();
    }
}
