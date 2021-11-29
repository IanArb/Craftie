package com.craftie.android.presentation.viewAllBreweries

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.craftie.data.model.Result
import com.craftie.data.repository.CraftieBreweriesRepository
import java.lang.Exception
import javax.inject.Inject

class BreweriesPagingSource @Inject constructor(
    private val breweriesRepository: CraftieBreweriesRepository
): PagingSource<Int, Result>() {

    override fun getRefreshKey(state: PagingState<Int, Result>): Int? {
        return null
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Result> {
        return try {
            val page = params.key ?: 1

            val response = breweriesRepository.breweriesPageable(page)
            val breweries = response.results

            val prevKey = if (page > 0) page - 1 else null
            val nextKey = response.info.next

            LoadResult.Page(
                data = breweries,
                prevKey = prevKey,
                nextKey = nextKey
            )
        } catch (exception: Exception) {
            LoadResult.Error(exception)
        }
    }
}