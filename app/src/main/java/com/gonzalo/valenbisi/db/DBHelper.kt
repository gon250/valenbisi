package com.gonzalo.valenbisi.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import com.gonzalo.valenbisi.db.models.BookMark
import com.gonzalo.valenbisi.pojo.ResponseStations
import com.gonzalo.valenbisi.services.StationService
import org.jetbrains.anko.db.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class DBHelper(ctx: Context) : ManagedSQLiteOpenHelper(ctx, "ValenbisiDB", null, 1) {
    companion object {
        private var instance: DBHelper? = null

        @Synchronized
        fun getInstance(ctx: Context): DBHelper {
            if (instance == null) {
                instance = DBHelper(ctx.getApplicationContext())
            }
            return instance!!
        }
    }

    override fun onCreate(db: SQLiteDatabase?) {
        db?.createTable(BookMark.TABLE_NAME, true,
                BookMark.COLUMN_ID to INTEGER + PRIMARY_KEY + AUTOINCREMENT + UNIQUE,
                BookMark.COLUMN_STATION_ID to TEXT,
                BookMark.COLUMN_NAME to TEXT,
                BookMark.COLUMN_ACTIVE to INTEGER)
    }

    override fun onUpgrade(db: SQLiteDatabase?, old: Int, newV: Int) {
        //TODO: Upgrade tables, as usual
        db?.dropTable(BookMark.TABLE_NAME, true)
    }

    val Context.database: DBHelper?
        get() = getInstance(applicationContext)

}