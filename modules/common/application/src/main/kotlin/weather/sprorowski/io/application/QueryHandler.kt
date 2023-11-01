package weather.sprorowski.io.application

interface QueryHandler<Q, R> where Q : Query<R> {
    suspend fun handle(query: Q): R
}
