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
    }

    suspend fun beers(): List<Beer> = httpClient.get(Endpoints.BEERS_ENDPOINT)

    suspend fun beersPageable(page: Int) = httpClient.get<Pagination<Beer>>(Endpoints.BEERS_PAGINATION_ENDPOINT) {
        parameter(PAGE_KEY, page)
    }

    suspend fun findBeer(id: String) = httpClient.get<Beer>(Endpoints.BEERS_ENDPOINT.plus("/$id"))

    suspend fun beersByType(type: String): List<Beer> {
        return httpClient.get(Endpoints.BEERS_ENDPOINT) {
            parameter("type", type)
        }
    }

    suspend fun beersByTypePageable(page: Int, type: String): Pagination<Beer> {
        return httpClient.get(Endpoints.BEERS_PAGINATION_ENDPOINT) {
            parameter(PAGE_KEY, page)
            parameter("type", type)
        }
    }

    suspend fun beersByProvince(province: String): List<Beer> {
        return httpClient.get(Endpoints.BEERS_ENDPOINT) {
            parameter("province", province)
        }
    }

    suspend fun beersByProvincePageable(page: Int, province: String): Pagination<Beer> {
        return httpClient.get(Endpoints.BEERS_PAGINATION_ENDPOINT) {
            parameter(PAGE_KEY, page)
            parameter("province", province)
        }
    }

    suspend fun beersByBrewery(brewery: String): List<Beer> {
        return httpClient.get(Endpoints.BEERS_ENDPOINT) {
            parameter("brewery", brewery)
        }
    }

    suspend fun beersByBreweryPageable(page: Int, brewery: String): Pagination<Beer> {
        return httpClient.get(Endpoints.BEERS_PAGINATION_ENDPOINT) {
            parameter(PAGE_KEY, page)
            parameter("brewery", brewery)
        }
    }

    suspend fun featuredBeer(): Beer = httpClient.get(Endpoints.BEERS_FEATURED_ENDPOINT)

    suspend fun findBeersByKeyword(keyword: String): List<Beer> {
        return httpClient.get(Endpoints.BEERS_ENDPOINT) {
            parameter("keyword", keyword)
        }
    }
}