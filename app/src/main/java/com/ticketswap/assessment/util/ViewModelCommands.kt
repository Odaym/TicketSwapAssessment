package com.ticketswap.assessment.util

import com.ticketswap.assessment.responses.ArtistItem

interface ViewModelCommand

object CloseScreen : ViewModelCommand

object OpenSearchScreen : ViewModelCommand

object LoginWithSpotify : ViewModelCommand

data class OpenArtistDetailScreen(val artist: ArtistItem) : ViewModelCommand

data class ShowErrorDialog(val message: String) : ViewModelCommand

data class ShowToast(val message: String) : ViewModelCommand

