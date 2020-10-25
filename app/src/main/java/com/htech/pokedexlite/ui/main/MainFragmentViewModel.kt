package com.htech.pokedexlite.ui.main

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.htech.pokedexlite.repository.db.PokeApiRoomDatabase
import com.htech.pokedexlite.repository.model.PokeApiResponse

class MainFragmentViewModel(private val context: Application) : ViewModel() {

    fun getLocalAllPokemons() : LiveData<List<PokeApiResponse>>{
        return PokeApiRoomDatabase.getInstance(context).pokeApiDao().getAllPokemons()
    }
}