package com.sample.jjug;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.linecorp.armeria.common.logging.LogLevel;
import com.linecorp.armeria.common.logging.LogWriter;
import com.linecorp.armeria.server.grpc.GrpcService;
import com.linecorp.armeria.server.logging.LoggingService;
import com.linecorp.armeria.spring.ArmeriaServerConfigurator;

import io.grpc.protobuf.services.ProtoReflectionService;

@Configuration
public class ArmeriaSpringConfig {
    @Bean
    public ArmeriaServerConfigurator armeriaServerConfigurator(AsyncUserService asyncUserService) {
        final GrpcService grpcService = GrpcService.builder()
                                                   .addService(asyncUserService)
                                                   .addService(ProtoReflectionService.newInstance())
                                                   .enableUnframedRequests(true)
                                                   .build();
        final LogWriter logWriter = LogWriter.builder()
                                             .requestLogLevel(LogLevel.INFO)
                                             .successfulResponseLogLevel(LogLevel.INFO)
                                             .failureResponseLogLevel(LogLevel.ERROR)
                                             .build();
        return serverBuilder -> serverBuilder.service(grpcService)
                                             .decorator(LoggingService.builder()
                                                                      .logWriter(logWriter)
                                                                      .newDecorator());
    }
}
