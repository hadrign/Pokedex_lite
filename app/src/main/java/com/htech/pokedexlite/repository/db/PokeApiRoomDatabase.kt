package com.htech.pokedexlite.repository.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.htech.pokedexlite.repository.model.PokeApiResponse


@Database(entities = [PokeApiResponse::class], version = 1, exportSchema = false)
abstract class PokeApiRoomDatabase : RoomDatabase() {
    abstract fun pokeApiDao(): PokeApiDao

    companion object {
        private var instance: PokeApiRoomDatabase? = null

        fun getInstance(context: Context): PokeApiRoomDatabase {
            if (instance == null) {
                synchronized(PokeApiRoomDatabase::class) {
                    instance = Room.databaseBuilder(context.applicationContext, PokeApiRoomDatabase::class.java, "pokedex_db")
                        .allowMainThreadQueries()
                        .fallbackToDestructiveMigration()
                        .build()
                }
            }
            return instance!!
        }
    }
}