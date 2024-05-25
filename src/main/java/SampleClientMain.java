import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import sample.UserServiceGrpc;
import sample.UserServiceOuterClass.GetUserRequest;
import sample.UserServiceOuterClass.GetUserResponse;

public class SampleClientMain {
    private static final Logger logger = LoggerFactory.getLogger(SampleClientMain.class);

    public static void main(String[] args) throws Exception {
        ExecutorService executor = Executors.newFixedThreadPool(50); // 50個のスレッドを持つプール

        long startTime = System.nanoTime();
        try {
            for (int i = 0; i < 1; i++) { // 50回のリクエストを送信する
                int userId = i;
                executor.submit(() -> {
                    // 各リクエストごとに新しいチャネルを作成
                    ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 6565)
                                                                  .usePlaintext()
                                                                  .build();

                    // スタブの作成
                    UserServiceGrpc.UserServiceBlockingStub stub = UserServiceGrpc.newBlockingStub(channel);
                    GetUserRequest request = GetUserRequest.newBuilder()
                                                           .setId(userId)
                                                           .build();
                    // リクエストの送信とレスポンスの受信
                    GetUserResponse response = stub.getUser(request);
                    logger.info("Received response for user ID " + userId + ": " + response.toString());

                    // チャネルのシャットダウン
                    channel.shutdown();
                    try {
                        channel.awaitTermination(5, TimeUnit.SECONDS);
                    } catch (InterruptedException e) {
                        logger.error("Channel shutdown interrupted", e);
                    }
                });
            }
        } finally {
            // シャットダウン処理
            executor.shutdown();
            try {
                if (!executor.awaitTermination(1, TimeUnit.MINUTES)) {
                    executor.shutdownNow();
                }
            } catch (InterruptedException e) {
                executor.shutdownNow();
            }
        }
        long endTime = System.nanoTime();
        long duration = TimeUnit.NANOSECONDS.toMillis(endTime - startTime);

        logger.info("Total request time: " + duration + " milliseconds.");
    }
}
