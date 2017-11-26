package com.gonzalo.valenbisi

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentTransaction
import android.support.v7.app.AppCompatActivity
import com.gonzalo.valenbisi.db.DBHelper
import com.gonzalo.valenbisi.db.models.BookMark
import com.gonzalo.valenbisi.fragments.FavoritesFragment
import com.gonzalo.valenbisi.fragments.MapFragment
import com.gonzalo.valenbisi.fragments.StationsFragment
import com.gonzalo.valenbisi.pojo.ResponseStations
import com.gonzalo.valenbisi.services.StationService
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.parseList
import org.jetbrains.anko.db.select
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_station -> {
                switchFragment(StationsFragment.newInstance(), StationsFragment.TAG)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_favorite -> {
                switchFragment(FavoritesFragment.newInstance(), FavoritesFragment.TAG)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_map -> {
                switchFragment(MapFragment.newInstance(), MapFragment.TAG)
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

        initBookMark()

        //Init fragment.
        savedInstanceState ?: switchFragment(StationsFragment.newInstance(), StationsFragment.TAG)
    }

    private fun switchFragment(fragment: Fragment, tag: String): Boolean {
        if (fragment.isAdded) return false
        detachFragment()
        attachFragment(fragment, tag)
        supportFragmentManager.executePendingTransactions()
        return true
    }

    private fun detachFragment() {
        supportFragmentManager.findFragmentById(R.id.container)?.also {
            supportFragmentManager.beginTransaction().detach(it).commit()
        }
    }

    private fun attachFragment(fragment: Fragment, tag: String) {
        if (fragment.isDetached) {
            supportFragmentManager.beginTransaction().attach(fragment).commit()
        } else {
            supportFragmentManager.beginTransaction().add(R.id.container, fragment, tag).commit()
        }
        // Set a transition animation for this transaction.
        supportFragmentManager.beginTransaction()
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .commit()
    }

    private fun initBookMark() {
        val db = DBHelper.getInstance(this)
        val bookmarks = db.use {
            select(BookMark.TABLE_NAME).exec { parseList<BookMark>(classParser()).size }
        }

        if(bookmarks.equals(0)) {
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
                                db.use {
                                    insert(BookMark.TABLE_NAME,
                                            BookMark.COLUMN_STATION_ID to it?.id,
                                            BookMark.COLUMN_NAME to it?.name,
                                            BookMark.COLUMN_ACTIVE to 0
                                    )
                                }
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
}
