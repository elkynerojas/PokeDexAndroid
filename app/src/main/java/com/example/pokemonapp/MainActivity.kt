package com.example.pokemonapp

import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        getPokemonData("pikachu")
    }
    private fun getPokemonData(pokemonName: String) {
        val call = RetrofitInstance.api.getPokemon(pokemonName)

        // Ejecutar la llamada en segundo plano
        call.enqueue(object : Callback<PokemonResponse> {
            override fun onResponse(call: Call<PokemonResponse>, response: Response<PokemonResponse>) {
                if (response.isSuccessful) {
                    val pokemon = response.body()

                    pokemon?.let {
                        Log.d("MainActivity", "Nombre: ${it.name}, Altura: ${it.height}, Peso: ${it.weight}")
                        Log.d("MainActivity", "Sprite: ${it.sprites.other.home.front_default}")
                        displayPokemon(pokemon)
                    }

                } else {
                    Log.e("MainActivity", "Error en la respuesta: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<PokemonResponse>, t: Throwable) {
                Log.e("MainActivity", "Error en la llamada: ${t.message}")
            }
        })
    }
    private fun displayPokemon(pokemon: PokemonResponse) {

        val pokemonImageView = findViewById<ImageView>(R.id.pokemonImageView)
        val pokemonNameTextView = findViewById<TextView>(R.id.tlPokemonNombre)

        pokemonNameTextView.text = pokemon.name
        //pokemonHeightTextView.text = "Height: ${pokemon.height}"
        //pokemonWeightTextView.text = "Weight: ${pokemon.weight}"*/

        // Cargar la imagen del sprite usando Glide
        Glide.with(this)
            .load(pokemon.sprites.other.home.front_default)
            .into(pokemonImageView)
    }
}