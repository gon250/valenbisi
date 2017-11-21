package com.gonzalo.valenbisi.services

import com.gonzalo.valenbisi.pojo.ResponseStations
import retrofit2.Call
import retrofit2.http.GET

interface StationService {
    @GET("valenbisi")
    fun getStations() : Call<ResponseStations>
}