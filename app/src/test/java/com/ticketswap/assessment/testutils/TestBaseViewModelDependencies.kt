package com.ticketswap.assessment.testutils

import com.ticketswap.assessment.base.BaseViewModel
import com.ticketswap.assessment.util.ResourcesProvider
import io.reactivex.Scheduler
import io.reactivex.schedulers.Schedulers

object TestBaseViewModelDependencies {
    operator fun invoke(
        ioScheduler: Scheduler = Schedulers.trampoline(),
        uiScheduler: Scheduler = Schedulers.trampoline(),
        resourceProvider: ResourcesProvider = FakeResourcesProvider(),
    ) = BaseViewModel.Dependencies(
        ioScheduler = ioScheduler,
        uiScheduler = uiScheduler,
        resourceProvider = resourceProvider
    )
}