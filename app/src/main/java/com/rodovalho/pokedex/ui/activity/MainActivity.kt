package com.rodovalho.pokedex.ui.activity

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.os.Parcelable
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.Toast
import com.rodovalho.pokedex.R
import com.rodovalho.pokedex.ui.adapter.PokemonAdapter
import com.rodovalho.pokedex.ui.util.ItemOffsetDecoration
import com.rodovalho.pokedex.ui.util.PokemonViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val viewModel: PokemonViewModel by lazy {
        ViewModelProviders.of(this).get(PokemonViewModel::class.java)
    }

    private val adapter: PokemonAdapter by lazy {
        PokemonAdapter()
    }

    private var recyclerViewState: Parcelable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initRecyclerView()
        loadPokemon(0)
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        outState?.putParcelable("layoutManager", rv_pokemon_list.layoutManager?.onSaveInstanceState())
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)
        recyclerViewState = savedInstanceState?.getBundle("layoutManager")
    }

    private fun initRecyclerView() {
        rv_pokemon_list.setHasFixedSize(true)
        rv_pokemon_list.layoutManager = GridLayoutManager(this, 2)
        var itemOffsetDecoration = ItemOffsetDecoration(this, R.dimen.spacing)
        rv_pokemon_list.addItemDecoration(itemOffsetDecoration)
        rv_pokemon_list.adapter = adapter
    }

    private fun loadPokemon(page: Int) {
        val disposable = viewModel.load(page)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    adapter.add(it)
                    adapter.notifyDataSetChanged()
                },
                {
                    Toast.makeText(this, "Erro ao carregar dados. Mensagem: ${it.message}", Toast.LENGTH_SHORT)
                },
                {
                    if (recyclerViewState != null) {
                        rv_pokemon_list.layoutManager?.onRestoreInstanceState(recyclerViewState)
                        recyclerViewState = null
                    }
                    pb_loading_list.visibility = View.GONE
                    adapter.notifyDataSetChanged()
                }
            )
    }
}
