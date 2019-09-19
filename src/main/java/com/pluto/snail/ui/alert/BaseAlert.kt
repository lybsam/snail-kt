package com.pluto.snail.ui.alert

import android.app.Activity
import android.content.DialogInterface
import android.graphics.Rect
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.*
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import com.pluto.snail.R


abstract class BaseAlert : DialogFragment(), DialogInterface.OnKeyListener {
    private var isBottom = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        when (isBottom) {
            true -> setStyle(STYLE_NO_TITLE, R.style.BottomDialog)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(layout(), null)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        val window = getDialog()?.getWindow()!!
        window.requestFeature(Window.FEATURE_NO_TITLE)
        super.onActivityCreated(savedInstanceState)
        //设置背景为透明
        window.setBackgroundDrawable(
            ContextCompat.getDrawable(
                getContext()!!,
                android.R.color.transparent
            )
        )
        val dialogHeight = getActivity()?.let { getContextRect(it) }
        //设置弹窗大小为会屏

        if (dialogHeight == 0)
            window.setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
        else dialogHeight?.let { window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, it) }
        //去除阴影
        val layoutParams = window.getAttributes()
        layoutParams.dimAmount = 0.0f
        window.setAttributes(layoutParams)
        dialog!!.setOnKeyListener(this)
        init()
    }

    //获取内容区域
    private fun getContextRect(activity: Activity): Int {
        //应用区域
        val outRect1 = Rect();
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(outRect1)
        return outRect1.height()
    }

    abstract fun layout(): Int

    abstract fun init()

    protected fun isBottom(isBottom: Boolean) {
        this.isBottom = isBottom
    }

    override fun onKey(dialog: DialogInterface?, keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            return true
        } else {
            //这里注意当不是返回键时需将事件扩散，否则无法处理其他点击事件
            return false
        }
    }

}

