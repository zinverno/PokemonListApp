package ru.khash.pokelistapp

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() , OnPokemonClickListener {

    private val pokemonViewModel: PokemonViewModel by viewModels()

    private lateinit var pokemonAdapter: PokemonAdapter
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main) // Замените на реальное имя вашего макета

        setupRecyclerView()

        pokemonViewModel.pokemonList.observe(this) { pokemonList ->
            pokemonAdapter.updateItems(pokemonList ?: emptyList())
        }
    }

    private fun setupRecyclerView() {
        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)


        pokemonAdapter = PokemonAdapter(emptyList(), this)
        recyclerView.adapter = pokemonAdapter
    }
    override fun onPokemonClick(pokemonItem: PokemonItem) {
        val intent = Intent(this, PokemonDetailActivity::class.java).apply {
            putExtra("POKEMON_URL", pokemonItem.url)
        }
        startActivity(intent)
    }
}

