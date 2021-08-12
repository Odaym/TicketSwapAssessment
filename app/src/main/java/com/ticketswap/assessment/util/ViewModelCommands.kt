package com.ticketswap.assessment.util

import com.ticketswap.assessment.spotify.ArtistItem

interface ViewModelCommand

object CloseScreen : ViewModelCommand

object OpenSearchScreen : ViewModelCommand

data class OpenArtistDetailScreen(val artist: ArtistItem) : ViewModelCommand

data class ShowToast(val message: String) : ViewModelCommand

