package com.rodovalho.pokedex.model

data class Response (
    val count: Int,
    val results: List<Result>
)