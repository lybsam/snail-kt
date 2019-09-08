package com.pluto.snail.util.file

import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Typeface
import android.media.MediaScannerConnection
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.webkit.MimeTypeMap
import android.widget.TextView
import com.pluto.snail.AppContext
import java.io.*
import java.text.SimpleDateFormat
import java.util.*

object FileUtil {

    //格式化的模板
    private val TIME_FORMAT = "_yyyyMMdd_HHmmss"

    private val SDCARD_DIR = Environment.getExternalStorageDirectory().path

    //默认本地上传图片目录
    val UPLOAD_PHOTO_DIR = Environment.getExternalStorageDirectory().path + "/a_upload_photos/"

    //网页缓存地址
    val WEB_CACHE_DIR = Environment.getExternalStorageDirectory().path + "/app_web_cache/"

    //系统相机目录
    val CAMERA_PHOTO_DIR =
        Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).path + "/Camera/"

    private fun getTimeFormatName(timeFormatHeader: String): String {
        val date = Date(System.currentTimeMillis())
        //必须要加上单引号
        val dateFormat = SimpleDateFormat("'$timeFormatHeader'$TIME_FORMAT", Locale.getDefault())
        return dateFormat.format(date)
    }

    /**
     * @param timeFormatHeader 格式化的头(除去时间部分)
     * @param extension        后缀名
     * @return 返回时间格式化后的文件名
     */
    fun getFileNameByTime(timeFormatHeader: String, extension: String): String {
        return getTimeFormatName(timeFormatHeader) + "." + extension
    }

    private fun createDir(sdcardDirName: String): File {
        //拼接成SD卡中完整的dir
        val dir = "$SDCARD_DIR/$sdcardDirName/"
        val fileDir = File(dir)
        if (!fileDir.exists()) {
            fileDir.mkdirs()
        }
        return fileDir
    }

    fun createFile(sdcardDirName: String, fileName: String): File {
        return File(createDir(sdcardDirName), fileName)
    }

    private fun createFileByTime(
        sdcardDirName: String,
        timeFormatHeader: String,
        extension: String
    ): File {
        val fileName = getFileNameByTime(timeFormatHeader, extension)
        return createFile(sdcardDirName, fileName)
    }

    //获取文件的MIME
    fun getMimeType(filePath: String): String? {
        val extension = getExtension(filePath)
        return MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension)
    }

    //获取文件的后缀名
    fun getExtension(filePath: String): String {
        var suffix = ""
        val file = File(filePath)
        val name = file.name
        val idx = name.lastIndexOf('.')
        if (idx > 0) {
            suffix = name.substring(idx + 1)
        }
        return suffix
    }

    /**
     * 保存Bitmap到SD卡中
     *
     * @param dir      目录名,只需要写自己的相对目录名即可
     * @param compress 压缩比例 100是不压缩,值约小压缩率越高
     * @return 返回该文件
     */
    fun saveBitmap(mBitmap: Bitmap, dir: String, compress: Int): File? {

        val sdStatus = Environment.getExternalStorageState()
        // 检测sd是否可用
        if (sdStatus != Environment.MEDIA_MOUNTED) {
            return null
        }
        var fos: FileOutputStream? = null
        var bos: BufferedOutputStream? = null
        val fileName = createFileByTime(dir, "DOWN_LOAD", "jpg")
        try {
            fos = FileOutputStream(fileName)
            bos = BufferedOutputStream(fos)
            mBitmap.compress(Bitmap.CompressFormat.JPEG, compress, bos)// 把数据写入文件
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        } finally {
            try {

                bos?.flush()
                bos?.close()
                //关闭流
                fos?.flush()
                fos?.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }

        }
        refreshDCIM()

        return fileName
    }

    fun writeToDisk(`is`: InputStream, dir: String, name: String): File {
        val file = FileUtil.createFile(dir, name)
        var bis: BufferedInputStream? = null
        var fos: FileOutputStream? = null
        var bos: BufferedOutputStream? = null

        try {
            bis = BufferedInputStream(`is`)
            fos = FileOutputStream(file)
            bos = BufferedOutputStream(fos)

            val data = ByteArray(1024 * 4)

            while (bis.read(data) != -1) {
                bos.write(data, 0, bis.read(data))
            }

            bos.flush()
            fos.flush()


        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            try {
                bos?.close()
                fos?.close()
                bis?.close()
                `is`.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }

        }

        return file
    }

    fun writeToDisk(`is`: InputStream, dir: String, prefix: String, extension: String): File {
        val file = FileUtil.createFileByTime(dir, prefix, extension)
        var bis: BufferedInputStream? = null
        var fos: FileOutputStream? = null
        var bos: BufferedOutputStream? = null

        try {
            bis = BufferedInputStream(`is`)
            fos = FileOutputStream(file)
            bos = BufferedOutputStream(fos)

            val data = ByteArray(1024 * 4)

            while (bis.read(data) != -1) {
                bos.write(data, 0, bis.read(data))
            }

            bos.flush()
            fos.flush()


        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            try {
                bos?.close()
                fos?.close()
                bis?.close()
                `is`.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }

        }

        return file
    }

    /**
     * 通知系统刷新系统相册，使照片展现出来
     */
    private fun refreshDCIM() {
        if (Build.VERSION.SDK_INT >= 19) {
            //兼容android4.4版本，只扫描存放照片的目录
            MediaScannerConnection.scanFile(
                AppContext,
                arrayOf(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).path),
                null,
                null
            )
        } else {
            //扫描整个SD卡来更新系统图库，当文件很多时用户体验不佳，且不适合4.4以上版本
            AppContext.sendBroadcast(
                Intent(
                    Intent.ACTION_MEDIA_MOUNTED,
                    Uri.parse("file://" + Environment.getExternalStorageDirectory())
                )
            )
        }
    }

    /**
     * 读取raw目录中的文件,并返回为字符串
     */
    fun readRawFile(id: Int): String {
        val ir = InputStreamReader(AppContext.resources.openRawResource(id))
        val br = BufferedReader(ir)
        var line = br.readLine()
        var ressult = ""
        while (line != null) {
            line = br.readLine()
            ressult += line
        }
        return ressult
    }


    fun setIconFont(path: String, textView: TextView) {
        val typeface = Typeface.createFromAsset(AppContext.assets, path)
        textView.typeface = typeface
    }

    /**
     * 读取assets目录下的文件,并返回字符串
     */
    fun getAssetsFile(name: String): String? {
        var `is`: InputStream? = null
        var bis: BufferedInputStream? = null
        var isr: InputStreamReader? = null
        var br: BufferedReader? = null
        var stringBuilder: StringBuilder? = null
        val assetManager = AppContext.assets
        try {
            `is` = assetManager.open(name)
            bis = BufferedInputStream(`is`!!)
            isr = InputStreamReader(bis)
            br = BufferedReader(isr)
            stringBuilder = StringBuilder()
            while (br.readLine() != null) {
                stringBuilder.append(br.readLine())
            }
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            try {
                br?.close()
                isr?.close()
                bis?.close()
                `is`?.close()
                assetManager.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }

        }
        return stringBuilder?.toString()
    }

    fun getRealFilePath(context: Context, uri: Uri?): String? {
        if (null == uri) return null
        val scheme = uri.scheme
        var data: String? = null
        if (scheme == null)
            data = uri.path
        else if (ContentResolver.SCHEME_FILE == scheme) {
            data = uri.path
        } else if (ContentResolver.SCHEME_CONTENT == scheme) {
            val cursor = context.contentResolver.query(
                uri,
                arrayOf(MediaStore.Images.ImageColumns.DATA),
                null,
                null,
                null
            )
            if (null != cursor) {
                if (cursor.moveToFirst()) {
                    val index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA)
                    if (index > -1) {
                        data = cursor.getString(index)
                    }
                }
                cursor.close()
            }
        }
        return data
    }
}