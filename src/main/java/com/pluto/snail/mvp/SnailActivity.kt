package com.pluto.snail.mvp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.pluto.snail.proxys.BaseActivity
import java.lang.reflect.ParameterizedType


abstract class SnailActivity<out P : SnailPresenter<SnailActivity<P>>> : BaseActivity(),
    IMvpView<P> {

    final override val presenter: @UnsafeVariance P

    init {
        presenter = createPresenter()
        presenter.view = this
    }

    private fun createPresenter(): P {
        sequence {
            var thisClass: Class<*> = this@SnailActivity.javaClass
            while (true) {
                yield(thisClass.genericSuperclass)
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
    override fun onViewStateRestored(savedInstanceState: Bundle?) {}

    override fun onLazyInitView(savedInstanceState: Bundle?) {

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

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)
        onViewStateRestored(savedInstanceState)
        presenter.onViewStateRestored(savedInstanceState)
    }
}
