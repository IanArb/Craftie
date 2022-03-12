package com.craftie.data.repository

import com.craftie.data.model.Beer
import com.craftie.data.model.BeersDb
import com.craftie.data.model.RecentSearchDb
import io.realm.*
import io.realm.notifications.InitialResults
import io.realm.notifications.RealmChange
import io.realm.notifications.ResultsChange
import io.realm.notifications.UpdatedResults
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class FavouritesRepository : KoinComponent {

    private val realm: Realm by inject()

    private val mainScope: CoroutineScope = MainScope()

    fun saveBeer(beer: Beer) {
        realm.writeBlocking {
            copyToRealm(BeersDb().apply {
                id = beer.id
                name = beer.name
                imageUrl = beer.imageUrl
                province = beer.breweryInfo.location.province
            })
        }
    }

    fun findAllBeers(): Flow<ResultsChange<BeersDb>> {
        return realm.query<BeersDb>().asFlow()
    }

    //iOS
    fun findAllBeers(success: (List<BeersDb>) -> Unit) {
        mainScope.launch {
            realm.query<BeersDb>().asFlow()
                .collect {
                    when(it) {
                        is InitialResults -> {
                            success(it.list)
                        }
                        is UpdatedResults -> {
                            success(it.list)
                        }
                    }
                }
        }
    }

    fun removeBeer(beer: BeersDb) {
        realm.writeBlocking {
            delete(beer)
        }
    }

    fun removeAll() {
        realm.writeBlocking {
            val results = query<BeersDb>().find()
            delete(results)
        }
    }

}