package com.ticketswap.assessment.spotify

import kotlinx.serialization.Serializable

@Serializable
class ArtistResponse(
    val external_urls: ExternalUrls,
    val followers: Followers,
    val genres: List<String>,
    val href: String,
    val id: String,
    val images: List<Image>,
    val name: String,
    val popularity: Int,
    val type: String,
    val uri: String
)

@Serializable
class Image(
    val height: Int,
    val url: String,
    val width: Int
)

@Serializable
class Followers(
    val href: String? = null,
    val total: Int
)

@Serializable
class ExternalUrls(
    val spotify: String
)