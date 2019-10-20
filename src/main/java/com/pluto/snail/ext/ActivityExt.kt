package com.pluto.snail.ext

import android.app.Activity
import android.app.DownloadManager
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import com.pluto.charon.ext.logger
import com.pluto.snail.AppContext
import org.jetbrains.anko.toast
import java.io.File


fun Activity.barTandBlack(isText: Boolean, isColor: Boolean = false) {
    val decorView = window.decorView
    val option = when (isText) {
        true -> View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        false -> View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
    }
    decorView.systemUiVisibility = option
    window.statusBarColor = when (isColor) {
        true -> Color.WHITE
        false -> Color.TRANSPARENT
    }
}


fun Activity.barTextColor(isText: Boolean = false) {
    val decorView = window.decorView
    val option = when (isText) {
        true -> View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        false -> View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
    }
    decorView.systemUiVisibility = option
}

fun Context.getHPixels(): Int {
    return this.resources.displayMetrics.heightPixels
}

fun Context.getWPixels(): Int {
    return this.resources.displayMetrics.widthPixels
}


fun Activity.twoFinish(TOUCH_TIME: Long): Long {
    val WAIT_TIME = 2000L
    var _time = 0L
    if (System.currentTimeMillis() - TOUCH_TIME < WAIT_TIME) {
        finish()
    } else {
        _time = System.currentTimeMillis()
        toast("双击退出${getString(com.pluto.snail.R.string.app_name)}")
    }
    return _time
}


fun Activity.versionCode(): String {
    return packageManager.getPackageInfo(packageName, 0).versionName
}


fun toast(str: String) {
    AppContext.toast(str)
}

fun AppCompatActivity.hideSoftKeyboard(vararg viewList: View) {
    val inputMethodManager =
        this.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager

    for (v in viewList) {
        inputMethodManager!!.hideSoftInputFromWindow(
            v.windowToken,
            InputMethodManager.HIDE_NOT_ALWAYS
        )
    }
}

fun Fragment.hideSoftKeyboard(vararg viewList: View) {
    val inputMethodManager =
        this.activity?.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager

    for (v in viewList) {
        inputMethodManager!!.hideSoftInputFromWindow(
            v.windowToken,
            InputMethodManager.HIDE_NOT_ALWAYS
        )
    }
}

fun clearFocus(vararg views: EditText) {
    views.forEach {
        it.clearFocus()
    }
}


fun AppCompatActivity.installApk(id: Long, name: String) {
    val intentInstall = Intent()
    intentInstall.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    intentInstall.action = Intent.ACTION_VIEW
    var uri: Uri
    val downloadManager = getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) { // 6.0以下
        uri = downloadManager.getUriForDownloadedFile(id)
        intentInstall.setDataAndType(uri, "application/vnd.android.package-archive")
    } else if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) { // 6.0 - 7.0
        val query = DownloadManager.Query()
        query.setFilterById(id)
        query.setFilterByStatus(DownloadManager.STATUS_SUCCESSFUL)
        val cursor = downloadManager.query(query)
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                val path = cursor.getString(cursor.getColumnIndex(DownloadManager.COLUMN_LOCAL_URI))
                val apkFile = File(Uri.parse(path).path)
                uri = Uri.fromFile(apkFile)
                intentInstall.setDataAndType(uri, "application/vnd.android.package-archive")
            }
        }

    } else { // Android 7.0 以上
        uri = FileProvider.getUriForFile(
            this,
            "${packageName}.fileProvider",
            File(
                this.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS),
                "${name}.apk"
            )
        )

        intentInstall.setDataAndType(uri, "application/vnd.android.package-archive")
        intentInstall.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
    }


    this.startActivity(intentInstall)
}