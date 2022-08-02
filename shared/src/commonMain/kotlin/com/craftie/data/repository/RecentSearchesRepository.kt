package com.craftie.data.repository

import com.craftie.data.model.RecentSearchDb
import com.craftie.data.model.RecentSearchUiData
import io.realm.kotlin.Realm
import io.realm.kotlin.ext.query
import io.realm.kotlin.notifications.InitialResults
import io.realm.kotlin.notifications.ResultsChange
import io.realm.kotlin.notifications.UpdatedResults
import kotlinx.coroutines.flow.*
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class RecentSearchesRepository : KoinComponent {

    private val realm: Realm by inject()

    suspend fun saveRecentSearch(beerId: String, beerName: String) {
        realm.write {
            copyToRealm(RecentSearchDb()).apply {
                id = beerId
                name = beerName
            }
        }
    }

    fun findAllRecentSearches(): Flow<List<RecentSearchUiData>> {
        return realm.query<RecentSearchDb>().asFlow()
            .map {
                when (it) {
                    is InitialResults -> {
                        transformToModel(it)
                    }
                    is UpdatedResults -> {
                        transformToModel(it)
                    }
                }
            }
    }

    private fun transformToModel(it: ResultsChange<RecentSearchDb>) =
        it.list
            .map { search ->
                RecentSearchUiData(
                    id = search.id,
                    name = search.name,
                    createdDate = search.createdDate
                )
            }
            .sortedBy {
                it.createdDate
            }
            .take(3)

    suspend fun removeRecentSearch(id: String) {
        realm.write {
            val recentSearch = query<RecentSearchDb>("id = $0", id)
            delete(recentSearch)
        }
    }

    suspend fun removeAllRecentSearches() {
        realm.write {
            val results = query<RecentSearchDb>().find()
            delete(results)
        }
    }
}