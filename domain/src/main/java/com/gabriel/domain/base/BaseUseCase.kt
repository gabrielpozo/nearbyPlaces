package com.gabriel.domain.base

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.observers.DisposableSingleObserver
import timber.log.Timber

/**
 * Created by Gabriel Pozo Guzman on 2019-11-30.
 */

abstract class BaseUseCase<T> : Disposable {

    protected var disposables = CompositeDisposable()

    protected fun disposableSingleObserver(
        onNext: (T) -> Unit,
        onError: (Throwable) -> Unit = {}
    ): DisposableSingleObserver<T> {

        return object : DisposableSingleObserver<T>() {
            override fun onSuccess(value: T) {
                Timber.i("onNext...")
                onNext.invoke(value)
            }

            override fun onError(e: Throwable) {
                Timber.e("onError... ${e.localizedMessage}")
                onError.invoke(e)
            }
        }
    }

    override fun dispose() {
        disposables.clear()
    }

    override fun isDisposed(): Boolean {
        return disposables.isDisposed
    }
}