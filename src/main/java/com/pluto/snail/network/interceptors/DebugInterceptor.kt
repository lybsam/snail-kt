package  com.pluto.snail.network.interceptors

import androidx.annotation.RawRes
import com.pluto.snail.util.file.FileUtil
import okhttp3.*
import java.io.IOException

class DebugInterceptor(private val DEBUG_URL: String, private val DEBUG_RAW_ID: Int) : BaseInterceptor() {

    private fun getResponse(chain: Interceptor.Chain, json: String): Response {
        return Response.Builder()
                .code(200)
                .addHeader("Content-Type", "application/json")
                .body(ResponseBody.create(MediaType.parse("application/json"), json))
                .message("OK")
                .request(chain.request())
                .protocol(Protocol.HTTP_1_1)
                .build()
    }

    private fun debugResponse(chain: Interceptor.Chain, @RawRes rawId: Int): Response {
        val json = FileUtil.readRawFile(rawId)
        return getResponse(chain, json)
    }

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val url = chain.request().url().toString()
        return if (url.contains(DEBUG_URL)) {
            debugResponse(chain, DEBUG_RAW_ID)
        } else chain.proceed(chain.request())
    }
}