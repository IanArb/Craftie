package com.craftie.android.authentication

import com.craftie.android.presentation.home.LoginUiState
import com.craftie.android.util.CoroutinesDispatcherProvider
import com.craftie.android.util.Outcome
import com.craftie.android.util.makeApiCall
import com.craftie.data.repository.CraftieAuthenticationRepository
import com.craftie.data.settings.SettingsRepository
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class TokenUseCase @Inject constructor(
    private val authenticationRepository: CraftieAuthenticationRepository,
    private val settingsRepository: SettingsRepository,
    private val usernamePasswordProvider: UsernamePasswordProvider,
    private val dispatcher: CoroutinesDispatcherProvider
) {

    suspend fun login() = flow {
        val result = makeApiCall("Failed to login") {
            val usernamePassword = usernamePasswordProvider.usernamePassword()
            val username = usernamePassword.first
            val password = usernamePassword.second
            val login = authenticationRepository.login(username, password)
            Outcome.Success(login.token)
        }

        if (result is Outcome.Success) {
            settingsRepository.clear()
            settingsRepository.saveToken(result.value)
            emit(LoginUiState.Success)
            return@flow
        }

        if (result is Outcome.Error) {
            emit(LoginUiState.Error)
            return@flow
        }

    }.flowOn(dispatcher.io)

}