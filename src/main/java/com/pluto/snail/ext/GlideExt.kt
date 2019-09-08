package com.pluto.snail.ext

import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.luck.picture.lib.PictureSelector
import com.luck.picture.lib.config.PictureConfig
import com.luck.picture.lib.config.PictureMimeType


fun options(load: Int, round: Int): RequestOptions {
    val options = RequestOptions.bitmapTransform(RoundedCorners(round))
    options.placeholder(load)
    return options
}

fun roundOptions(round: Int): RequestOptions {
    val options = RequestOptions.bitmapTransform(RoundedCorners(round))
    return options
}

fun options(load: Int): RequestOptions {
    val options = RequestOptions().placeholder(load)
    return options
}

fun options(): RequestOptions {
    return RequestOptions()
}



fun PictureSelector.camera(crop: Boolean = false) {
    this.openCamera(PictureMimeType.ofImage())
            .imageFormat(PictureMimeType.PNG)// 拍照保存图片格式后缀,默认jpeg
            .enableCrop(crop)// 是否裁剪 true or false
            .hideBottomControls(crop)// 是否显示uCrop工具栏，默认不显示 true or false
            .freeStyleCropEnabled(false)// 裁剪框是否可拖拽 true or false
            .circleDimmedLayer(crop)// 是否圆形裁剪 true or false
            .compress(true)// 是否压缩 true or false
            .isDragFrame(false)// 是否可拖动裁剪框(固定)
            .forResult(PictureConfig.CHOOSE_REQUEST)
}


fun PictureSelector.gallery(isCamera: Boolean, maxNum: Int = 9, crop: Boolean = false, select: Int = PictureConfig.MULTIPLE) {
    this.openGallery(PictureMimeType.ofImage())
            .maxSelectNum(maxNum)// 最大图片选择数量 int
            .minSelectNum(1)
            .imageSpanCount(4)// 每行显示个数 int
            .selectionMode(select)// 多选 or 单选 PictureConfig.MULTIPLE or PictureConfig.SINGLE
            .previewImage(true)// 是否可预览图片 true or false
            .isCamera(isCamera)// 是否显示拍照按钮 true or false
            .imageFormat(PictureMimeType.PNG)// 拍照保存图片格式后缀,默认jpeg
            .sizeMultiplier(0.5f)// glide 加载图片大小 0~1之间 如设置 .glideOverride()无效
            .enableCrop(crop)// 是否裁剪 true or false
            .hideBottomControls(crop)// 是否显示uCrop工具栏，默认不显示 true or false
            .freeStyleCropEnabled(false)// 裁剪框是否可拖拽 true or false
            .circleDimmedLayer(crop)// 是否圆形裁剪 true or false
            .compress(true)// 是否压缩 true or false
            .forResult(PictureConfig.CHOOSE_REQUEST)
}

