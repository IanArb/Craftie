package com.craftie.android.util

import com.craftie.data.model.*

object MockData {
    fun beers(): List<Beer> {
        val arcadia = Beer(
            "1",
            "Arcadia",
            "This is the big, bad wolf of West Coast IPAs. A Mammoth amount of Strata, Simcoe, Chinook, Cascade and Eureka! hops give a burst of floral, resinous pine, grapefruit and mountains of citrus fruit. This punchy IPA is supported by a strong malty backbone.",
            "",
            "https://firebasestorage.googleapis.com/v0/b/craftie-91fee.appspot.com/o/beers%2FArcadia_gluten_free.png?alt=media&token=3dffbb0f-8843-4b67-91ee-2e2e4fbae804",
            "Ale",
            "Pale Ale",
            Amount(
                6.2f,
                "%"
            ),
            Amount(
                60.0f,
                ""
            ),
            BreweryInfo(
                "Wicklow Wolf",
                "Wicklow Wolf was established in late 2014 after the meeting of two likeminded friends with a common interest in good beer. Quincey Fennelly and Simon Lynch both fell in love with craft beer while living in California for many years. Setting up a brewery together was always inevitable.",
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
            "2021-12-04T16:59:40.599613",
        )

        val elevation = Beer(
            "1",
            "Elevation PaleAle",
            "This is the big, bad wolf of West Coast IPAs. A Mammoth amount of Strata, Simcoe, Chinook, Cascade and Eureka! hops give a burst of floral, resinous pine, grapefruit and mountains of citrus fruit. This punchy IPA is supported by a strong malty backbone.",
            "",
            "https://firebasestorage.googleapis.com/v0/b/craftie-91fee.appspot.com/o/beers%2FElevation_pale_ale.png?alt=media&token=e5fbe476-dfeb-41ac-87d8-c2698099313c",
            "Ale",
            "Pale Ale",
            Amount(
                10.0f,
                "%"
            ),
            Amount(
                0.0f,
                "unit"
            ),
            BreweryInfo(
                "Wicklow Wolf",
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
            "2021-12-04T16:59:40.599613",
        )

        val mamoth = Beer(
            "1",
            "Five Lamps Lager",
            "This is the big, bad wolf of West Coast IPAs. A Mammoth amount of Strata, Simcoe, Chinook, Cascade and Eureka! hops give a burst of floral, resinous pine, grapefruit and mountains of citrus fruit. This punchy IPA is supported by a strong malty backbone.",
            "",
            "https://firebasestorage.googleapis.com/v0/b/craftie-91fee.appspot.com/o/beers%2Fmammoth.png?alt=media&token=b6c970db-3a54-41fb-b4a6-72d7cc6a3ba3",
            "Pilsner Lager",
            "Lager",
            Amount(
                10.0f,
                "%"
            ),
            Amount(
                0.0f,
                "unit"
            ),
            BreweryInfo(
                "Five Lamps",
                "",
                "",
                Location(
                    "Dublin",
                    "Leinster",
                    "",
                    LatLng(
                        10.0,
                        10.0
                    )
                )
            ),
            false,
            "2021-12-04T16:59:40.599613",
        )

        return listOf(arcadia, elevation, mamoth, mamoth)
    }

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

    fun provinces(): List<Province> {
        return listOf(
            Province(
                "Connaught",
                "https://firebasestorage.googleapis.com/v0/b/craftie-91fee.appspot.com/o/general_ui%2FConnaught.png?alt=media&token=32b6f284-d5ec-4ab4-b9e2-c71d15de999e"
            ),
            Province(
                "Leinster",
                "https://firebasestorage.googleapis.com/v0/b/craftie-91fee.appspot.com/o/general_ui%2FLeinster.png?alt=media&token=8cb29e1f-a9ad-46ee-8c8c-52b6737a22e9"
            ),
            Province(
                "Munster",
                "https://firebasestorage.googleapis.com/v0/b/craftie-91fee.appspot.com/o/general_ui%2FMunster.png?alt=media&token=ff179873-d7ac-45cf-a4de-358c70f64272"
            ),
            Province(
                "Ulster",
                "https://firebasestorage.googleapis.com/v0/b/craftie-91fee.appspot.com/o/general_ui%2FUlster.png?alt=media&token=4ff562fe-4b5f-4a6f-aeff-fc770b0cf450"
            )
        )
    }

    data class Province(
        val name: String,
        val imageUrl: String
    )

    fun mockBeerFavourites(): List<BeersDb> {
        val first = BeersDb().apply {
            id = "607b1569a936b1552000c688"
            name = "Five Lamps Stout"
            imageUrl = "https://firebasestorage.googleapis.com/v0/b/craftie-91fee.appspot.com/o/beers%2Fmammoth.png?alt=media&token=b6c970db-3a54-41fb-b4a6-72d7cc6a3ba3"
            province = "Leinster"
        }

        val second = BeersDb().apply {
            id = "607b108ccb97be5ad9ba171a"
            name = "Elevation Pale Ale"
            imageUrl = "https://firebasestorage.googleapis.com/v0/b/craftie-91fee.appspot.com/o/beers%2Fmammoth.png?alt=media&token=b6c970db-3a54-41fb-b4a6-72d7cc6a3ba3"
            province = "Leinster"
        }

        val third = BeersDb().apply {
            id = "607b15c4a936b1552000c689"
            name = "Hop-on Session IPA"
            imageUrl = "https://firebasestorage.googleapis.com/v0/b/craftie-91fee.appspot.com/o/beers%2Fmammoth.png?alt=media&token=b6c970db-3a54-41fb-b4a6-72d7cc6a3ba3"
            province = "Leinster"
        }

        return listOf(first, second, third)
    }
}