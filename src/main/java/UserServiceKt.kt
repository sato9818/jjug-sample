import kotlinx.coroutines.*
import org.slf4j.LoggerFactory
import sample.UserServiceGrpcKt
import sample.UserServiceOuterClass
import sample.getUserResponse
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.coroutineContext

val logger = LoggerFactory.getLogger(UserServiceKt::class.java)

class UserServiceKt : UserServiceGrpcKt.UserServiceCoroutineImplBase() {
    @OptIn(ExperimentalStdlibApi::class)
    override suspend fun getUser(request: UserServiceOuterClass.GetUserRequest): UserServiceOuterClass.GetUserResponse {
        val context: CoroutineContext = coroutineContext
        val job = context[Job]
        val dispatcher = context[CoroutineDispatcher]
        logger.info("context: $context, job: $job, dispatcher: $dispatcher")
//        delay(1000L)
//        Thread.sleep(1000)

        withContext(Dispatchers.IO) {
            val context: CoroutineContext = coroutineContext
            val job = context[Job]
            val dispatcher = context[CoroutineDispatcher]
            logger.info("context: $context, job: $job, dispatcher: $dispatcher")
            logger.info("Coroutine started on thread ${Thread.currentThread().name}")
            delay(1000)  // サスペンドポイント
            logger.info("Coroutine resumed on thread ${Thread.currentThread().name}")
        }
        return getUserResponse {
            id = request.id
            name = "Koki Sato"
            email = "test@example.com"
        }
    }
}