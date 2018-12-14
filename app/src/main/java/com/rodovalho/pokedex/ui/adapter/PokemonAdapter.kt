package com.rodovalho.pokedex.ui.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.rodovalho.pokedex.R
import com.rodovalho.pokedex.extension.load
import com.rodovalho.pokedex.model.Pokemon
import kotlinx.android.synthetic.main.item_list.view.*

class PokemonAdapter() : RecyclerView.Adapter<PokemonAdapter.ViewHolder>() {

    private var pokemons = mutableListOf<Pokemon>()

    override fun onCreateViewHolder(parent: ViewGroup, type: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_list, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount() = pokemons.size

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.bindView(pokemons[position])
    }

    fun add(pokemon: Pokemon) {
        pokemons.add(pokemon)
        notifyItemChanged(pokemons.lastIndex)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder (itemView) {
        fun bindView (pokemon: Pokemon) {
            itemView.tv_name.text = pokemon.name
            itemView.iv_sprite.load("https://pokeres.bastionbot.org/images/pokemon/${pokemon.id}.png",
                itemView.pb_loading_image,
                itemView.tv_number)
        }
    }
}