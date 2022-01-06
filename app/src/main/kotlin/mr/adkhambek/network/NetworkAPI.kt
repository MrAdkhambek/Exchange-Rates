package mr.adkhambek.network

import mr.adkhambek.Exchange
import retrofit2.Response
import retrofit2.http.GET

interface NetworkAPI {

    @GET("en/exchange-rates/json/")
    suspend fun loadFromNBU(): Response<List<Exchange>>
}