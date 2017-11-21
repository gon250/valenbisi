package com.gonzalo.valenbisi.adapters

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.gonzalo.valenbisi.models.Station
import com.gonzalo.valenbisi.R
import com.gonzalo.valenbisi.db.DBHelper
import com.gonzalo.valenbisi.helpers.inflate
import kotlinx.android.synthetic.main.item_station.view.*
import org.jetbrains.anko.db.insert

class AdapterStation(val data: List<Station?>?) : RecyclerView.Adapter<AdapterStation.Holder>() {

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): Holder =
            Holder(parent?.inflate(R.layout.item_station))

    override fun onBindViewHolder(holder: Holder?, position: Int) {
        data?.let {
            holder?.bindView(it[position])
        }
    }

    override fun getItemCount(): Int {
        return data?.size ?: 0
    }

    class Holder(itemView: View?) :  RecyclerView.ViewHolder(itemView) {
        fun bindView(station: Station?) {
            station.let {
                with(it) {
                    val stId = it?.stationId
                    val stName = it?.name
                    val stStatus = "1" //TODO: Implement Status

                    itemView.txtStationName.text = it?.stationId
                    itemView.txtStationBikes.text = it?.freeBikes.toString()
                    itemView.txtStationSlots.text = it?.slots.toString()
                    itemView.txtStationDate.text = it?.lastUpdate.toString()
                    itemView.txtStationDistance.text = "0.00 m" //TODO: Calculate Distance

                    itemView.favBtn.setOnClickListener {
                        val db = DBHelper.getInstance(itemView.context)
                        db?.use {
                            val stationId = "stationId" to stId
                            val stationName = "name" to stName
                            val stationStatus = "status" to stStatus
                            insert("Station",  stationId, stationName, stationStatus)
                        }
                        //TODO: Change image icon.
                    }
                }
            }
        }

    }

}