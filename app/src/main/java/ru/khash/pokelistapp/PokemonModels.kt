package ru.khash.pokelistapp

data class PokemonListResponse(
    val results: List<PokemonItem>
)

data class PokemonItem(
    val name: String,
    val url: String,
    val imageUrl: String
)

data class PokemonDetailResponse(
    val name: String,
    val base_experience: Int,
    val height: Int,
    val weight: Int,
    val order: Int,
    val sprites: PokemonSprites
)

data class PokemonSprites(
    val front_default: String
)