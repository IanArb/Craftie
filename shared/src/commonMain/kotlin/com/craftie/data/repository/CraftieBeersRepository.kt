package com.craftie.data.repository

import com.craftie.data.model.Beer
import com.craftie.data.model.Pagination
import com.craftie.data.remote.CraftieBeersApi
import com.craftie.data.util.CommonFlow
import com.craftie.data.util.asCommonFlow
import com.kuuurt.paging.multiplatform.Pager
import com.kuuurt.paging.multiplatform.PagingConfig
import com.kuuurt.paging.multiplatform.PagingData
import com.kuuurt.paging.multiplatform.PagingResult
import com.kuuurt.paging.multiplatform.helpers.cachedIn
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.MainScope
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

@FlowPreview
class CraftieBeersRepository : KoinComponent {

    private val scope = MainScope()

    private val craftieBeersApi: CraftieBeersApi by inject()

    private val pagingConfig = PagingConfig(pageSize = 10, enablePlaceholders = false)

    val beersPager = Pager(clientScope = scope, config = pagingConfig, initialKey = 1,
        getItems = { currentKey, _ ->
            try {
                val beers = craftieBeersApi.beersPageable(currentKey)
                val items = beers.results
                pagingResult(items, currentKey, beers)
            } catch(exception: Exception) {
                pagingResultError(currentKey)
            }
        }
    )

    fun beersByProvincePager(province: String) = Pager(clientScope = scope, config = pagingConfig, initialKey = 1,
        getItems = { currentKey, _ ->
            try {
                val beers = craftieBeersApi.beersByProvincePageable(currentKey, province)
                val items = beers.results
                pagingResult(items, currentKey, beers)
            } catch (exception: Exception) {
                pagingResultError(currentKey)
            }

        }
    )

    private fun pagingResultError(currentKey: Int) = PagingResult(
        items = emptyList<Beer>(),
        currentKey = currentKey,
        prevKey = { if (currentKey > 0) currentKey - 1 else null },
        nextKey = { null }
    )

    private fun pagingResult(
        items: List<Beer>,
        currentKey: Int,
        beers: Pagination<Beer>
    ) = PagingResult(
        items = items,
        currentKey = currentKey,
        prevKey = { if (currentKey > 0) currentKey - 1 else null },
        nextKey = { beers.info.next }
    )

    val beersPagingData: CommonFlow<PagingData<Beer>>
        get() = beersPager.pagingData
            .cachedIn(scope)
            .asCommonFlow()

    fun beersProvincesData(province: String): CommonFlow<PagingData<Beer>> {
        return beersByProvincePager(province).pagingData
            .cachedIn(scope)
            .asCommonFlow()
    }

    suspend fun beers(): List<Beer> = craftieBeersApi.beersPageable().results

    suspend fun findBeer(id: String) = craftieBeersApi.findBeer(id)

    suspend fun featuredBeer() = craftieBeersApi.featuredBeer()

    suspend fun findBeersByKeyword(keyword: String) = craftieBeersApi.findBeersByKeyword(keyword)
}