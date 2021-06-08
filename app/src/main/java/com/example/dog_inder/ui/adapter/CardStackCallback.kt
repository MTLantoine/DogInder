package com.example.dog_inder.ui.adapter

import androidx.recyclerview.widget.DiffUtil
import com.example.dog_inder.utils.model.Card

class CardStackCallback(
    old: List<Card>,
    newCard: List<Card>
) :
    DiffUtil.Callback() {
    private val old: List<Card> = old
    private val newCard: List<Card> = newCard
    override fun getOldListSize(): Int {
        return old.size
    }

    override fun getNewListSize(): Int {
        return newCard.size
    }

    override fun areItemsTheSame(
        oldItemPosition: Int,
        newItemPosition: Int
    ): Boolean {
        return old[oldItemPosition].getImage() === newCard[newItemPosition].getImage()
    }

    override fun areContentsTheSame(
        oldItemPosition: Int,
        newItemPosition: Int
    ): Boolean {
        return old[oldItemPosition] === newCard[newItemPosition]
    }

}
