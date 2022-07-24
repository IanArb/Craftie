package com.craftie.android.util

import com.craftie.android.BuildConfig
import javax.inject.Inject

class BaseUrlResolver @Inject constructor() {

    companion object {
        private const val STAGING_URL = "https://craftie-api.herokuapp.com/api/v1"
        private const val PRODUCTION_URL = "https://craftie.app:8080/api/v1"
    }

    fun resolveBaseUrl(): String {
        return if (BuildConfig.ENABLE_PRODUCTION) {
            PRODUCTION_URL
        } else {
            STAGING_URL
        }
    }
}