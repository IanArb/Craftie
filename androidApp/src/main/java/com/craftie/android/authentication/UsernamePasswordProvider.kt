package com.craftie.android.authentication

import com.craftie.android.BuildConfig
import javax.inject.Inject

class UsernamePasswordProvider @Inject constructor() {

    fun usernamePassword(): Pair<String, String> {
        return Pair(BuildConfig.USERNAME, BuildConfig.PASSWORD)
    }

}