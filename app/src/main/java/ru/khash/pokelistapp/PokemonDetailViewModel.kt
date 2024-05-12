package ru.khash.pokelistapp

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class PokemonDetailViewModel : ViewModel() {
    private val _pokemonDetail = MutableLiveData<PokemonDetailResponse?>()
    val pokemonDetail: LiveData<PokemonDetailResponse?> = _pokemonDetail

    fun fetchPokemonDetails(pokemonUrl: String) {
        viewModelScope.launch {
            try {
                val pokemonId = extractIdFromUrl(pokemonUrl)
                if (pokemonId != null) {
                    val response = RetrofitClient.instance.getPokemonDetail(pokemonId)
                    _pokemonDetail.value = response
                } else {
                    Log.e("PokemonDetailViewModel", "Invalid URL format for Pokemon item: $pokemonUrl")
                }
            } catch (e: Exception) {
                Log.e("PokemonDetailViewModel", "Failed to load details for Pokemon URL: $pokemonUrl", e)
            }
        }
    }

    private fun extractIdFromUrl(url: String): Int? {
        return url.trimEnd('/').split("/").lastOrNull()?.toIntOrNull()
    }
}
