package com.htech.pokedexlite.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.htech.pokedexlite.R
import com.htech.pokedexlite.repository.model.PokeApiResponse
import com.htech.pokedexlite.ui.detail.DetailActivity
import com.htech.pokedexlite.ui.detail.DetailActivity.Companion.LOCAL_POKEMON
import com.htech.pokedexlite.ui.detail.DetailActivity.Companion.OBJECT_POKEMON
import com.htech.pokedexlite.ui.detail.DetailActivity.Companion.REQUEST_CODE
import com.htech.pokedexlite.utils.CustomViewModelFactory
import kotlinx.android.synthetic.main.fragment_main.*

class MainFragment : Fragment(), CallBackItemClick {

    companion object {
        const val TAG = "MainFragment"
        fun newInstance() = MainFragment()
    }

    private var mAdapter: MainAdapte? = null
    private var pokedexList: List<PokeApiResponse>? = null

    private val mViewModel: MainFragmentViewModel by lazy {
        val factory = CustomViewModelFactory(activity!!.application)
        ViewModelProvider(this, factory).get(MainFragmentViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        getLocalAllPokemons()
    }

    fun init() {
        recyclerViewMainList.layoutManager = LinearLayoutManager(activity)
        recyclerViewMainList.isNestedScrollingEnabled = false
        recyclerViewMainList.setHasFixedSize(false)
    }

    fun getLocalAllPokemons() {
        mViewModel.getLocalAllPokemons().observe(viewLifecycleOwner, Observer { pokedexList ->

            mAdapter = MainAdapte(activity!!.applicationContext, this, pokedexList)
            recyclerViewMainList.adapter = mAdapter
        })
    }

    override fun onItemClick(pokeApiResponse: PokeApiResponse) {
        activity?.let { fragment ->
            Intent(fragment, DetailActivity::class.java).apply {

                arguments = Bundle().apply {
                    putSerializable(OBJECT_POKEMON, pokeApiResponse)
                }
                arguments?.let { args -> putExtras(args) }

                putExtra("EXTRA_POKEMON", LOCAL_POKEMON)
                putExtra("EXTRA_POKEMON_ID", pokeApiResponse.id)
                fragment.startActivityForResult(this, REQUEST_CODE)
            }
        }
    }

    fun updateList() {
        mAdapter!!.notifyDataSetChanged()
    }

}