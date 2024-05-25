import io.grpc.ServerBuilder
import io.grpc.protobuf.services.ProtoReflectionService

class GrpcKotlinServer

fun main() {
    val server = ServerBuilder.forPort(6565)
        .addService(UserServiceKt())
        .addService(ProtoReflectionService.newInstance())
        .build()

    server.start()
    server.awaitTermination()
}