package com.example.dog_inder.utils.model

class Card {

    private var image = ""

    constructor(image: String) {
        this.image = image
    }

    fun getImage(): String {
        return image
    }
}
