package com.craftie.data.repository

import com.craftie.data.model.Beer
import com.craftie.data.model.BeersDb
import com.craftie.data.model.FavouriteBeerUiData
import io.realm.kotlin.Realm
import io.realm.kotlin.ext.query
import io.realm.kotlin.notifications.InitialResults
import io.realm.kotlin.notifications.ResultsChange
import io.realm.kotlin.notifications.UpdatedResults
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class FavouritesRepository : KoinComponent {

    private val realm: Realm by inject()

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