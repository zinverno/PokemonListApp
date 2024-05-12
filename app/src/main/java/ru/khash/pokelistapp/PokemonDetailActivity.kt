package ru.khash.pokelistapp

import android.os.Bundle
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.bumptech.glide.Glide

class PokemonDetailActivity : AppCompatActivity() {
    private val viewModel by viewModels<PokemonDetailViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pokemon_detail)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val pokemonUrl = intent.getStringExtra("POKEMON_URL") ?: ""
        if (pokemonUrl.isNotEmpty()) {
            viewModel.fetchPokemonDetails(pokemonUrl)
        } else {
            finish()
        }

        viewModel.pokemonDetail.observe(this) { pokemonDetail ->
            pokemonDetail?.let { detail ->
                findViewById<ImageView>(R.id.pokemon_image_view).loadImage(detail.sprites.front_default)
                findViewById<TextView>(R.id.pokemon_name_text_view).text = detail.name
                findViewById<TextView>(R.id.pokemon_experience_text_view)
                    .text = getString(R.string.base_experience, detail.base_experience)
                findViewById<TextView>(R.id.pokemon_height_text_view)
                    .text = getString(R.string.height, detail.height)
                findViewById<TextView>(R.id.pokemon_weight_text_view)
                    .text = getString(R.string.weight, detail.weight)
                findViewById<TextView>(R.id.pokemon_order_text_view)
                    .text = getString(R.string.order, detail.order)
            } ?: run {
            }
        }
    }

    private fun ImageView.loadImage(url: String) {
        Glide.with(this.context).load(url).into(this)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}
