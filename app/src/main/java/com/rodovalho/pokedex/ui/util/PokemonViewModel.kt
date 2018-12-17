package com.rodovalho.pokedex.ui.util

import android.arch.lifecycle.ViewModel
import com.rodovalho.pokedex.data.PokemonDataSource
import com.rodovalho.pokedex.model.Pokemon
import io.reactivex.Observable

class PokemonViewModel : ViewModel() {
    private var pokemons = mutableListOf<Pokemon>()

    var isLoading = false
        private set

    var currentPage = -1
        private set

    val limit = 20

    fun load(page: Int) : Observable<Pokemon> {
        return if (page <= currentPage) {
            Observable.fromIterable(pokemons)
        } else {
            currentPage = page
            PokemonDataSource().loadPokemons(limit, page)
        }
    }
}