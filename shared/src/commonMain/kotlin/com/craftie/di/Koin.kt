package com.craftie.di

import com.craftie.data.model.BeersDb
import com.craftie.data.model.RecentSearchDb
import com.craftie.data.remote.*
import com.craftie.data.repository.*
import com.craftie.data.settings.SettingsRepository
import com.craftie.data.useCase.CraftieFilterUseCase
import com.russhwolf.settings.Settings
import io.ktor.client.HttpClient
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.DEFAULT
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import io.realm.kotlin.Configuration
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration
import kotlinx.coroutines.FlowPreview
import kotlinx.serialization.json.Json
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module

fun initCraftie(enableNetworkLogs: Boolean = false, baseUrl: String, appDeclaration: KoinAppDeclaration = {}) {
    startKoin {
        appDeclaration()
        modules(networkModule(enableNetworkLogs = enableNetworkLogs), commonModules(baseUrl))
    }
}

//iOS
fun initCraftie(baseUrl: String) {
    initCraftie(enableNetworkLogs = false, baseUrl = baseUrl) {}
}

fun networkModule(enableNetworkLogs: Boolean) = module {
    single { createJson() }
    single { createHttpClient(get(), enableNetworkLogs) }
}

@OptIn(FlowPreview::class)
fun commonModules(baseUrl: String) = module {
    single { CraftieBreweriesAPI(get(), get()) }
    single { CraftieBreweriesRepository() }
    single { CraftieBeersApi(get(), get()) }
    single { CraftieBeersRepository() }
    single { CraftieProvincesApi(get(), get()) }
    single { CraftieProvincesRepository() }
    single<Configuration> { RealmConfiguration.create(schema = setOf(BeersDb::class, RecentSearchDb::class))}
    single { Realm.open(get()) }
    single { FavouritesRepository() }
    single { CraftieBeerRatingsApi(get(), get()) }
    single { CraftieBeerRatingsRepository() }
    single { CraftieFilterUseCase() }
    single { CraftieAuthenticationApi(get(), get()) }
    single { CraftieAuthenticationRepository() }
    single { Settings() }
    single { SettingsRepository(baseUrl) }
    single { CraftieProvincesCountApi(get(), get()) }
    single { ProvincesCountRepository() }
}

fun createHttpClient(json: Json, enableNetworkLogs: Boolean) = HttpClient {
    val timeout = 6000L

    install(ContentNegotiation) {
        json(json)
    }

    install(HttpTimeout) {
        requestTimeoutMillis = timeout
        connectTimeoutMillis = timeout
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