package com.gonzalo.valenbisi.db.models

data class BookMark(val id: Int, val stationId: String, val name: String, val active: Int) {
    companion object {
        val TABLE_NAME = "BookMark"
        val COLUMN_ID = "id"
        val COLUMN_STATION_ID = "stationId"
        val COLUMN_NAME = "name"
        val COLUMN_ACTIVE = "active"
    }
}