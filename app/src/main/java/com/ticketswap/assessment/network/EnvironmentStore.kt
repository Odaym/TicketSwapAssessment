package com.ticketswap.assessment.network

class EnvironmentStore : HostProvider {
    override val scheme: String = "https"
    override val host: String = "api.spotify.com"
}