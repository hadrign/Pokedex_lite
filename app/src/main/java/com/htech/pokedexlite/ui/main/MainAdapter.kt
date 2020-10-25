package com.htech.pokedexlite.ui.main

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.htech.pokedexlite.R
import com.htech.pokedexlite.repository.model.PokeApiResponse
import kotlinx.android.synthetic.main.item_list.view.*

class MainAdapte(private val context: Context, private val callbackItemClick: CallBackItemClick, private val pokedexList: List<PokeApiResponse>?) : RecyclerView.Adapter<MainAdapte.MainHolder>() {

    class MainHolder(v: View) : RecyclerView.ViewHolder(v) {
        internal var view = v
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_list, parent, false)
        return MainHolder(view)
    }

    override fun onBindViewHolder(holder: MainHolder, position: Int) {

        pokedexList?.get(position).let { pokemon ->
            val urlImage = "https://pokeres.bastionbot.org/images/pokemon/"+ pokemon?.id +".png"
            Glide.with(context)
                .load(urlImage)
                .apply(
                    RequestOptions()
                        .placeholder(R.drawable.ic_launcher_background)
                )
                .into(holder.view.imageCardView)
            holder.view.textnamePokemon.text = pokemon?.name.toString().toUpperCase()


            holder.view.cardView.setOnClickListener {
                callbackItemClick.onItemClick(pokemon!!)
            }
        }
    }

    override fun getItemCount(): Int {
        if (pokedexList != null) {
            return pokedexList.size
        } else {
            return 0
        }
    }
}