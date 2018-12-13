package com.rodovalho.pokedex.data

import android.net.Uri
import com.rodovalho.pokedex.data.remote.PokemonApi
import com.rodovalho.pokedex.model.Pokemon
import rx.Observable

class PokemonDataSource {

    fun loadPokemons(limit: Int
                     , offset: Int) : Observable<Pokemon>{
        return PokemonApi.instance.listPokemons(limit, offset)
            .flatMap {
                Observable.from(it.results)
                    .flatMap {
                        getPokemon(Uri.parse(it.url).lastPathSegment)
                    }
            }
    }

    private fun getPokemon(id: String): Observable<Pokemon> {
        return PokemonApi.instance.getPokemon(id)
    }
}