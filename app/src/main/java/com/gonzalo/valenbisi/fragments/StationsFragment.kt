package com.gonzalo.valenbisi.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.gonzalo.valenbisi.models.Station
import com.gonzalo.valenbisi.pojo.ResponseStations
import com.gonzalo.valenbisi.pojo.StationsItem

import com.gonzalo.valenbisi.R
import com.gonzalo.valenbisi.services.StationService
import com.gonzalo.valenbisi.adapters.AdapterStation
import kotlinx.android.synthetic.main.fragment_stations.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class StationsFragment : Fragment() {

    companion object {
        val TAG: String = StationsFragment::class.java.simpleName
        fun newInstance() = StationsFragment()
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        activity.title = getString(R.string.title_home)
        val view = inflater?.inflate(R.layout.fragment_stations, container, false)

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        rcViewStations.layoutManager = LinearLayoutManager(activity)

        val retrofit = Retrofit.Builder()
                .baseUrl("http://api.citybik.es/v2/networks/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        val stations = retrofit.create(StationService::class.java)

        val call = stations.getStations()

        call.enqueue(object : Callback<ResponseStations> {

            override fun onResponse(call: Call<ResponseStations>?, response: Response<ResponseStations>?) {
                if (response?.code() == 200) {
                    Log.e("Respuesta", "${response.body().toString()}")
                    loadData(response.body()?.network?.stations)
                }
            }

            override fun onFailure(call: Call<ResponseStations>?, t: Throwable?) {
                //TODO: Implement on error call.
            }

        })
    }

    private fun loadData(stations: List<StationsItem?>?) {
        val items = stations?.map {
            it?.let {
                Station(
                        it.name ?: "",
                        it.extra?.address ?: "",
                        it.extra?.slots ?: 0,
                        it.empty_slots ?: 0,
                        it.free_bikes ?: 0,
                        it.extra?.status ?: "",
                        0.00,
                        it.extra?.lastUpdate ?: 0
                )
            }
        }

        rcViewStations.adapter = AdapterStation(items)

    }

}
