package com.example.dog_inder.ui.adapter

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.AsyncTask
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import com.example.dog_inder.R
import com.example.dog_inder.utils.model.Card
import java.io.InputStream
import java.net.URL


class ListAdapter(val context: Context, val list: ArrayList<Card>): BaseAdapter() {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View = LayoutInflater.from(context).inflate(R.layout.fragment_card, parent, false)

        val cardImg = view.findViewById(R.id.card_img) as ImageView
        DownLoadImageTask(cardImg).execute(list[position].img)

        return view
    }

    override fun getItem(position: Int): Any {
        return list[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return list.size
    }

    private class DownLoadImageTask(var imageView: ImageView) :
        AsyncTask<String?, Void?, Bitmap?>() {

        override fun onPostExecute(result: Bitmap?) {
            imageView.setImageBitmap(result)
        }

        override fun doInBackground(vararg urls: String?): Bitmap? {
            val urlOfImage = urls[0]
            var logo: Bitmap? = null
            try {
                val mis: InputStream = URL(urlOfImage).openStream()
                 logo = BitmapFactory.decodeStream(mis)
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return logo
        }

    }

}