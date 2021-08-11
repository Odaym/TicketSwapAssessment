package com.ticketswap.assessment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ticketswap.assessment.base.BaseViewModel
import com.ticketswap.assessment.service.ApiService
import com.ticketswap.assessment.util.LoadArtists
import timber.log.Timber

class SearchViewModel(
    private val apiService: ApiService
) : BaseViewModel() {

    private val _isRefreshing = MutableLiveData(false)
    val isRefreshing = _isRefreshing as LiveData<Boolean>

    fun searchButtonClicked(searchInput: String) {
        apiService.search(searchInput)
            .doFinally {
                _isRefreshing.postValue(false)
            }
            .viewModelSubscription({ searchResponse ->
                val artists = arrayListOf<String>()
                artists.addAll(searchResponse.artists.items.map { "Artist: ${it.name}" })

                emitCommand(LoadArtists(artists))
            }, {
                Timber.e("Error getting artists ${it.localizedMessage}")
            })
    }
}