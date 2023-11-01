package weather.sprorowski.io.application

interface CommandHandler<in C, out R> where C : Command<R> {
    suspend fun handle(command: C): R
}
