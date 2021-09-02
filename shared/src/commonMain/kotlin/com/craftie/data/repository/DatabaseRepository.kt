package com.craftie.data.repository

import com.craftie.data.model.Beer
import com.craftie.data.model.BeersDb
import io.realm.MutableRealm
import io.realm.Realm
import io.realm.RealmResults
import io.realm.delete
import kotlinx.coroutines.flow.Flow
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class DatabaseRepository : KoinComponent {

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

    fun findAllBeers(): Flow<RealmResults<BeersDb>> {
        return realm.objects(BeersDb::class).observe()
    }

    fun removeBeer(beer: BeersDb) {
        realm.writeBlocking {
            findLatest(beer)?.delete()
        }
    }

    fun removeAll() {
        realm.writeBlocking {
            objects(BeersDb::class).delete()
        }
    }
}