package com.gonzalo.valenbisi.adapters

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.gonzalo.valenbisi.models.Station
import com.gonzalo.valenbisi.R
import com.gonzalo.valenbisi.db.DBHelper
import com.gonzalo.valenbisi.db.models.BookMark
import com.gonzalo.valenbisi.helpers.inflate
import kotlinx.android.synthetic.main.item_station.view.*
import org.jetbrains.anko.db.*
import org.jetbrains.anko.image

class AdapterStation(val data: List<Station?>?) : RecyclerView.Adapter<AdapterStation.Holder>() {

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): Holder =
            Holder(parent?.inflate(R.layout.item_station))

    override fun onBindViewHolder(holder: Holder?, position: Int) {
            data?.let {
            holder?.bindView(it[position])
        }
    }

    override fun getItemCount(): Int = data?.size ?: 0

    class Holder(itemView: View?) :  RecyclerView.ViewHolder(itemView) {
        fun bindView(station: Station?) {
            val db = DBHelper.getInstance(itemView.context)
            station.let {
                with(it) {
                    val stationId = it?.stationId
                    val currentBookMark = getCurrentBookMark(db, stationId)

                    itemView.txtStationName.text = it?.name
                    itemView.txtStationBikes.text = it?.freeBikes.toString()
                    itemView.txtStationSlots.text = it?.slots.toString()
                    itemView.txtStationDate.text = it?.lastUpdate.toString()
                    itemView.txtStationDistance.text = "0.00 m" //TODO: Calculate Distance
                    setFavIcon(currentBookMark.active)

                    itemView.favBtn.setOnClickListener {
                        val updatetBookMark = getCurrentBookMark(db, stationId)
                        db.use {
                            update(BookMark.TABLE_NAME, BookMark.COLUMN_ACTIVE to if (updatetBookMark.active == 0) 1 else 0)
                                    .whereArgs("stationId = {stationId}", "stationId" to stationId.toString())
                                    .exec()
                        }
                        setFavIcon(if (updatetBookMark.active == 0) 1 else 0)
                    }
                }
            }
        }

        private fun getCurrentBookMark(db: DBHelper, stationId: String?): BookMark {
            return db.use {
                select(BookMark.TABLE_NAME)
                        .whereArgs("stationId = {stationId}", "stationId" to stationId.toString())
                        .exec { parseSingle<BookMark>(classParser()) }
            }
        }

        private fun setFavIcon(active: Int) {
            when(active) {
                0 -> itemView.favBtn.image = itemView.context.getDrawable(R.drawable.ic_favorite_border)
                1 -> itemView.favBtn.image = itemView.context.getDrawable(R.drawable.ic_favorite)
            }
        }

    }

}