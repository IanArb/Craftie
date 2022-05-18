package com.craftie.utils

object Endpoints {
    private const val BASE_URL = "https://craftie-api.herokuapp.com/api/v1"

    const val BEERS_ENDPOINT = "$BASE_URL/beers"
    const val BEERS_FEATURED_ENDPOINT = "$BEERS_ENDPOINT/featured"
    const val BEERS_KEYWORD_ENDPOINT = "$BEERS_ENDPOINT/query"
    const val BREWERIES_ENDPOINT = "$BASE_URL/breweries"
    const val PROVINCES_ENDPOINT = "$BASE_URL/provinces"
    const val AVERAGE_RATING_ENDPOINT = "$BASE_URL/ratings/rating"
    const val RATINGS_ENDPOINT = "$BASE_URL/ratings"
    const val AUTHENTICATION_ENDPOINT = "$BASE_URL/login"
    const val PROVINCES_COUNT_ENDPOINT = "$BASE_URL/beersCount"
}