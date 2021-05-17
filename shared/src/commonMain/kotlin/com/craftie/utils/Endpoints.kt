package com.craftie.utils

object Endpoints {
    private const val BASE_URL = "https://craftie-api.herokuapp.com/api/v1"

    const val BEERS_ENDPOINT = "$BASE_URL/beers"
    const val BEERS_FEATURED_ENDPOINT = "$BEERS_ENDPOINT/featued"
    const val BREWERIES_ENDPOINT = "$BASE_URL/breweries"
}