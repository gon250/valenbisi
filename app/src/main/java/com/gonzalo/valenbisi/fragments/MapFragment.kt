package com.gonzalo.valenbisi.fragments


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.gonzalo.valenbisi.R

class MapFragment : Fragment() {

    companion object {
        val TAG: String = MapFragment::class.java.simpleName
        fun newInstance() = MapFragment()
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        activity.title = getString(R.string.title_home)
        val view = inflater?.inflate(R.layout.fragment_map, container, false)
        return view
    }

}
