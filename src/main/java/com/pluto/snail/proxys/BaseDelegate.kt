package com.pluto.snail.proxys

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import me.yokeyword.fragmentation.ExtraTransaction
import me.yokeyword.fragmentation.ISupportFragment
import me.yokeyword.fragmentation.SupportFragmentDelegate
import me.yokeyword.fragmentation.anim.FragmentAnimator

abstract class BaseDelegate : Fragment(), ISupportFragment {
    private val DELEGATE = SupportFragmentDelegate(this)
    private var isLock = true
    abstract fun layout(): Any

    abstract fun bindView(state: Bundle?)

    lateinit var _mActivity: ProxyActivity
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = when (layout()) {
            is View -> layout() as View
            is Int -> inflater.inflate(layout() as Int, container, false)
            else -> throw ClassCastException("type of setLayout() must be int or View!")
        }
        return rootView
    }

    override fun onDestroy() {
        super.onDestroy()
        DELEGATE.onDestroy()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context!!)
        DELEGATE.onAttach(context!! as Activity)
        _mActivity = DELEGATE.activity as ProxyActivity
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DELEGATE.onCreate(savedInstanceState)
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        DELEGATE.onActivityCreated(savedInstanceState)
        bindView(savedInstanceState)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        DELEGATE.onSaveInstanceState(outState)
    }

    override fun setFragmentResult(resultCode: Int, bundle: Bundle?) {
        DELEGATE.setFragmentResult(resultCode, bundle)
    }

    override fun onSupportInvisible() {
        DELEGATE.onSupportInvisible()
    }

    override fun onNewBundle(args: Bundle?) {
        DELEGATE.onNewBundle(args)
    }

    override fun extraTransaction(): ExtraTransaction {
        return DELEGATE.extraTransaction()
    }

    override fun onCreateFragmentAnimator(): FragmentAnimator {
        return DELEGATE.onCreateFragmentAnimator()
    }

    override fun enqueueAction(runnable: Runnable?) {
    }

    override fun onFragmentResult(requestCode: Int, resultCode: Int, data: Bundle?) {
        DELEGATE.onFragmentResult(requestCode, resultCode, data)
    }

    override fun setFragmentAnimator(fragmentAnimator: FragmentAnimator?) {
        DELEGATE.fragmentAnimator = fragmentAnimator
    }

    override fun onLazyInitView(savedInstanceState: Bundle?) {
        DELEGATE.onLazyInitView(savedInstanceState)
    }

    override fun getFragmentAnimator(): FragmentAnimator {
        return DELEGATE.fragmentAnimator
    }

    override fun isSupportVisible(): Boolean {
        return DELEGATE.isSupportVisible
    }

    override fun onEnterAnimationEnd(savedInstanceState: Bundle?) {
        DELEGATE.onEnterAnimationEnd(savedInstanceState)
    }

    override fun onSupportVisible() {
        DELEGATE.onSupportVisible()
    }

    override fun onBackPressedSupport(): Boolean {
        return DELEGATE.onBackPressedSupport()
    }

    override fun getSupportDelegate(): SupportFragmentDelegate {
        return DELEGATE
    }

    override fun putNewBundle(newBundle: Bundle?) {
        DELEGATE.putNewBundle(newBundle)
    }

    override fun post(runnable: Runnable?) {
        DELEGATE.post(runnable)
    }

}