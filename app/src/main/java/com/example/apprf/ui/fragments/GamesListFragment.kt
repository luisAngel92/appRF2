package com.example.apprf.ui.fragments

import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.apprf.R
import com.example.apprf.application.PracticaRFApp
import com.example.apprf.data.GameRepository
import com.example.apprf.data.remote.model.GameDto
import com.example.apprf.databinding.FragmentGamesListBinding
import com.example.apprf.ui.MainActivity
import com.example.apprf.ui.adapters.GamesAdapter
import com.example.apprf.util.Constants
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class GamesListFragment : Fragment() {

    lateinit var mp: MediaPlayer

    private var _binding: FragmentGamesListBinding? = null
    private val binding get() = _binding!!

    private lateinit var repository: GameRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentGamesListBinding.inflate(inflater, container, false)

        //if (requireActivity() is MainActivity) {
        //    val mainActivity = requireActivity() as MainActivity
          //  mainActivity.mp.start()

            mp = MediaPlayer.create(requireContext(), R.raw.zelda)
            mp.isLooping = true  // Reproducir en bucle
            mp.start()  // Comenzar la reproducción


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)








        repository = (requireActivity().application as PracticaRFApp).repository

        lifecycleScope.launch {
            val call: Call<List<GameDto>> = repository.getGames("products/products_list")

            call.enqueue(object: Callback<List<GameDto>>{
                override fun onResponse(
                    call: Call<List<GameDto>>,
                    response: Response<List<GameDto>>
                ) {

                    binding.pbLoading.visibility = View.GONE
                    Log.d(Constants.LOGTAG, "Respuesta del servidor ${response.body()}")



                    response.body()?.let{games ->
                        binding.rvGames.apply {
                            layoutManager = LinearLayoutManager(requireContext())
                            adapter = GamesAdapter(games){game->
                                game.id?.let {id ->
                                    //aqui va el codigo para la operación para ver los detalles
                                    requireActivity().supportFragmentManager.beginTransaction()
                                        .replace(R.id.fragment_container, GameDetailFragment.newInstance(id))
                                        .addToBackStack(null)
                                        .commit()
                                    //if (requireActivity() is MainActivity) {
                                        //val mainActivity = requireActivity() as MainActivity
                                       // mainActivity.mp.stop()}
                                }
                            }
                        }
                    }

                }

                override fun onFailure(call: Call<List<GameDto>>, t: Throwable) {
                    Log.d(Constants.LOGTAG, "Error: ${t.message}")

                    Toast.makeText(requireActivity(), "no hay conexión", Toast.LENGTH_SHORT).show()

                    binding.pbLoading.visibility = View.GONE
                }

            })


        }
    }


    override fun onResume() {
        super.onResume()
        // Comenzar la reproducción nuevamente al volver al fragmento
        //if (requireActivity() is MainActivity) {
           // val mainActivity = requireActivity() as MainActivity
           // mainActivity.mp.start()}
        mp.start()
    }


    override fun onDestroyView() {
        super.onDestroyView()
        // Liberar los recursos del MediaPlayer cuando el fragmento se destruye
        mp.release()
    }


    override fun onDestroy() {
        super.onDestroy()

        //mp.stop()

        //if (requireActivity() is MainActivity) {
          //  val mainActivity = requireActivity() as MainActivity
           // mainActivity.mp.stop()}

        _binding = null
    }

}