package com.htech.pokedexlite.ui.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.htech.pokedexlite.R
import com.htech.pokedexlite.ui.detail.DetailActivity
import com.htech.pokedexlite.ui.detail.DetailActivity.Companion.REQUEST_CODE
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))

        supportFragmentManager.beginTransaction().replace(R.id.content,
            MainFragment.newInstance()
        ).commitNow()

        fab.setOnClickListener {
            Intent(this, DetailActivity::class.java).apply {
                putExtra("EXTRA_POKEDEX", "SERVER_POKEDEX")
                startActivityForResult(this, REQUEST_CODE)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE)
            (supportFragmentManager.findFragmentById(R.id.content) as MainFragment).updateList()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
}