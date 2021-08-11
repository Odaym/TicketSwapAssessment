package com.ticketswap.assessment.util

interface ViewModelCommand

object CloseScreen : ViewModelCommand

object OpenSearchScreen : ViewModelCommand

data class LoadArtists(val artists: ArrayList<String>): ViewModelCommand

data class ShowToast(val message: String) : ViewModelCommand

