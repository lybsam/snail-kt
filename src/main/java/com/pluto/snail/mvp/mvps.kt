package com.pluto.snail.mvp

interface IPresenter<out View: IMvpView<IPresenter<View>>>: ILifecycle{
    val view: View
}

interface IMvpView<out Presenter: IPresenter<IMvpView<Presenter>>>: ILifecycle{
    val presenter: Presenter
}