package com.pluto.charon.network

import com.pluto.snail.AppContext
import com.pluto.snail.ext.net.Result
import com.pluto.snail.ext.net.awaitOrError
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.Level
import org.jetbrains.anko.runOnUiThread
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.pluto.charon.ext.*
import com.pluto.snail.app.Snail
import com.pluto.snail.network.compat.enableTls12OnPreLollipop
import kotlinx.coroutines.Job

private val cacheFile by lazy {
    File(AppContext.cacheDir, "webServiceApi").apply { ensureDir() }
}


private val retrofit by lazy {

    val builder = OkHttpClient.Builder()
        .connectTimeout(60, TimeUnit.SECONDS)
        .readTimeout(60, TimeUnit.SECONDS)
        .writeTimeout(60, TimeUnit.SECONDS)
        .cache(Cache(cacheFile, 1024 * 1024 * 1024))
        .addInterceptor(HttpLoggingInterceptor().setLevel(Level.BODY))
        .enableTls12OnPreLollipop()
    Snail.interceptor.forEach {
        builder.addInterceptor(it)
    }
    Retrofit.Builder()
        .addConverterFactory(ScalarsConverterFactory.create())
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .client(builder.build())
        .baseUrl(Snail.baseUrl)
        .build()
}

object ApiService : RestApi by retrofit.create(RestApi::class.java)


internal fun Result<Response<String>>.result(block: (String) -> Unit, err: () -> Unit) {
    AppContext.runOnUiThread {
        if (error != null) {
            err()
        } else {
            value.isSuccessful.yes {
                block(value.body().toString())
            }.otherwise {
                err()
            }
        }
    }

}

fun Get(
    url: String,
    vararg params: Pair<String, Any>,
    err: () -> Unit = {},
    block: (String) -> Unit
) {
    GlobalScope.launch {
        ApiService.get(url, toHMap(*params)).awaitOrError().result(block, err)
    }
}


fun Post(
    url: String,
    vararg params: Pair<String, Any>,
    type: Boolean = true,
    err: () -> Unit = {},
    block: (String) -> Unit = {}
) {
    GlobalScope.launch {
        type.yes {
            val body = RequestBody.create(
                MediaType.parse("application/json;charset=UTF-8"),
                jsSrt(*params)
            )
            ApiService.postRaw(url, body).awaitOrError().result(block, err)
        }.otherwise {
            ApiService.post(url, toHMap(*params)).awaitOrError().result(block, err)
        }

    }
}

fun Put(
    url: String,
    vararg params: Pair<String, Any>,
    type: Boolean = true,
    err: () -> Unit = {},
    block: (String) -> Unit = {}
) {
    GlobalScope.launch {
        type.yes {
            val body = RequestBody.create(
                MediaType.parse("application/json;charset=UTF-8"),
                jsSrt(*params)
            )
            ApiService.putRaw(url, body).awaitOrError().result(block, err)
        }.otherwise {
            ApiService.put(url, toHMap(*params)).awaitOrError().result(block, err)
        }
    }
}


fun Delete(
    url: String,
    vararg params: Pair<String, Any>,
    err: () -> Unit,
    block: (String) -> Unit = {}
) {
    GlobalScope.launch {
        ApiService.delete(url, toHMap(*params)).awaitOrError().result(block, err)
    }
}


fun Load(
    url: String,
    path: String,
    err: () -> Unit = {},
    block: (String) -> Unit = {}
) {
    GlobalScope.launch {
        val file = File(path)
        val requestBody = RequestBody.create(MediaType.parse(""), file)
        val body = MultipartBody.Part.createFormData("image", file.name, requestBody)
        ApiService.upload(url, body).awaitOrError().result(block, err)
    }
}


private fun toHMap(vararg params: Pair<String, Any>): HashMap<String, Any> {
    val data = hashMapOf<String, Any>()
    params.isNotEmpty().yes { data.putAll(params) }
    return data
}





