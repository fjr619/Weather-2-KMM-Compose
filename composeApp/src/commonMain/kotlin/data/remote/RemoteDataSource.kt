package data.remote

interface RemoteDataSource {
    suspend fun fetchForecast():
}