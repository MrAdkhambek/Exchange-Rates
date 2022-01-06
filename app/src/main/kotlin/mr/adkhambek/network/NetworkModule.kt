package mr.adkhambek.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

class NetworkModule {

    private val retrofit: Retrofit = Retrofit
        .Builder()
        .baseUrl("https://nbu.uz/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val networkAPI: NetworkAPI
        get() = retrofit.create()
}