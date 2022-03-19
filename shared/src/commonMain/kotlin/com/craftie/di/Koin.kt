package com.craftie.di

import com.craftie.data.model.BeersDb
import com.craftie.data.model.RecentSearchDb
import com.craftie.data.remote.CraftieBeerRatingsApi
import com.craftie.data.remote.CraftieBeersApi
import com.craftie.data.remote.CraftieBreweriesAPI
import com.craftie.data.remote.CraftieProvincesApi
import com.craftie.data.repository.CraftieBeerRatingsRepository
import com.craftie.data.repository.CraftieProvincesRepository
import com.craftie.data.repository.CraftieBreweriesRepository
import com.craftie.data.repository.CraftieBeersRepository
import com.craftie.data.repository.FavouritesRepository
import io.ktor.client.HttpClient
import io.ktor.client.plugins.ContentNegotiation
import io.ktor.client.plugins.logging.DEFAULT
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import io.realm.Configuration
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
    single<Configuration> { RealmConfiguration.with(schema = setOf(BeersDb::class, RecentSearchDb::class))}
    single { Realm.open(get()) }
    single { FavouritesRepository() }
    single { CraftieBeerRatingsApi(get()) }
    single { CraftieBeerRatingsRepository() }
}

fun createHttpClient(json: Json, enableNetworkLogs: Boolean) = HttpClient {
    install(ContentNegotiation) {
        json(json)
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
    useAlternativeNames = false
}