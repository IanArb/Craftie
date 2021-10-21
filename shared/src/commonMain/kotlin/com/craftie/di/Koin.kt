package com.craftie.di

import com.craftie.data.model.BeersDb
import com.craftie.data.model.RecentSearchDb
import com.craftie.data.remote.CraftieBeerRatingsApi
import com.craftie.data.remote.CraftieBeersApi
import com.craftie.data.remote.CraftieBreweriesAPI
import com.craftie.data.remote.CraftieProvincesApi
import com.craftie.data.repository.*
import io.ktor.client.HttpClient
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.json.serializer.KotlinxSerializer
import io.ktor.client.features.logging.DEFAULT
import io.ktor.client.features.logging.LogLevel
import io.ktor.client.features.logging.Logger
import io.ktor.client.features.logging.Logging
import io.realm.Realm
import io.realm.RealmConfiguration
import kotlinx.serialization.json.Json
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module

fun initKoin(enableNetworkLogs: Boolean = false, appDeclaration: KoinAppDeclaration = {}) {
    startKoin {
        appDeclaration()
        modules(networkModule(enableNetworkLogs = enableNetworkLogs), commonModules())
    }
}

//iOS
fun initKoin() = initKoin(enableNetworkLogs = false) {}

fun networkModule(enableNetworkLogs: Boolean) = module {
    single { createJson() }
    single { createHttpClient(get(), enableNetworkLogs)}
}

fun commonModules() = module {
    single { CraftieBreweriesAPI(get()) }
    single { CraftieBreweriesRepository() }
    single { CraftieBeersApi(get()) }
    single { CraftieBeersRepository() }
    single { CraftieProvincesApi(get()) }
    single { CraftieProvincesRepository() }
    single { RealmConfiguration(schema = setOf(BeersDb::class, RecentSearchDb::class))}
    single { Realm(get()) }
    single { FavouritesRepository() }
    single { CraftieBeerRatingsApi(get()) }
    single { CraftieBeerRatingsRepository() }
}

fun createHttpClient(json: Json, enableNetworkLogs: Boolean) = HttpClient {
    install(JsonFeature) {
        serializer = KotlinxSerializer(json)
    }
    if (enableNetworkLogs) {
        install(Logging) {
            logger = Logger.DEFAULT
            level = LogLevel.INFO
        }
    }
}
fun createJson() = Json {
    isLenient = true
    ignoreUnknownKeys = true
    encodeDefaults = true
}