package com.craftie.data.model

import io.realm.RealmObject

class BeersDb : RealmObject {
    var id: String = ""
    var name: String = ""
    var imageUrl: String = ""
    var province: String = ""
}