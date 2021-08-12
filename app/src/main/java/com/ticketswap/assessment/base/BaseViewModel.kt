package com.ticketswap.assessment.base

import androidx.lifecycle.ViewModel
import com.ticketswap.assessment.util.CloseScreen
import com.ticketswap.assessment.util.ViewModelCommand
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import io.reactivex.subjects.Subject

abstract class BaseViewModel : ViewModel() {
    private val ioScheduler = Schedulers.io()
    private val uiScheduler = AndroidSchedulers.mainThread()

    private val subscriptions = CompositeDisposable()

    private val commandsSubject: Subject<ViewModelCommand> = PublishSubject.create()
    val commands: Observable<ViewModelCommand> = commandsSubject.hide()

    protected fun emitCommand(command: ViewModelCommand) {
        commandsSubject.onNext(command)
    }

    private fun subscription(block: () -> Disposable) {
        subscriptions.add(block())
    }

    override fun onCleared() {
        super.onCleared()
        subscriptions.clear()
    }

    protected fun <T> Single<T>.viewModelSubscription(
        onSuccess: (T) -> Unit,
        onError: (Throwable) -> Unit
    ) = subscription {
        subscribeOn(ioScheduler)
            .observeOn(uiScheduler)
            .subscribe(onSuccess, onError)
    }

    fun onBackButtonClicked() = emitCommand(CloseScreen)
}