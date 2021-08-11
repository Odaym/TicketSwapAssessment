package com.ticketswap.assessment.network

interface HostProvider {
    val scheme: String
    val host: String
}