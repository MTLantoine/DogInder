package com.example.dog_inder.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.dog_inder.R
import com.example.dog_inder.utils.model.Card
import com.squareup.picasso.Picasso


class CardStackAdapter(private val list: List<Card>) : RecyclerView.Adapter<CardStackAdapter.ViewHolder>() {

    private var mList = list

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view: View = inflater.inflate(R.layout.item_card, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = mList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.setData(mList[position])
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val image: ImageView = itemView.findViewById(R.id.item_image)

        fun setData(data: Card) {
            Picasso.get()
                    .load(data.getImage())
                    .fit()
                    .centerCrop()
                    .into(image)
        }
    }

    fun getItems(): List<Card> {
        return mList
    }

    fun setItems(newList: List<Card>) {
        mList = newList
    }
}
