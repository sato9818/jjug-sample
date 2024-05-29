package com.sample.jjug;

import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import io.grpc.stub.StreamObserver;
import sample.UserServiceGrpc;
import sample.UserServiceOuterClass.GetUserRequest;
import sample.UserServiceOuterClass.GetUserResponse;

@Component
public class AsyncUserService extends UserServiceGrpc.UserServiceImplBase {
    private final UserRepository userRepository;

    @Autowired
    public AsyncUserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void getUser(GetUserRequest request, StreamObserver<GetUserResponse> responseObserver) {
        if (request.getId() == 1000) {
            responseObserver.onError(new StatusRuntimeException(Status.UNIMPLEMENTED));
            return;
        }
        final CompletableFuture<User> future = userRepository.findById(request.getId());

        future.thenAccept(user -> {
            responseObserver.onNext(GetUserResponse.newBuilder()
                                                   .setEmail(user.email())
                                                   .setName(user.name())
                                                   .setId(user.id())
                                                   .build());
            responseObserver.onCompleted();
        }).exceptionally(ex -> {
            responseObserver.onError(new StatusRuntimeException(Status.INTERNAL));
            return null;
        });
    }
}
