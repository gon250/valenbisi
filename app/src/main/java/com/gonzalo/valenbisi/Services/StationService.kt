package com.gonzalo.valenbisi.Services

import com.gonzalo.valenbisi.POJORetrofit.ResponseStations
import retrofit2.Call
import retrofit2.http.GET

interface StationService {
    @GET("valenbisi")
    fun getStations() : Call<ResponseStations>
}