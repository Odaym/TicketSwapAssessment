package com.ticketswap.assessment.network

import okhttp3.Response

interface ErrorBodyConverter {
    fun convert(response: Response): Throwable?
}