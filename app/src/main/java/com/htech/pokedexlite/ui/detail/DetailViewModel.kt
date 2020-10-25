package com.htech.pokedexlite.ui.detail

import android.app.Activity
import android.app.Application
import android.content.Context
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.htech.pokedexlite.R
import com.htech.pokedexlite.repository.db.PokeApiRoomDatabase
import com.htech.pokedexlite.repository.model.PokeApiResponse
import com.htech.pokedexlite.repository.network.PokeApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel(private val context: Application) : ViewModel() {

    fun getPokemon(idPokemon: String, cb: PokeApiService.CallbackResponse<PokeApiResponse>) {
        PokeApiService().pokeApi.getPokemonId(idPokemon).enqueue(object : Callback<PokeApiResponse> {
            override fun onResponse(call: Call<PokeApiResponse>, response: Response<PokeApiResponse>) {
                if (response.isSuccessful && response.body() != null) {
                    cb.onResponse(response.body()!!)
                } else {
                    cb.onFailure(Throwable(response.message()), response)
                }
            }

            override fun onFailure(call: Call<PokeApiResponse>, t: Throwable) {
                cb.onFailure(t)
            }

        })
    }

    fun insertPokemon(pokeApiResponse: PokeApiResponse) {
        PokeApiRoomDatabase.getInstance(context).pokeApiDao().insertPokemon(pokeApiResponse)
    }

    fun getLocalPokemonById(pokemonId: Int) : LiveData<PokeApiResponse> {
        return PokeApiRoomDatabase.getInstance(context).pokeApiDao().getPokemonId(pokemonId)
    }


    fun deletePokemon(pokeApiResponse: PokeApiResponse) {
        PokeApiRoomDatabase.getInstance(context).pokeApiDao().deletePokemon(pokeApiResponse)
    }

    fun showPokemon(context: Activity,
                    txtName: TextView,
                    txtWeight: TextView,
                    txtExperience: TextView,
                    txtHeight: TextView,
                    imageDetail: ImageView, pokeApiResponse: PokeApiResponse) {
        txtName.text = "Nombre: " + pokeApiResponse.name.toString().toUpperCase()
        txtExperience.text = "Experiencia: " + pokeApiResponse.baseExperience
        txtHeight.text = "Altura: " + pokeApiResponse.height + " Decimetros"
        txtWeight.text = "Peso: " + pokeApiResponse.weight + " Hectogramos"

        val urlImage = "https://pokeres.bastionbot.org/images/pokemon/"+ pokeApiResponse.id +".png"
        Glide.with(context)
            .load(urlImage)
            .apply (
                RequestOptions()
                    .placeholder(R.drawable.ic_launcher_background)
                //.circleCrop()
            )
            .into(imageDetail)
    }
}