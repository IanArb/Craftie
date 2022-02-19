package com.craftie.data.repository

import com.craftie.data.model.Result
import com.craftie.data.remote.CraftieBreweriesAPI
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

@OptIn(FlowPreview::class)
class CraftieBreweriesRepository: KoinComponent {

    private val scope = MainScope()

    private val pagingConfig = PagingConfig(pageSize = 10, enablePlaceholders = false)

    private val craftieBreweriesApi: CraftieBreweriesAPI by inject()

    suspend fun breweries() = craftieBreweriesApi.breweries()

    val breweriesPager = Pager(clientScope = scope, config = pagingConfig, initialKey = 1,
        getItems = { currentKey, _ ->
            val breweries = craftieBreweriesApi.breweriesPageable(currentKey)
            val items = breweries.results
            PagingResult(
                items = items,
                currentKey = currentKey,
                prevKey = { if (currentKey > 0) currentKey - 1 else null },
                nextKey = { breweries.info.next }
            )
        }
    )

    val breweriesPagingData: CommonFlow<PagingData<Result>>
    get() = breweriesPager.pagingData
        .cachedIn(scope)
        .asCommonFlow()

    suspend fun breweriesPageable(page: Int) = craftieBreweriesApi.breweriesPageable(page)

    suspend fun findBrewery(id: String) = craftieBreweriesApi.findBrewery(id)
}