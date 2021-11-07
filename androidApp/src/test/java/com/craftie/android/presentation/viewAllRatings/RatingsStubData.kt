package com.craftie.android.presentation.viewAllRatings

import com.craftie.data.model.RatingResponse

object RatingsStubData {

    fun ratings(): List<RatingResponse> {
        val buckleRating = RatingResponse(
            "1",
            "1",
            "Buckle",
            "Great Beer!",
            5.0,
            ""
        )

        val joshRating = RatingResponse(
            "1",
            "1",
            "Buckle",
            "Crap Beer!",
            2.0,
            ""
        )

        val foleyRating = RatingResponse(
            "1",
            "1",
            "Buckle",
            "Alright Beer",
            3.0,
            ""
        )

        return listOf(
            buckleRating,
            joshRating,
            foleyRating
        )
    }
}