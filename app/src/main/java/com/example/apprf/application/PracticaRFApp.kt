package com.example.apprf.application

import android.app.Application
import com.example.apprf.data.GameRepository
import com.example.apprf.data.remote.RetrofitHelper

class PracticaRFApp: Application() {

    private val retrofit by lazy{
        RetrofitHelper().getRetrofit()
    }

    val repository by lazy{
        GameRepository(retrofit)
    }

}