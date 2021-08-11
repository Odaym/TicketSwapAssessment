package com.ticketswap.assessment.service

import com.ticketswap.assessment.PrefStore
import com.ticketswap.assessment.network.BaseNetworkService
import com.ticketswap.assessment.network.HostProvider
import com.ticketswap.assessment.network.NetworkRequestProcessor
import com.ticketswap.assessment.spotify.ArtistResponse
import com.ticketswap.assessment.spotify.SearchResponse
import io.reactivex.Single

class ApiService(
    override val processor: NetworkRequestProcessor,
    override val hostProvider: HostProvider,
    override val prefStore: PrefStore
) : BaseNetworkService() {

    fun getArtist(id: String): Single<ArtistResponse> {
        return get("v1/artists/$id")
            .asSingle(ArtistResponse.serializer())
    }

    fun search(term: String): Single<SearchResponse> {
        return get("v1/search")
            .withParam("q", term)
            .withParam("type", "artist")
            .asSingle(SearchResponse.serializer())
    }
}