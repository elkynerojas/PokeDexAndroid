package com.example.pokemonapp

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.Call

interface PokeApiService {
    @GET("pokemon/{name}")
    fun getPokemon(@Path("name") pokemonName: String): Call<PokemonResponse>
}