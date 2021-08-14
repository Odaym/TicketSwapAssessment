package com.ticketswap.assessment.ui.login

import com.spotify.sdk.android.authentication.AuthenticationResponse
import com.ticketswap.assessment.PrefStore
import com.ticketswap.assessment.R
import com.ticketswap.assessment.base.BaseViewModel
import com.ticketswap.assessment.util.*
import org.koin.core.component.KoinComponent

class LoginViewModel(
    dependencies: Dependencies,
    private val prefStore: PrefStore,
    private val networkChecker: NetworkAvailabilityChecker
) : BaseViewModel(dependencies), KoinComponent {

    fun loginButtonClicked() {
        if (networkChecker.isConnectedToInternet) {
            emitCommand(LoginWithSpotify)
        } else {
            emitCommand(ShowErrorDialog(resourcesProvider.getString(R.string.offline_error)))
        }
    }

    fun spotifyResponseReceived(response: AuthenticationResponse) {
        if (response.type == AuthenticationResponse.Type.ERROR) {
            emitCommand(ShowErrorDialog(resourcesProvider.getString(R.string.spotify_login_failed)))
        } else {
            authTokenReceived(response.accessToken)
        }
    }

    private fun authTokenReceived(accessToken: String?) {
        if (accessToken != null) {
            prefStore.setAuthToken(accessToken)
            emitCommand(OpenSearchScreen)
            emitCommand(CloseScreen)
        } else {
            emitCommand(ShowErrorDialog(resourcesProvider.getString(R.string.spotify_auth_failed)))
        }
    }
}

