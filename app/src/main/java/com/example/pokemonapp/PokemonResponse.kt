package com.example.pokemonapp

data class PokemonResponse(
    val name: String,
    val height: Int,
    val weight: Int,
    val sprites: Sprites
)

data class Sprites(
    val other: Other
)
data class Other(
    val home: Home
)
data class Home(
    val front_default: String
)