package com.example.apprf.ui

import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.example.apprf.R
import com.example.apprf.data.GameRepository
import com.example.apprf.data.remote.RetrofitHelper
import com.example.apprf.data.remote.model.GameDto
import com.example.apprf.databinding.ActivityMainBinding
import com.example.apprf.ui.fragments.GamesListFragment
import com.example.apprf.util.Constants
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding



   /*private lateinit var repository: GameRepository
    private lateinit var retrofit: Retrofit*/

    override fun onCreate(savedInstanceState: Bundle?) {

        setTheme(R.style.Theme_AppRF)

        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        //Reproduccion de musica

       // mp = MediaPlayer.create(this, R.raw.zelda)
       // mp.start()

        if(savedInstanceState == null){
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, GamesListFragment())
                .commit()
        }

      /*  retrofit = RetrofitHelper().getRetrofit()

        repository = GameRepository(retrofit)

        lifecycleScope.launch {
            val call: Call<List<GameDto>> = repository.getGames("cm/games/games_list.php")

            call.enqueue(object: Callback<List<GameDto>>{
                override fun onResponse(
                    call: Call<List<GameDto>>,
                    response: Response<List<GameDto>>
                ) {
                    Log.d(Constants.LOGTAG, "Respuesta del servidor ${response.body()}")
                }

                override fun onFailure(call: Call<List<GameDto>>, t: Throwable) {
                    //manejo del error
                    Toast.makeText(this@MainActivity, "Error ${t.message}", Toast.LENGTH_SHORT).show()
                }

            })
        }*/


    }
}