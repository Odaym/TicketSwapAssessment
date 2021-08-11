package com.ticketswap.assessment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import com.spotify.sdk.android.authentication.AuthenticationClient
import com.spotify.sdk.android.authentication.AuthenticationRequest
import com.spotify.sdk.android.authentication.AuthenticationResponse
import com.ticketswap.assessment.base.BaseActivity
import com.ticketswap.assessment.databinding.ActivityLoginBinding
import com.ticketswap.assessment.util.OpenSearchScreen
import com.ticketswap.assessment.util.ViewModelCommand
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

class LoginActivity : BaseActivity<LoginViewModel>() {

    override val viewModel by viewModel<LoginViewModel>()
    private lateinit var binding: ActivityLoginBinding

    private lateinit var prefStore: PrefStore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonLogin.setOnClickListener {
            viewModel.loginButtonClicked()
        }

        viewModel.isConnected.observe(this, {
            loginWithSpotify()
        })

        prefStore = PrefStore(this)
    }

    override fun handleViewModelCommand(command: ViewModelCommand) = when (command) {
        is OpenSearchScreen -> {
            SearchActivity.start(this)
            true
        }
        else -> super.handleViewModelCommand(command)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val response = AuthenticationClient.getResponse(resultCode, data)

        if (response.type == AuthenticationResponse.Type.ERROR) {
            Toast.makeText(
                this,
                "Error: ${response.error}",
                Toast.LENGTH_LONG
            )
                .show()
        } else {
            Timber.d("Spotify AccessToken is ${response.accessToken} ")
            viewModel.authTokenReceived(response.accessToken)
        }
    }

    private fun loginWithSpotify() {
        AuthenticationClient.openLoginActivity(
            this, SPOTIFY_LOGIN_REQUEST,
            AuthenticationRequest.Builder(
                SPOTIFY_CLIENT_ID,
                AuthenticationResponse.Type.TOKEN, Uri.Builder()
                    .scheme(getString(R.string.com_spotify_sdk_redirect_scheme))
                    .authority(getString(R.string.com_spotify_sdk_redirect_host))
                    .build().toString()
            )
                .setShowDialog(true)
                .setScopes(arrayOf("user-read-email"))
                .setCampaign("your-campaign-token")
                .build()
        )
    }

    companion object {
        const val SPOTIFY_LOGIN_REQUEST = 101
        const val SPOTIFY_CLIENT_ID = "84ea753e599142b8bace9b63d153227b"
    }
}
