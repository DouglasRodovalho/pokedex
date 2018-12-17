package com.rodovalho.pokedex.model

data class Pokemon (
    val id: Int,
    val name: String,
    val weight: Int,
    val height: Int,
    val order: Int,
    val sprites: Sprite
)