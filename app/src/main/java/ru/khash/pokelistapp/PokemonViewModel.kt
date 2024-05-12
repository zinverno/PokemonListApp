package ru.khash.pokelistapp

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class PokemonViewModel : ViewModel(){
    private val _pokemonList = MutableLiveData<List<PokemonItem>?>()
    val pokemonList: LiveData<List<PokemonItem>?> = _pokemonList

    init {
        viewModelScope.launch {
            _pokemonList.value = loadPokemonList()
            loadPokemonList()?.let { fetchPokemonDetails(it) }
        }
    }
    fun fetchPokemonDetails(pokemonItems: List<PokemonItem>) {
        pokemonItems.forEach { pokemonItem ->
            viewModelScope.launch {
                try {
                    val pokemonId = pokemonItem.url.extractIdFromUrl()
                    if (pokemonId != null) {
                        val detailResponse = RetrofitClient.instance.getPokemonDetail(pokemonId)
                        updatePokemonItemWithImage(pokemonItem, detailResponse.sprites.front_default)
                    } else {
                        Log.e("PokemonViewModel", "Invalid URL format for Pokemon item: ${pokemonItem.url}")
                    }
                } catch (e: Exception) {
                    Log.e("PokemonViewModel", "Failed to load details for Pokemon ID: ${pokemonItem.url}", e)
                }
            }
        }
    }

    private fun updatePokemonItemWithImage(pokemonItem: PokemonItem, imageUrl: String) {
        _pokemonList.value = _pokemonList.value?.map {
            if (it.url == pokemonItem.url) {
                it.copy(imageUrl = imageUrl)
            } else {
                it
            }
        }
    }

    private suspend fun loadPokemonList(): List<PokemonItem>? {
        return try {
            val response = RetrofitClient.instance.getPokemonList(20, 0)
            response.results
        } catch (e: Exception) {
            Log.e("PokemonViewModel", "Error loading Pokemon list", e)
            null
        }
    }


    private fun String.extractIdFromUrl(): Int? {
        val parts = this.trimEnd('/').split("/")
        val id = parts.lastOrNull { it.isNotEmpty() }?.toIntOrNull()
        Log.d("PokemonViewModel", "Extracted Pokemon ID: $id from URL: $this")
        return id;
    }


}