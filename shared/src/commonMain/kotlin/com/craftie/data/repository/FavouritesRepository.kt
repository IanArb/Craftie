package com.craftie.data.repository

import com.craftie.data.model.Beer
import com.craftie.data.model.BeersDb
import com.craftie.data.model.FavouriteBeerUiData
import io.realm.*
import io.realm.notifications.InitialResults
import io.realm.notifications.ResultsChange
import io.realm.notifications.UpdatedResults
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class FavouritesRepository : KoinComponent {

    private val realm: Realm by inject()

    private val mainScope: CoroutineScope = MainScope()

    suspend fun saveBeer(beer: Beer) {
        realm.write {
            copyToRealm(BeersDb().apply {
                id = beer.id
                name = beer.name
                imageUrl = beer.imageUrl
                province = beer.breweryInfo.location.province
            })
        }
    }

    fun findAllBeers(): Flow<List<FavouriteBeerUiData>> {
        return realm.query<BeersDb>().asFlow()
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

    private fun transformToModel(it: ResultsChange<BeersDb>) =
        it.list.map { beer ->
            FavouriteBeerUiData(
                id = beer.id,
                name = beer.name,
                imageUrl = beer.imageUrl,
                province = beer.province
            )
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

    suspend fun removeBeer(id: String) {
        realm.write {
            val beer = query<BeersDb>("id = $0", id)
            delete(beer)
        }
    }

    suspend fun removeAll() {
        realm.write {
            val results = query<BeersDb>().find()
            delete(results)
        }
    }

}