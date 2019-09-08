package com.pluto.snail.proxys

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.ContentFrameLayout
import com.pluto.snail.R
import me.yokeyword.fragmentation.ExtraTransaction
import me.yokeyword.fragmentation.ISupportActivity
import me.yokeyword.fragmentation.SupportActivityDelegate
import me.yokeyword.fragmentation.anim.FragmentAnimator

abstract class ProxyActivity : AppCompatActivity(), ISupportActivity {

    private val DELEGATE = SupportActivityDelegate(this)
    abstract fun setRootDelegate(): SnailDelegate


    @SuppressLint("RestrictedApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DELEGATE.onCreate(savedInstanceState)
        val content = ContentFrameLayout(this@ProxyActivity.baseContext)
        content.id = R.id.delegate_container
        setContentView(content)
        if (savedInstanceState == null) {
            DELEGATE.loadRootFragment(R.id.delegate_container, setRootDelegate())
        }
    }


    override fun setFragmentAnimator(fragmentAnimator: FragmentAnimator?) {
        DELEGATE.fragmentAnimator = fragmentAnimator
    }

    override fun getFragmentAnimator(): FragmentAnimator {
        return DELEGATE.fragmentAnimator
    }

    override fun onBackPressedSupport() {
        DELEGATE.onBackPressedSupport()
    }

    override fun extraTransaction(): ExtraTransaction {
        return DELEGATE.extraTransaction()
    }

    override fun onCreateFragmentAnimator(): FragmentAnimator {
        return DELEGATE.onCreateFragmentAnimator()
    }

    override fun getSupportDelegate(): SupportActivityDelegate {
        return DELEGATE
    }

    override fun post(runnable: Runnable?) {
        DELEGATE.post(runnable)
    }

    override fun onDestroy() {
        super.onDestroy()
        DELEGATE.onDestroy()
    }

}