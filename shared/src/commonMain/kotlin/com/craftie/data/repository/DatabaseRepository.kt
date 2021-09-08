package com.craftie.data.repository

import com.craftie.data.model.Beer
import com.craftie.data.model.BeersDb
import io.realm.Realm
import io.realm.RealmResults
import io.realm.delete
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class DatabaseRepository : KoinComponent {

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

    fun findAllBeers(): Flow<RealmResults<BeersDb>> {
        return realm.objects(BeersDb::class).observe()
    }

    //iOS
    fun findAllBeers(success: (List<BeersDb>) -> Unit) {
        mainScope.launch {
            realm.objects(BeersDb::class).observe()
                .collect {
                    success(it)
                }
        }
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