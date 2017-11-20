package com.gonzalo.valenbisi.Fragments


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.gonzalo.valenbisi.R


class FavoritesFragment : Fragment() {

    companion object {
        val TAG: String = FavoritesFragment::class.java.simpleName
        fun newInstance() = FavoritesFragment()
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        activity.title = getString(R.string.title_home)
        val view = inflater?.inflate(R.layout.fragment_favorites, container, false)
        return view
    }

}// Required empty public constructor
