package com.pluto.snail.apk

import android.app.DownloadManager
import android.app.IntentService
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Environment
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import java.io.File

class DownloadService : IntentService("DownloadService") {

    companion object {
        val BROADCAST_ACTION = "com.example.android.threadsample.BROADCAST"
        val EXTENDED_DATA_STATUS = "com.example.android.threadsample.STATUS"
    }

    private lateinit var mLocalBroadcastManager: LocalBroadcastManager

    override fun onHandleIntent(intent: Intent?) {
        //获取下载地址
        val url = intent?.getStringExtra("url")
        val name = intent?.getStringExtra("name")
        //获取DownloadManager对象
        val downloadManager = getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
        //已存在 -- 删除
        val apkFile =
            File(this.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS), "${name}.apk");
        if (apkFile != null && apkFile.exists()) {
            apkFile.delete()
        }
        val request = DownloadManager.Request(Uri.parse(url))
        //指定APK缓存路径和应用名称,比如我这个路径就是/storage/emulated/0/Download/vooloc.apk
        //设置title
        request.setDestinationInExternalFilesDir(
            this,
            Environment.DIRECTORY_DOWNLOADS,
            "${name}.apk"
        );
        //设置网络下载环境为wifi或者移动数据
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI or DownloadManager.Request.NETWORK_MOBILE);
        //设置显示通知栏，下载完成后通知栏自动消失
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
        //设置通知栏标题
        request.setTitle("${name}")
        //设置这个下载的描述，显示在通知中
        request.setDescription("正在下载...")
        //设置类型为.apk
        request.setMimeType("application/vnd.android.package-archive");
        //获得唯一下载id
        val requestId = downloadManager.enqueue(request)
        //将id放进Intent
        val localIntent = Intent(BROADCAST_ACTION);
        localIntent.putExtra(EXTENDED_DATA_STATUS, requestId)
        localIntent.putExtra("name", name)
        //查询下载信息
        val query = DownloadManager.Query()
        //只包括带有给定id的下载。
        query.setFilterById(requestId);
        try {
            var isGoging = true
            while (isGoging) {
                val cursor = downloadManager.query(query)
                if (cursor != null && cursor.moveToFirst()) {
                    val status =
                        cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS));
                    when (status) {
                        //如果下载状态为成功
                        DownloadManager.STATUS_SUCCESSFUL -> {
                            isGoging = false;
                            //调用LocalBroadcastManager.sendBroadcast将intent传递回去
                            mLocalBroadcastManager = LocalBroadcastManager.getInstance(this);
                            mLocalBroadcastManager.sendBroadcast(localIntent)
                        }
                    }
                }
                if (cursor != null) {
                    cursor.close();
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}