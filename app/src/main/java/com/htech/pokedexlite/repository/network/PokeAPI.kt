package com.htech.pokedexlite.repository.network

import com.htech.pokedexlite.repository.model.PokeApiResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface PokeAPI {

    @GET("pokemon/{id}/")
    fun getPokemonId(@Path("id") pokemonId: String): Call<PokeApiResponse>
}