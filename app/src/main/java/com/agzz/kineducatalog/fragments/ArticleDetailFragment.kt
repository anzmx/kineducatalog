package com.agzz.kineducatalog.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.agzz.kineducatalog.R

class ArticleDetailFragment: Fragment() {
    companion object {
        fun newInstance(): ArticleDetailFragment {
            return ArticleDetailFragment()
        }
    }
    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_articles_detail, container, false)
        }
}