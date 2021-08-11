package com.ticketswap.assessment.network

import com.ticketswap.assessment.PrefStore
import io.reactivex.Single
import kotlinx.serialization.KSerializer
import okhttp3.MediaType.Companion.toMediaTypeOrNull

abstract class BaseNetworkService {
    abstract val processor: NetworkRequestProcessor
    abstract val hostProvider: HostProvider
    abstract val prefStore: PrefStore

    companion object {
        private val JSON_MIME = "application/json".toMediaTypeOrNull()
    }

    fun get(path: String): NetworkRequestBuilder {
        return NetworkRequestBuilder(hostProvider.host, hostProvider.scheme)
            .withMethod(HttpMethod.GET)
            .withHeader("Authorization", prefStore.getAuthToken())
            .withPath(path)
    }

    protected fun <T : Any> NetworkRequestBuilder.asSingle(serializer: KSerializer<T>): Single<T> {
        return processor.process(this, serializer)
    }
}