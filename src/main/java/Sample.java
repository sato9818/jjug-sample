import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

public class Sample {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        Sample sample = new Sample();
        sample.getUserAndItem("userId1", "itemId1");
    }

    public void getUserAndItem(String userId, String itemId) {
        long startTime = System.nanoTime();
        CompletableFuture<User> userFuture = getUser(userId);
        CompletableFuture<Item> itemFuture = getItem(itemId);

        // ユーザーとアイテムの情報を組み合わせて出力
        userFuture.thenCombine(itemFuture, (user, item) -> {
            System.out.println("user: " + user.toString() + ", item: " + item.toString());
            return null; // thenCombineは何かを返さなければならないため、ここではnullを返す
        }).join(); // 非同期処理が完了するのを待つ
        System.out.println(TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startTime));
    }

    // 非同期にユーザー情報を取得
    public CompletableFuture<User> getUser(String id) {
        return CompletableFuture.supplyAsync(() -> {
            // サーバーAにUser情報を取りに行くシミュレーション
            try {
                Thread.sleep(1000); // ネットワーク遅延のシミュレーション
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            return new User(id); // Userオブジェクトを返す
        });
    }

    // 非同期にアイテム情報を取得
    public CompletableFuture<Item> getItem(String id) {
        return CompletableFuture.supplyAsync(() -> {
            // サーバーBにItem情報を取りに行くシミュレーション
            try {
                Thread.sleep(1000); // ネットワーク遅延のシミュレーション
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            return new Item(id); // Itemオブジェクトを返す
        });
    }

    class User {
        private String userId;

        public User(String userId) {
            this.userId = userId;
        }

        @Override
        public String toString() {
            return "User{" +
                   "userId='" + userId + '\'' +
                   '}';
        }
    }

    class Item {
        private String itemId;

        public Item(String itemId) {
            this.itemId = itemId;
        }

        @Override
        public String toString() {
            return "Item{" +
                   "itemId='" + itemId + '\'' +
                   '}';
        }
    }
}