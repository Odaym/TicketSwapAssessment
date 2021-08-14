package com.ticketswap.assessment.network

import com.ticketswap.assessment.PrefStore
import com.ticketswap.assessment.responses.ArtistResponse
import com.ticketswap.assessment.responses.SearchResponse
import io.reactivex.Single

interface ApiService {
    fun getArtist(id: String): Single<ArtistResponse>

    fun search(term: String): Single<SearchResponse>
}

class ApiServiceImpl(
    override val processor: NetworkRequestProcessor,
    override val hostProvider: HostProvider,
    override val prefStore: PrefStore
) : ApiService, BaseNetworkService() {

    override fun getArtist(id: String): Single<ArtistResponse> {
        return get("/artists/$id")
            .asSingle(ArtistResponse.serializer())
    }

    override fun search(term: String): Single<SearchResponse> {
        return get("v1/search")
            .withParam("q", term)
            .withParam("type", "artist")
            .asSingle(SearchResponse.serializer())
    }
}