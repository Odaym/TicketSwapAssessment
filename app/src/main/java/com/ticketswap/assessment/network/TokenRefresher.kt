package com.ticketswap.assessment.network

import io.reactivex.Completable
import io.reactivex.Single

class TokenRefresher {

    fun await(): Completable {
        return Completable.complete()
    }

    fun tryRefresh(throwable: Throwable): Single<Unit> {
        return Single.error(throwable)
    }
}