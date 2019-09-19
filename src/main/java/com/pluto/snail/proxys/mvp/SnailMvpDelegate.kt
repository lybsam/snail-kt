package com.pluto.snail.proxys.mvp

import android.os.Bundle
import com.pluto.charon.proxys.mvp.IMvpView
import com.pluto.charon.proxys.mvp.IPresenter
import com.pluto.charon.proxys.mvp.SnailPresenter
import com.pluto.snail.proxys.SnailDelegate
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

abstract class SnailMvpDelegate<out P : SnailPresenter<SnailMvpDelegate<P>>> : IMvpView<P>,
    SnailDelegate() {
    override val presenter: P

    init {
        presenter = createPresenter()
        presenter.view = this
    }

    private fun createPresenter(): P {
        sequence {
            var thisClass: Class<*> = this@SnailMvpDelegate.javaClass
            while (true) {
                thisClass.genericSuperclass?.let { yield(it) }
                thisClass = thisClass.superclass ?: break
            }
        }.filter {
            it is ParameterizedType
        }.flatMap {
            (it as ParameterizedType).actualTypeArguments.asSequence()
        }.first {
            it is Class<*> && IPresenter::class.java.isAssignableFrom(it)
        }.let {
            return (it as Class<P>).newInstance()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter.onCreate(savedInstanceState)
    }

    override fun onStart() {
        super.onStart()
        presenter.onStart()
    }

    override fun onResume() {
        super.onResume()
        presenter.onResume()
    }

    override fun onPause() {
        super.onPause()
        presenter.onPause()
    }

    override fun onStop() {
        super.onStop()
        presenter.onStop()
    }

    override fun onDestroy() {
        presenter.onDestroy()
        super.onDestroy()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        presenter.onSaveInstanceState(outState)
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        presenter.onViewStateRestored(savedInstanceState)
    }
}