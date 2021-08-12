package com.ticketswap.assessment.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ticketswap.assessment.base.BaseViewModel
import com.ticketswap.assessment.service.ApiService
import com.ticketswap.assessment.spotify.ArtistItem
import com.ticketswap.assessment.util.NetworkAvailabilityChecker
import com.ticketswap.assessment.util.OpenArtistDetailScreen

class SearchViewModel(
    private val apiService: ApiService,
    private val networkChecker: NetworkAvailabilityChecker
) : BaseViewModel() {

    private val _isRefreshing = MutableLiveData(false)
    val isRefreshing = _isRefreshing as LiveData<Boolean>

    private val _listItems = MutableLiveData<List<ArtistItem>>()
    val listItems = _listItems as LiveData<List<ArtistItem>>

    private val _isHintVisible = MutableLiveData(true)
    val isHintVisible = _isHintVisible as LiveData<Boolean>

    private val _isProgressVisible = MutableLiveData(false)
    val isProgressVisible = _isProgressVisible as LiveData<Boolean>

    private var searchQuery = ""

    fun onSearchQueryChanged(query: String) {
        searchQuery = query

        if (searchQuery.isNotEmpty()) {
            _isHintVisible.value = false

            search()
        } else {
            _isHintVisible.value = true
            _listItems.value = emptyList()
        }
    }

    fun search() {
        if (networkChecker.isConnectedToInternet) {
            _isProgressVisible.value = true

            apiService.search(searchQuery)
                .doFinally {
                    _isRefreshing.postValue(false)
                    _isProgressVisible.postValue(false)
                }
                .viewModelSubscription({ searchResponse ->
                    _listItems.postValue(searchResponse.artists.items)
                }, {
                    _isHintVisible.value = true
                    _listItems.value = emptyList()

                })
        } else {
            // TODO show error dialog
        }
    }

    fun onListItemClicked(artist: ArtistItem) {
        emitCommand(OpenArtistDetailScreen(artist))
    }
}