package com.rodovalho.pokedex.extension

import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import java.lang.Exception

fun ImageView.load(url: String, progressBar: ProgressBar, textView: TextView) {
    Picasso.get()
        .load(url)
        .centerCrop()
        .into(this, object : Callback {
            override fun onSuccess() {
                progressBar.visibility = View.GONE
                textView.visibility = View.GONE
            }
            override fun onError(e: Exception?) {
                progressBar.visibility = View.GONE
                textView.visibility = View.VISIBLE
            }
        })
}