package com.craftie.utils

object Endpoints {
    private const val BASE_URL = "https://craftie-api.herokuapp.com"

    const val BEERS_ENDPOINT = "$BASE_URL/api/v1/beers"
    const val BEERS_FEATURED_ENDPOINT = "$BEERS_ENDPOINT/featured"
    const val BREWERIES_ENDPOINT = "$BASE_URL/api/v1/breweries"
    const val PROVINCES_ENDPOINT = "$BASE_URL/api/v1/provinces"
    const val RATINGS_ENDPOINT = "$BASE_URL/api/v1/ratings"
}