package com.gabriel.domain.base

import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Created by Gabriel Pozo Guzman on 2019-11-30.
 */
abstract class SingleUseCase<T, Params> : BaseUseCase<T>() {

    abstract fun useCaseExecution(params: Params): Single<T>
    fun execute(
        onNext: (T) -> Unit,
        onError: (Throwable) -> Unit = {},
        params: Params
    ) {
        val single = useCaseExecution(params)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
        val disposable = single
            .subscribeWith(disposableSingleObserver(onNext, onError))
        disposables.add(disposable)
    }
}