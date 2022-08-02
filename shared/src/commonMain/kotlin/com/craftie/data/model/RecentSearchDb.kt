package com.craftie.data.model

import io.realm.kotlin.types.RealmObject
import kotlinx.datetime.*

class RecentSearchDb : RealmObject {
    var id: String = ""
    var name: String = ""
    var createdDate = Clock.System.now().epochSeconds
}