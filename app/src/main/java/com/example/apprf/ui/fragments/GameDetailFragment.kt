package com.example.apprf.ui.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import android.widget.VideoView
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.example.apprf.R
import com.example.apprf.application.PracticaRFApp
import com.example.apprf.data.GameRepository
import com.example.apprf.data.remote.model.GameDetailDto
import com.example.apprf.databinding.FragmentGameDetailBinding
import com.example.apprf.util.Constants
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


private const val GAME_ID = "game_id"



class GameDetailFragment : Fragment() {


    //nuevo codigo  ****************
    //private lateinit var videoView: VideoView

    private var game_id: String? = null

    private var _binding: FragmentGameDetailBinding? = null
    private val binding get() = _binding!!

    private lateinit var repository: GameRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {args->
            game_id = args.getString(GAME_ID)

            Log.d(Constants.LOGTAG, "Id recibido: $game_id")


            // Inicializar el VideoView  ***********************
            //videoView = view.findViewById(R.id.videoView)
            //val packageName = requireActivity().packageName
            //binding.videoView.setVideoPath("android.resource://${packageName}/${R.raw.maverick}")
           // binding.videoView.setVideoPath("android.resource://${requireActivity().packageName}/${R.raw.maverick}")

            //val videoPath = "android.resource://$packageName/${R.raw.maverick}"
            //binding.videoView.setVideoPath(videoPath)

          // binding.videoView.start()

            repository = (requireActivity().application as PracticaRFApp).repository

            lifecycleScope.launch {
                game_id?.let{id->
                    val call: Call<GameDetailDto> = repository.getGameDetailApiary(id)

                    call.enqueue(object: Callback<GameDetailDto>{
                        override fun onResponse(
                            call: Call<GameDetailDto>,
                            response: Response<GameDetailDto>
                        ) {


                            binding.apply {
                                pbLoading.visibility = View.GONE
                                tvTitle.text = response.body()?.title

                                tvLongDesc.text = response.body()?.longDesc

                                //**********************************
                                tvSeller.text = response.body()?.seller
                                tvUbiation.text = response.body()?.ubication
                                tvPrice.text = response.body()?.price
                                //**********************************

                                Glide.with(requireContext())
                                    .load(response.body()?.image)
                                    .into(ivImage)

                                binding.videoView.setVideoPath(response.body()?.video)
                                binding.videoView.start()
                            }
                        }

                        override fun onFailure(call: Call<GameDetailDto>, t: Throwable) {
                            binding.pbLoading.visibility = View.GONE

                            Toast.makeText(requireActivity(), "no hay conexión", Toast.LENGTH_SHORT).show()
                        }

                    })
                }
            }

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding= FragmentGameDetailBinding.inflate(inflater, container, false)

        // Aquí puedes acceder a las vistas a través de binding
       // val packageName = requireActivity().packageName
       // binding.videoView.setVideoPath("android.resource://${packageName}/${R.raw.maverick}")
       // binding.videoView.start()
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {

        @JvmStatic
        fun newInstance(gameId: String) =
            GameDetailFragment().apply {
                arguments = Bundle().apply {
                    putString(GAME_ID,gameId)

                }
            }
    }
}