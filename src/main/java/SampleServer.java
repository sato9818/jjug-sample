import io.grpc.Server;
import io.grpc.ServerBuilder;

public final class SampleServer {
    public static void main(String[] args) throws Exception {
        final Server server = ServerBuilder.forPort(6565)
                                           .addService(new UserService())
                                           .build();

        server.start();
        System.out.println("Server started, listening on " + server.getPort());
        server.awaitTermination();
    }
}
