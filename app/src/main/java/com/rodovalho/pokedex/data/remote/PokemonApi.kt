package com.rodovalho.pokedex.data.remote

import com.google.gson.GsonBuilder
import com.rodovalho.pokedex.model.Pokemon
import com.rodovalho.pokedex.model.Response
import io.reactivex.Observable
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import java.util.concurrent.TimeUnit

interface PokemonApi {
    @GET("pokemon")
    fun listPokemons(@Query("limit") limit: Int,
                     @Query("offset") offset: Int) : Observable<Response>

    @GET("pokemon/{id}")
    fun getPokemon(@Path("id") id: String) : Observable<Pokemon>

    companion object {
        private var mInstance: PokemonApi? = null

        val instance: PokemonApi
            get() {
                if (mInstance == null) {
                    val loggingInterceptor = HttpLoggingInterceptor()
                    loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

                    val httpClient = OkHttpClient.Builder()
                        .addInterceptor(loggingInterceptor)
                        .connectTimeout(20, TimeUnit.SECONDS)
                        .writeTimeout(20, TimeUnit.SECONDS)
                        .readTimeout(90, TimeUnit.SECONDS)

                    val gson = GsonBuilder().setLenient().create()

                    val retrofit = Retrofit.Builder()
                        .baseUrl("http://pokeapi.salestock.net/api/v2/")
                        .client(httpClient.build())
                        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                        .addConverterFactory(GsonConverterFactory.create(gson))
                        .build()

                    mInstance = retrofit.create(PokemonApi::class.java)
                }
                return mInstance!!
            }
    }
}