package com.craftie.data.remote

import com.craftie.data.model.Beer
import com.craftie.data.model.Pagination
import com.craftie.utils.Endpoints
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import org.koin.core.component.KoinComponent

class CraftieBeersApi(private val httpClient: HttpClient) : KoinComponent {

    companion object {
        private const val PAGE_KEY = "page"
        private const val PROVINCE_KEY = "province"
        private const val KEYWORD_KEY = "keyword"
    }

    suspend fun beersPageable(page: Int = 1) = httpClient.get<Pagination<Beer>>(Endpoints.BEERS_PAGINATION_ENDPOINT) {
        parameter(PAGE_KEY, page)
    }

    suspend fun findBeer(id: String) = httpClient.get<Beer>(Endpoints.BEERS_ENDPOINT.plus("/$id"))

    suspend fun beersByProvincePageable(page: Int = 1, province: String): Pagination<Beer> {
        return httpClient.get(Endpoints.BEERS_PAGINATION_ENDPOINT) {
            parameter(PAGE_KEY, page)
            parameter(PROVINCE_KEY, province)
        }
    }

    suspend fun featuredBeer(): Beer = httpClient.get(Endpoints.BEERS_FEATURED_ENDPOINT)

    suspend fun findBeersByKeyword(keyword: String): List<Beer> {
        return httpClient.get(Endpoints.BEERS_ENDPOINT) {
            parameter(KEYWORD_KEY, keyword)
        }
    }
}