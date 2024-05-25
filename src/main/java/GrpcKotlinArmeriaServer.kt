import com.linecorp.armeria.server.Server
import com.linecorp.armeria.server.docs.DocService
import com.linecorp.armeria.server.grpc.GrpcService
import io.grpc.protobuf.services.ProtoReflectionService

class GrpcKotlinArmeriaServer

fun main() {
    val grpcService = GrpcService.builder()
        .addService(UserServiceKt())
        .addService(ProtoReflectionService.newInstance())
        .enableUnframedRequests(true)
        .build()

    val server = Server.builder()
        .http(6565)
        .service(grpcService)
        .serviceUnder("/internal/docs", DocService())
        .build()

    server.start().join()
}