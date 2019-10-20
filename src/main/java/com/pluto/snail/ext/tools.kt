package com.pluto.charon.ext

import android.Manifest
import android.app.Activity
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import androidx.fragment.app.Fragment
import com.pluto.snail.AppContext
import com.pluto.snail.ui.view.Faces
import com.pluto.snail.ui.view.Titanic
import com.pluto.snail.ui.view.TitanicTextView
import com.zhy.view.flowlayout.FlowLayout
import com.zhy.view.flowlayout.TagAdapter
import com.zhy.view.flowlayout.TagFlowLayout
import org.jetbrains.anko.layoutInflater
import pub.devrel.easypermissions.AfterPermissionGranted
import pub.devrel.easypermissions.AppSettingsDialog
import pub.devrel.easypermissions.EasyPermissions
import pub.devrel.easypermissions.PermissionRequest
import java.util.*

//输入监听
fun EditText.afterText(success: (String) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
            success(s.toString())
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

        }

    })
}

//Titanic
fun TitanicTextView.titanic(ttf: String = "Satisfy-Regular.ttf") {
    this.setTypeface(Faces.get(AppContext, ttf))
    Titanic().start(this)
}
//流式布局

fun TagFlowLayout.setData(block: (View, String?) -> Unit, ids: Int, arr: List<String>) {
    this.adapter = object : TagAdapter<String>(arr) {
        override fun getView(parent: FlowLayout?, position: Int, t: String?): View {
            val view = AppContext.layoutInflater.inflate(ids, parent, false)
            block(view, t)
            return view
        }

    }
}


//权限申请
const val RC_CAMERA_AND_LOCATION = 10002

@AfterPermissionGranted(RC_CAMERA_AND_LOCATION)
fun Activity.easyPermissions(success: () -> Unit, arr: ArrayList<String>) {
    val activity = this
    val perms = arrayOf(
        Manifest.permission.CAMERA,
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.WRITE_EXTERNAL_STORAGE
    )
    if (EasyPermissions.hasPermissions(activity, *perms)) {
        success()
    } else {
        EasyPermissions.requestPermissions(
            PermissionRequest.Builder(activity, RC_CAMERA_AND_LOCATION, *perms)
                .setRationale(arr[0])
                .setPositiveButtonText(arr[1])
                .setNegativeButtonText(arr[2])
                .build()
        )
    }
    object : EasyPermissions.PermissionCallbacks {
        override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {
            //如果用户点击永远禁止，这个时候就需要跳到系统设置页面去手动打开了
            if (EasyPermissions.somePermissionPermanentlyDenied(activity, perms)) {
                AppSettingsDialog.Builder(activity).build().show()
            }
        }

        override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {
            success()
        }

        override fun onRequestPermissionsResult(
            requestCode: Int,
            permissions: Array<out String>,
            grantResults: IntArray
        ) {
            EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults)
        }

    }
}


@AfterPermissionGranted(RC_CAMERA_AND_LOCATION)
fun Fragment.easyPermissions(arr: ArrayList<String>, hash: () -> Unit = {}) {
    val fm = this
    val perms = arrayOf(
        Manifest.permission.CAMERA,
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.WRITE_EXTERNAL_STORAGE
    )
    if (EasyPermissions.hasPermissions(fm.context!!, *perms)) {
        hash()
    } else {
        EasyPermissions.requestPermissions(
            PermissionRequest.Builder(fm, RC_CAMERA_AND_LOCATION, *perms)
                .setRationale(arr[0])
                .setPositiveButtonText(arr[1])
                .setNegativeButtonText(arr[2])
                .build()
        )
    }
    object : EasyPermissions.PermissionCallbacks {
        override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {
            //如果用户点击永远禁止，这个时候就需要跳到系统设置页面去手动打开了
            if (EasyPermissions.somePermissionPermanentlyDenied(fm.activity!!, perms)) {
                AppSettingsDialog.Builder(fm.activity!!).build().show()
            }
        }

        override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {
        }

        override fun onRequestPermissionsResult(
            requestCode: Int,
            permissions: Array<out String>,
            grantResults: IntArray
        ) {
            EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults)
        }

    }
}
//----------------------------------------------------------------------------------------------------------------------------

//定时器
fun timer(e: () -> Unit, period: Long, delay: Long = 0) {
    Timer().schedule(object : TimerTask() {
        override fun run() {
            e()
        }
    }, delay, period)
}

//时间差
fun timeLess(
    e: (d: Int, h: Int, m: Int, s: Int) -> Unit,
    oldy: Int = 2019,
    oldm: Int = 5,
    oldd: Int = 1,
    oldh: Int = 19,
    oldf: Int = 43
) {
    val calendar = Calendar.getInstance();
    val year = calendar.get(Calendar.YEAR)//年
    val month = calendar.get(Calendar.MONTH) + 1 //月
    val day = calendar.get(Calendar.DAY_OF_MONTH)  //日
    val hour = calendar.get(Calendar.HOUR_OF_DAY) //小时
    val minute = calendar.get(Calendar.MINUTE) //分钟
    val second = calendar.get(Calendar.SECOND)//秒
    var run = 365
    var rDay: Int
    if (year % 4 == 0) {
        run = 366
        rDay = 29
    } else {
        rDay = 28
    }
    if (month == 1 || month == 3 || month == 5 || month == 7 ||
        month == 8 || month == 10 || month == 12
    ) {
        rDay = 31
    } else if (month == 4 || month == 6 || month == 9 || month == 11) {
        rDay = 30
    }
    var numDay = (year - oldy) * run * 24 * 60
    numDay += (month - oldm) * rDay * 24 * 60
    numDay += (((day - oldd) * 24 - oldh) * 60 - oldf + hour * 60 + minute)
    val d = numDay / (60 * 24)
    val h = numDay % (60 * 24) / 60
    val m = numDay % (60 * 24) % 24
    e(d, h, m, second)
}


