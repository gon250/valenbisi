package com.gonzalo.valenbisi.models


data class Station(val name: String, val address: String, val slots: Int, val emptySlots: Int,
                   val freeBikes: Int, val status: String, val distance: Double, val lastUpdate: Long)