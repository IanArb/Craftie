package com.craftie.data.repository

import com.craftie.data.model.RecentSearchDb
import io.realm.Realm
import io.realm.RealmResults
import io.realm.delete
import io.realm.query
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class RecentSearchesRepository : KoinComponent {

    private val realm: Realm by inject()

    private val mainScope: CoroutineScope = MainScope()

    fun saveRecentSearch(beerId: String, beerName: String) {
        realm.writeBlocking {
            copyToRealm(RecentSearchDb()).apply {
                id = beerId
                name = beerName
            }
        }
    }

    fun findAllRecentSearches(): Flow<RealmResults<RecentSearchDb>> {
        return realm.query<RecentSearchDb>().asFlow()
    }

    fun findAllRecentSearches(success: (List<RecentSearchDb>) -> Unit) {
        mainScope.launch {
            realm.query<RecentSearchDb>().asFlow()
                .collect {
                    success(groupByDate(it))
                }
        }
    }

    fun groupByDate(it: RealmResults<RecentSearchDb>): List<RecentSearchDb> {
        val groupByDate = it.sortedBy { search ->
            search.createdDate
        }
            .take(3)
        return groupByDate
    }

    fun removeRecentSearch(recentSearchDb: RecentSearchDb) {
        realm.writeBlocking {
            findLatest(recentSearchDb)?.delete()
        }
    }

    fun removeAllRecentSearches() {
        realm.writeBlocking {
            query<RecentSearchDb>().find().delete()
        }
    }
}