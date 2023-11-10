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
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response



private const val GAME_ID = "game_id"



class GameDetailFragment : Fragment(), OnMapReadyCallback {

    private var call: Call<GameDetailDto>? = null


    private lateinit var mapFragment: SupportMapFragment


    private lateinit var map: GoogleMap


    private var game_id: String? = null

    private var long: Double? = null
    private var lat: Double? = null

    private var _binding: FragmentGameDetailBinding? = null
    private val binding get() = _binding!!

    private lateinit var repository: GameRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {args->
            game_id = args.getString(GAME_ID)

            Log.d(Constants.LOGTAG, "Id recibido: $game_id")




            repository = (requireActivity().application as PracticaRFApp).repository

            lifecycleScope.launch {
                game_id?.let{id->
                    call = repository.getGameDetailApiary(id)
                    //val call: Call<GameDetailDto> = repository.getGameDetailApiary(id)

                    call!!.enqueue(object: Callback<GameDetailDto>{
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

                                long= response.body()?.longitud
                                lat =response.body()?.latitud

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


//********************************************



        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)



        return binding.root
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

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
        createMarker()


    }

    private fun createMarker(){
        if (!::map.isInitialized) {
            map.clear() // Limpia todos los marcadores
        }

        //val coordinates = LatLng( 0.0,  0.0)
        //val coordinates = LatLng(lat!!, long!!)
        val coordinates = LatLng(lat ?: 0.0, long ?: 0.0)
        val marker = MarkerOptions()
            .position(coordinates)
            .title("DGTIC-UNAM")
            .snippet("Cursos y diplomados en tic")
            .icon(BitmapDescriptorFactory.fromResource(R.drawable.school))

        map.addMarker(marker)

        map.animateCamera(
            CameraUpdateFactory.newLatLngZoom(coordinates, 18f),
            4000,
            null
        )

    }




    override fun onDestroyView() {
        super.onDestroyView()
        if (!::map.isInitialized) {
            map.clear() // Limpia todos los marcadores
        }

        call?.cancel()

        _binding = null

    }


    override fun onDestroy() {
        super.onDestroy()

        _binding = null
    }






}