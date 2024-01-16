package com.kuantum.artbook.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.kuantum.artbook.R
import com.kuantum.artbook.model.Language

class LanguageSpinnerAdapter(context: Context, list: List<Language>) :
    ArrayAdapter<Language>(context, 0, list) {

    private var layoutInflater = LayoutInflater.from(context)

    @SuppressLint("InflateParams")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var view: View? = convertView
        if (view == null) {
            view = layoutInflater.inflate(R.layout.language_spinner_bg,null, false)
        }

        val spinnerBg = view?.findViewById<ImageView>(R.id.languageSpinnerBg)
        spinnerBg!!.setBackgroundResource(getItem(position)!!.flag)
        return view!!
    }

    @SuppressLint("InflateParams")
    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        var view: View? = convertView
        if (view == null) {
            view = layoutInflater.inflate(R.layout.language_spinner_item, null, false)
        }
        val spinnerItemTxt = view?.findViewById<TextView>(R.id.spinnerItemTxt)
        val spinnerItemFlag = view?.findViewById<ImageView>(R.id.spinnerItemFlag)

        spinnerItemTxt!!.text = getItem(position)!!.language
        spinnerItemFlag!!.setBackgroundResource(getItem(position)!!.flag)

        return view!!
    }
}