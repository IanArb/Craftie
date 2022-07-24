package com.craftie.utils

object Endpoints {
    private const val BASE_URL = "https://craftie-api.herokuapp.com/api/v1"
    const val PRODUCTION_BASE_URL = "https://craftie.app/api/v1"

    const val BEERS_ENDPOINT = "/beers"
    const val BEERS_FEATURED_ENDPOINT = "$BEERS_ENDPOINT/featured"
    const val BEERS_KEYWORD_ENDPOINT = "$BEERS_ENDPOINT/query"
    const val BREWERIES_ENDPOINT = "/breweries"
    const val PROVINCES_ENDPOINT = "/provinces"
    const val AVERAGE_RATING_ENDPOINT = "/ratings/rating"
    const val RATINGS_ENDPOINT = "/ratings"
    const val AUTHENTICATION_ENDPOINT = "/login"
    const val PROVINCES_COUNT_ENDPOINT = "/beersCount"
}