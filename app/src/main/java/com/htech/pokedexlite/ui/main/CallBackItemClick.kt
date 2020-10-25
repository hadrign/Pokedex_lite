package com.htech.pokedexlite.ui.main

import com.htech.pokedexlite.repository.model.PokeApiResponse

interface CallBackItemClick {
    fun onItemClick(pokeApiResponse: PokeApiResponse)
}