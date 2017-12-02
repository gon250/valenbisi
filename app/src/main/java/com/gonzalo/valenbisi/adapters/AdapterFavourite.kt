package com.gonzalo.valenbisi.adapters

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.gonzalo.valenbisi.R
import com.gonzalo.valenbisi.helpers.inflate
import com.gonzalo.valenbisi.models.Station

class AdapterFavourite(val data: List<Station?>?) : RecyclerView.Adapter<AdapterFavourite.Holder>() {
    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): Holder =
            AdapterFavourite.Holder(parent?.inflate(R.layout.item_station))

    override fun onBindViewHolder(holder: Holder?, position: Int) {
        data?.let {
            holder?.bindView(it[position])
        }
    }

    override fun getItemCount(): Int = data?.size ?: 0


    class Holder(itemView: View?) :  RecyclerView.ViewHolder(itemView) {
        fun bindView(station: Station?) {

        }
    }
}