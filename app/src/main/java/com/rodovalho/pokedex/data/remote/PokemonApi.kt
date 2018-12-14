package com.rodovalho.pokedex.data.remote

import com.rodovalho.pokedex.model.Pokemon
import com.rodovalho.pokedex.model.Response
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import rx.Observable

interface PokemonApi {
    @GET("pokemon")
    fun listPokemons(@Query("limit") limit: Int,
                     @Query("offset") offset: Int) : Observable<Response>

    @GET("pokemon")
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
                        .build()

                    val retrofit = Retrofit.Builder()
                        .baseUrl("http://pokeapi.salestock.net/api/v2/")
                        .client(httpClient)
                        .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                        .addConverterFactory(GsonConverterFactory.create())
                        .build()

                    mInstance = retrofit.create(PokemonApi::class.java)
                }
                return mInstance!!
            }
    }
}