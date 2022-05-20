package com.craftie.android.utils

import com.craftie.android.presentation.home.FavouriteBeersUiData
import com.craftie.data.model.*

object StubData {

    fun beers(): List<Beer> {
        val arcadia = Beer(
            "1",
            "Arcadia",
            "Arcadia",
            "",
            "https://firebasestorage.googleapis.com/v0/b/craftie-91fee.appspot.com/o/beers%2FArcadia_gluten_free.png?alt=media&token=3dffbb0f-8843-4b67-91ee-2e2e4fbae804",
            "",
            "",
            Amount(
                10.0f,
                "unit"
            ),
            Amount(
                10.0f,
                "unit"
            ),
            BreweryInfo(
                "",
                "",
                "",
                Location(
                    "Wicklow",
                    "Leinster",
                    "",
                    LatLng(
                        10.0,
                        10.0
                    )
                )
            ),
            true,
            creationDate = "2021-12-04T16:59:40.599613",
        )

        val elevation = Beer(
            "1",
            "Arcadia",
            "Arcadia",
            "",
            "https://firebasestorage.googleapis.com/v0/b/craftie-91fee.appspot.com/o/beers%2FElevation_pale_ale.png?alt=media&token=e5fbe476-dfeb-41ac-87d8-c2698099313c",
            "",
            "",
            Amount(
                10.0f,
                "unit"
            ),
            Amount(
                10.0f,
                "unit"
            ),
            BreweryInfo(
                "",
                "",
                "",
                Location(
                    "Wicklow",
                    "Leinster",
                    "",
                    LatLng(
                        10.0,
                        10.0
                    )
                )
            ),
            false,
            creationDate = "2021-12-04T16:59:40.599613",
        )

        val mamoth = Beer(
            "1",
            "Arcadia",
            "Arcadia",
            "",
            "https://firebasestorage.googleapis.com/v0/b/craftie-91fee.appspot.com/o/beers%2Fmammoth.png?alt=media&token=b6c970db-3a54-41fb-b4a6-72d7cc6a3ba3",
            "",
            "",
            Amount(
                10.0f,
                "unit"
            ),
            Amount(
                10.0f,
                "unit"
            ),
            BreweryInfo(
                "",
                "",
                "",
                Location(
                    "Wicklow",
                    "Leinster",
                    "",
                    LatLng(
                        10.0,
                        10.0
                    )
                )
            ),
            false,
            creationDate = "2021-12-04T16:59:40.599613",
        )

        return listOf(arcadia, elevation, mamoth, mamoth)
    }

    private fun info(): Info = Info(
        count = 0,
        next = 1,
        previous = null,
    )

    fun breweries(): List<Brewery> {
        val fiveLamps = Brewery(
            "1",
            "Five Lamps",
            "Five Lamps",
            Location(
                "",
                "",
                "",
                LatLng(10.0, 10.0)
            ),
            "https://firebasestorage.googleapis.com/v0/b/craftie-91fee.appspot.com/o/brewery%2F5_Lamps.png?alt=media&token=ab88b673-536e-465e-a949-2aa3842272a2",
            ""
        )
        val rascals = Brewery(
            "1",
            "Rascals",
            "Rascals",
            Location(
                "",
                "",
                "",
                LatLng(10.0, 10.0)
            ),
            "https://firebasestorage.googleapis.com/v0/b/craftie-91fee.appspot.com/o/brewery%2FRascals.png?alt=media&token=52027a30-e445-4a1e-82f7-c061405d3c53",
            ""
        )
        val mcGargles = Brewery(
            "1",
            "McGargles",
            "McGargles",
            Location(
                "",
                "",
                "",
                LatLng(10.0, 10.0)
            ),
            "https://firebasestorage.googleapis.com/v0/b/craftie-91fee.appspot.com/o/brewery%2FMcGargles.png?alt=media&token=dc09469c-1a7d-40b2-88c2-7a03390237a0",
            ""
        )

        val wicklowWolf = Brewery(
            "1",
            "Wicklow Wolf",
            "Wicklow Wolf",
            Location(
                "",
                "",
                "",
                LatLng(10.0, 10.0)
            ),
            "https://firebasestorage.googleapis.com/v0/b/craftie-91fee.appspot.com/o/brewery%2FWicklow_Wolf.png?alt=media&token=f28d3c65-ee80-4908-8889-cd9376824425",
            ""
        )

        val obrother = Brewery(
            "1",
            "OBrother",
            "OBrother",
            Location(
                "",
                "",
                "",
                LatLng(10.0, 10.0)
            ),
            "https://firebasestorage.googleapis.com/v0/b/craftie-91fee.appspot.com/o/brewery%2FOBrother.png?alt=media&token=f9e46a80-1bcc-438e-b35b-ce3b091a00d7",
            ""
        )

        return listOf(rascals, obrother, mcGargles, fiveLamps, wicklowWolf)
    }

    fun featuredBeer() = Beer(
        "1",
        "Arcadia",
        "Arcadia",
        "",
        "https://firebasestorage.googleapis.com/v0/b/craftie-91fee.appspot.com/o/beers%2Fmammoth.png?alt=media&token=b6c970db-3a54-41fb-b4a6-72d7cc6a3ba3",
        "",
        "",
        Amount(
            10.0f,
            "unit"
        ),
        Amount(
            10.0f,
            "unit"
        ),
        BreweryInfo(
            "",
            "",
            "",
            Location(
                "Wicklow",
                "Leinster",
                "",
                LatLng(
                    10.0,
                    10.0
                )
            )
        ),
        true,
        creationDate = "2021-12-04T16:59:40.599613",
    )

    fun aleBeers(): Pagination<Beer> {
        val elevation = Beer(
            "1",
            "Arcadia",
            "Arcadia",
            "",
            "https://firebasestorage.googleapis.com/v0/b/craftie-91fee.appspot.com/o/beers%2FElevation_pale_ale.png?alt=media&token=e5fbe476-dfeb-41ac-87d8-c2698099313c",
            "Ale",
            "Ale",
            Amount(
                10.0f,
                "unit"
            ),
            Amount(
                10.0f,
                "unit"
            ),
            BreweryInfo(
                "",
                "",
                "",
                Location(
                    "Wicklow",
                    "Leinster",
                    "",
                    LatLng(
                        10.0,
                        10.0
                    )
                )
            ),
            false,
            creationDate = "2021-12-04T16:59:40.599613",
        )

        val fiveLamps = Beer(
            "1",
            "Five Lamps Ale",
            "Five Lamps Ale",
            "",
            "https://firebasestorage.googleapis.com/v0/b/craftie-91fee.appspot.com/o/beers%2FElevation_pale_ale.png?alt=media&token=e5fbe476-dfeb-41ac-87d8-c2698099313c",
            "Ale",
            "Ale",
            Amount(
                10.0f,
                "unit"
            ),
            Amount(
                10.0f,
                "unit"
            ),
            BreweryInfo(
                "",
                "",
                "",
                Location(
                    "Wicklow",
                    "Leinster",
                    "",
                    LatLng(
                        10.0,
                        10.0
                    )
                )
            ),
            false,
            creationDate = "2021-12-04T16:59:40.599613",
        )

        val results =  listOf(elevation, fiveLamps)

        return Pagination(
            info = info(),
            results = results
        )
    }

    fun provinces(): List<Province> {
        return listOf(
            Province(
                "1",
                "Connaught",
                "https://firebasestorage.googleapis.com/v0/b/craftie-91fee.appspot.com/o/general_ui%2FConnaught.png?alt=media&token=32b6f284-d5ec-4ab4-b9e2-c71d15de999e"
            ),
            Province(
                "2",
                "Leinster",
                "https://firebasestorage.googleapis.com/v0/b/craftie-91fee.appspot.com/o/general_ui%2FLeinster.png?alt=media&token=8cb29e1f-a9ad-46ee-8c8c-52b6737a22e9"
            ),
            Province(
                "3",
                "Munster",
                "https://firebasestorage.googleapis.com/v0/b/craftie-91fee.appspot.com/o/general_ui%2FMunster.png?alt=media&token=ff179873-d7ac-45cf-a4de-358c70f64272"
            ),
            Province(
                "4",
                "Ulster",
                "https://firebasestorage.googleapis.com/v0/b/craftie-91fee.appspot.com/o/general_ui%2FUlster.png?alt=media&token=4ff562fe-4b5f-4a6f-aeff-fc770b0cf450"
            )
        )
    }

    fun provinceCount() = ProvincesCount(
        leinster = Leinster(
            name = "Leinster",
            amount = 10,
            imageUrl = "www.image.com"
        ),

        ulster = Ulster(
            name = "Ulster",
            amount = 5,
            imageUrl = "www.image.com"
        ),

        munster = Munster(
            name = "Munster",
            amount = 15,
            imageUrl = "www.image.com"
        ),

        connaught = Connaught(
            name = "Connaught",
            amount = 20,
            imageUrl = "www.image.com"
        )
    )

    fun favouriteBeersUiData(): List<FavouriteBeersUiData> {
        val leinster = FavouriteBeersUiData(
            name = "Leinster",
            value = 10,
            imageUrl = "www.image.com",
        )

        val munster = FavouriteBeersUiData(
            name = "Munster",
            value = 7,
            imageUrl = "www.image.com",
        )

        val connaught = FavouriteBeersUiData(
            name = "Connaught",
            value = 5,
            imageUrl = "www.image.com",
        )

        val ulster = FavouriteBeersUiData(
            name = "Ulster",
            value = 20,
            imageUrl = "www.image.com",
        )

        return listOf(leinster, munster, connaught, ulster)
    }

    fun favourites(): List<FavouriteBeerUiData> {
        val leinster = FavouriteBeerUiData(
            id = "1",
            name = "Leinster",
            province = "Leinster",
            imageUrl = "www.image.com",
        )

        val munster = FavouriteBeerUiData(
            id = "2",
            name = "Munster",
            imageUrl = "www.image.com",
            province = "Munster"
        )

        val connaught = FavouriteBeerUiData(
            id = "2",
            name = "Connaught",
            imageUrl = "www.image.com",
            province = "Connaught"
        )

        val ulster = FavouriteBeerUiData(
            id = "2",
            name = "Ulster",
            imageUrl = "www.image.com",
            province = "Ulster"
        )

        return listOf(leinster, munster, connaught, ulster)
    }

}