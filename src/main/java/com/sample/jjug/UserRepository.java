package com.sample.jjug;

import java.util.concurrent.CompletableFuture;

import org.springframework.stereotype.Repository;

@Repository
public class UserRepository {
    public CompletableFuture<User> findById(int id) {
        return CompletableFuture.supplyAsync(() -> new User(id, "Koki Sato", "test@exmaple.com"));
    }
}
