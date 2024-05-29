import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.linecorp.armeria.common.logging.LogLevel;
import com.linecorp.armeria.common.logging.LogWriter;
import com.linecorp.armeria.server.Server;
import com.linecorp.armeria.server.docs.DocService;
import com.linecorp.armeria.server.grpc.GrpcService;

import io.grpc.protobuf.services.ProtoReflectionService;

public final class SampleArmeriaServer {
    private static final Logger logger = LoggerFactory.getLogger(SampleArmeriaServer.class);

    public static void main(String[] args) throws Exception {
        final GrpcService grpcService = GrpcService.builder()
                                                   .addService(new UserService())
                                                   // DocServiceで使う。
                                                   .addService(ProtoReflectionService.newInstance())
                                                   .enableUnframedRequests(true)
                                                   .build();
        final LogWriter logWriter = LogWriter.builder()
                                             .requestLogLevel(LogLevel.INFO)
                                             .successfulResponseLogLevel(LogLevel.INFO)
                                             .failureResponseLogLevel(LogLevel.ERROR)
                                             .build();
        final Server server = Server.builder()
                                    .http(6565)
                                    .service(grpcService)
                                    .serviceUnder("/internal/docs", new DocService())
//                                    .decorator(LoggingService.builder()
//                                                             .logWriter(logWriter)
//                                                             .newDecorator()
//                                    )
                                    .build();
        server.start().join();
        logger.info("Server has been started. Serving DocService at http://127.0.0.1:{}/internal/docs",
                    server.activeLocalPort());
    }
}
