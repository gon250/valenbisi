package com.gonzalo.valenbisi.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.util.Log
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

        fun getInstance(ctx: Context): DBHelper {
            if (instance == null) {
                instance = DBHelper(ctx.getApplicationContext())
            }
            return instance!!
        }
    }

    override fun onCreate(db: SQLiteDatabase?) {
        db?.createTable("BookMark", true,
                "id" to INTEGER + PRIMARY_KEY + AUTOINCREMENT + UNIQUE,
                "stationId" to TEXT,
                "name" to TEXT,
                "active" to INTEGER)
        initBookMarks(db)
    }

    override fun onUpgrade(db: SQLiteDatabase?, old: Int, newV: Int) {
        //TODO: Upgrade tables, as usual
        db?.dropTable("BookMark", true)
    }

    val Context.database: DBHelper?
        get() = getInstance(applicationContext)


    private fun initBookMarks(db: SQLiteDatabase?) {
        val retrofit = Retrofit.Builder()
                .baseUrl("http://api.citybik.es/v2/networks/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        val stations = retrofit.create(StationService::class.java)

        val call = stations.getStations()

        call.enqueue(object : Callback<ResponseStations> {
            override fun onResponse(call: Call<ResponseStations>?, response: Response<ResponseStations>?) {
                if (response?.code() == 200) {
                    response.body()?.network?.stations?.forEach {
                        with(it) {
                            db?.insert("BookMark",
                                    "stationId" to it?.id,
                                    "name" to it?.name,
                                    "active" to 0
                            )
                        }
                    }
                }
            }

            override fun onFailure(call: Call<ResponseStations>?, t: Throwable?) {
                //TODO: Implement on error call.
            }
        })
    }
}