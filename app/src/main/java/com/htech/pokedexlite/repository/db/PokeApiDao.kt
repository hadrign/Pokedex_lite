package com.htech.pokedexlite.repository.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.htech.pokedexlite.repository.model.PokeApiResponse

@Dao
abstract class PokeApiDao {

    @Query("SELECT * FROM pokedex_table")
    abstract fun getAllPokemons(): LiveData<List<PokeApiResponse>>

    @Query("SELECT * FROM pokedex_table WHERE id = :pokemonId")
    abstract fun getPokemonId(pokemonId: Int): LiveData<PokeApiResponse>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertPokemon(pokeApiResponse: PokeApiResponse)

    @Delete
    abstract fun deletePokemon(pokeApiResponse: PokeApiResponse)

}