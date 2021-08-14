package com.ticketswap.assessment.testutils

import com.ticketswap.assessment.util.ResourcesProvider

class FakeResourcesProvider : ResourcesProvider {
    override fun getString(stringId: Int) = "$stringId"
}