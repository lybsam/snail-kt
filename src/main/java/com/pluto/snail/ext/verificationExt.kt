package com.pluto.snail.ext

import com.google.android.material.textfield.TextInputEditText
import java.util.regex.Pattern


fun String.isPhoneNum(block: (String) -> Unit) {
    if (this.isEmpty()) {
        toast("账号不能为空!")
        return
    }
    val telRegex = "^((13[0-9])|(14[5,7,9])|(15[^4])|(18[0-9])|(17[0,1,3,5,6,7,8]))\\d{8}$"
    val compile = Pattern.compile(telRegex)
    val mc = compile.matcher(this)
    if (!mc.matches()) {
        toast("请输入正确的号码!")
        return
    }
    block.invoke(this)
}


fun TextInputEditText.isPhoneNum(block: (String) -> Unit) {
    val text = this.text.toString()
    if (text.isEmpty()) {
        this.setError("账号不能为空!")
        return
    }
    val telRegex = "^((13[0-9])|(14[5,7,9])|(15[^4])|(18[0-9])|(17[0,1,3,5,6,7,8]))\\d{8}$"
    val compile = Pattern.compile(telRegex)
    val mc = compile.matcher(text)
    if (!mc.matches()) {
        this.setError("请输入正确的号码!")
        return
    }
    block.invoke(text)
}

fun TextInputEditText.isPassLen(block: (String) -> Unit) {
    val text = this.text.toString()
    if (text.isEmpty()) {
        this.setError("密码不能为空!")
        return
    } else if (text.length < 6) {
        this.setError("密码长度为6-18位,数字字母组合!")
        return
    }
    block.invoke(text)
}

fun TextInputEditText.isPass(block: (String) -> Unit, view: TextInputEditText) {
    val p1 = this.text.toString()
    val p2 = view.text.toString()
    if (p1.isEmpty()) {
        this.setError("密码不能为空!")
        return
    } else if (p1.length < 6) {
        this.setError("密码长度为6-18位,数字字母组合!")
        return
    } else if (p2.isEmpty()) {
        view.setError("两次密码不一样")
        return
    } else if (!p1.equals(p2)) {
        view.setError("两次密码不一样")
        return
    }
    block.invoke(p1)
}

fun TextInputEditText.isOldPass(block: (String) -> Unit, pass: String) {
    val p1 = this.text.toString()
    if (p1.isEmpty()) {
        this.setError("密码不能为空!")
        return
    } else if (p1.length < 6) {
        this.setError("密码长度为6-18位,数字字母组合!")
        return
    } else if (!p1.equals(pass)) {
        this.setError("旧密码错误!")
        return
    }
    block.invoke(p1)
}


