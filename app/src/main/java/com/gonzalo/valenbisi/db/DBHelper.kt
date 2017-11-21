package com.gonzalo.valenbisi.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import org.jetbrains.anko.db.*


class DBHelper(ctx: Context) : ManagedSQLiteOpenHelper(ctx, "Valenbisi", null, 1) {

    val stationDb = "Station"

    companion object {
        private var instance: DBHelper? = null

        fun getInstance(ctx: Context): DBHelper? = if (instance == null) {
            instance = DBHelper(ctx)
            instance
        } else {
            instance
        }
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val id = "id" to INTEGER + PRIMARY_KEY
        val stationId = "stationId" to TEXT
        val name = "name" to TEXT
        val status = "status" to REAL
        db?.createTable(stationDb, true, id, stationId, name, status)
    }

    override fun onUpgrade(db: SQLiteDatabase?, old: Int, newV: Int) {
        db?.dropTable(stationDb, true)
    }

    val Context.database: DBHelper?
        get() = getInstance(applicationContext)
}