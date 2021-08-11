package com.ticketswap.assessment.spotify

import kotlinx.serialization.Serializable

@Serializable
class SearchResponse(
    val artists: Artists
)

@Serializable
class Artists(
    val href: String,
    val items: List<ArtistItems>,
    val limit: Int,
    val next: String,
    val offset: Int,
    val previous: String?,
    val total: Int
)

@Serializable
class ArtistItems(
    val external_urls: ExternalUrls,
    val genres: List<String>,
    val href: String,
    val id: String,
    val images: List<Image>,
    val name: String,
    val popularity: Int,
    val type: String,
    val uri: String
)