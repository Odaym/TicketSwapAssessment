package com.ticketswap.assessment.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ticketswap.assessment.PrefStore
import com.ticketswap.assessment.base.BaseViewModel
import com.ticketswap.assessment.util.CloseScreen
import com.ticketswap.assessment.util.NetworkAvailabilityChecker
import com.ticketswap.assessment.util.OpenSearchScreen
import org.koin.core.component.KoinComponent

class LoginViewModel(
    private val prefStore: PrefStore,
    private val networkChecker: NetworkAvailabilityChecker
) : BaseViewModel(), KoinComponent {

    private val _isConnected = MutableLiveData(false)
    val isConnected = _isConnected as LiveData<Boolean>

    fun loginButtonClicked(){
       if (networkChecker.isConnectedToInternet){
          _isConnected.value = true
       }else{
           // TODO show error dialog
       }
    }

    fun authTokenReceived(accessToken: String?) {
        if (accessToken != null) {
            prefStore.setAuthToken(accessToken)
            emitCommand(OpenSearchScreen)
            emitCommand(CloseScreen)
        } else {
            // TODO show error dialog
        }
    }
}

