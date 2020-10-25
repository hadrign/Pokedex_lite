package com.htech.pokedexlite.ui.detail

import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.htech.pokedexlite.R
import com.htech.pokedexlite.repository.model.PokeApiResponse
import com.htech.pokedexlite.repository.network.PokeApiService
import com.htech.pokedexlite.utils.CustomViewModelFactory
import kotlinx.android.synthetic.main.activity_detail.*
import retrofit2.Response


class DetailActivity : AppCompatActivity() {

    companion object {
        const val TAG = "DetailActivity"
        const val LOCAL_POKEMON = "LOCAL_POKEMON"
        const val OBJECT_POKEMON = "OBJECT_POKEMON"
        const val REQUEST_CODE = 100
    }

    private var mPokeApiResponse: PokeApiResponse? = null
    private var localPokemon = true

    private val mViewModel: DetailViewModel by lazy {
        val factory = CustomViewModelFactory(application)
        ViewModelProvider(this, factory).get(DetailViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init()
        listeners()
    }

    private fun init() {
        setContentView(R.layout.activity_detail)
        setSupportActionBar(findViewById(R.id.toolbar))
        window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        intent?.let {
            if (it.getStringExtra("EXTRA_POKEMON") == LOCAL_POKEMON) {

                localPokemon = true
                btnPokedexDetail.setImageResource(android.R.drawable.ic_menu_delete)

                mPokeApiResponse = intent.extras!!.getSerializable(OBJECT_POKEMON) as PokeApiResponse?

                mViewModel.showPokemon(this@DetailActivity,
                        txtName,
                        txtWeight,
                        txtExperience,
                        txtHeight,
                        imageDetail, mPokeApiResponse!!)

            } else {
                localPokemon = false
                btnPokedexDetail.setImageResource(android.R.drawable.ic_menu_save)
                getServerPokemon()
            }
        } ?: getServerPokemon()
    }

    private fun listeners() {
        btnPokedexDetail.setOnClickListener {

            if (localPokemon) {
                mViewModel.
                deletePokemon(mPokeApiResponse!!)

            } else {

                mPokeApiResponse?.id!!.let { it1 ->
                    mViewModel.getLocalPokemonById(it1)
                            .observe(this, Observer { pokemon ->
                                if (pokemon != null) {
                                    //showToast()
                                } else {
                                    mViewModel.insertPokemon(mPokeApiResponse!!)
                                }
                            })
                }
            }
            finish()
        }
    }

    private fun getServerPokemon() {
        val randomPokemonId = (1..152).random()
        mViewModel.getPokemon(randomPokemonId.toString(), object : PokeApiService.CallbackResponse<PokeApiResponse> {
            override fun onResponse(response: PokeApiResponse) {
                mPokeApiResponse = response
                mViewModel.showPokemon(this@DetailActivity,
                        txtName,
                        txtWeight,
                        txtExperience,
                        txtHeight,
                        imageDetail, mPokeApiResponse!!)
            }

            override fun onFailure(t: Throwable, res: Response<*>?) {
                txtName.text = res.toString()
            }

        })
    }

    private fun showToast() {
        val toast2 = Toast.makeText(applicationContext,
                "Este Pokemon ya está en tu Pokedex Lite!", Toast.LENGTH_SHORT)
        toast2.setGravity(Gravity.BOTTOM, 0, 0)
        toast2.show()
    }
}