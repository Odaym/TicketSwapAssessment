package com.ticketswap.assessment.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ticketswap.assessment.R
import com.ticketswap.assessment.base.BaseViewModel
import com.ticketswap.assessment.network.ApiService
import com.ticketswap.assessment.responses.ArtistItem
import com.ticketswap.assessment.util.NetworkAvailabilityChecker
import com.ticketswap.assessment.util.OpenArtistDetailScreen
import com.ticketswap.assessment.util.ShowErrorDialog

class SearchViewModel(
    dependencies: Dependencies,
    private val apiService: ApiService,
    private val networkChecker: NetworkAvailabilityChecker
) : BaseViewModel(dependencies) {

    private val _isRefreshing = MutableLiveData(false)
    val isRefreshing = _isRefreshing as LiveData<Boolean>

    private val _listItems = MutableLiveData<List<ArtistItem>>()
    val listItems = _listItems as LiveData<List<ArtistItem>>

    private val _emptyViewVisible = MutableLiveData(true)
    val emptyViewVisible = _emptyViewVisible as LiveData<Boolean>

    private val _noResultsVisible = MutableLiveData(false)
    val noResultsVisible = _noResultsVisible as LiveData<Boolean>

    private val _isProgressVisible = MutableLiveData(false)
    val isProgressVisible = _isProgressVisible as LiveData<Boolean>

    var searchQuery = ""

    fun onSearchQueryChanged(query: String) {
        searchQuery = query

        if (searchQuery.isNotEmpty()) {
            _emptyViewVisible.value = false

            search()
        } else {
            _emptyViewVisible.value = true
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
                    _noResultsVisible.postValue(searchResponse.artists.items.isEmpty())
                    _listItems.postValue(searchResponse.artists.items)
                }, {
                    _emptyViewVisible.value = true
                    _listItems.value = emptyList()
                })
        } else {
            emitCommand(ShowErrorDialog(resourcesProvider.getString(R.string.spotify_search_failed)))
        }
    }

    fun onListItemClicked(artist: ArtistItem) {
        emitCommand(OpenArtistDetailScreen(artist))
    }
}