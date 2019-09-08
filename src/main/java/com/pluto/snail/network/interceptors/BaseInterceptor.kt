package  com.pluto.charon.network.interceptors

import okhttp3.FormBody
import okhttp3.Interceptor
import java.util.LinkedHashMap

abstract class BaseInterceptor : Interceptor {

    protected fun getUrlParameters(chain: Interceptor.Chain): LinkedHashMap<String, String> {
        val url = chain.request().url()
        val size = url.querySize()
        val params = LinkedHashMap<String, String>()
        for (i in 0 until size) {
            params[url.queryParameterName(i)] = url.queryParameterValue(i)
        }
        return params
    }

    protected fun getUrlParameters(chain: Interceptor.Chain, key: String): String? {
        val request = chain.request()
        return request.url().queryParameter(key)
    }

    protected fun getBodyParameters(chain: Interceptor.Chain): LinkedHashMap<String, String> {
        val formBody = chain.request().body() as FormBody?
        val params = LinkedHashMap<String, String>()
        var size = 0
        if (formBody != null) {
            size = formBody.size()
        }
        for (i in 0 until size) {
            params[formBody!!.name(i)] = formBody.value(i)
        }
        return params
    }

    protected fun getBodyParameters(chain: Interceptor.Chain, key: String): String? {
        return getBodyParameters(chain)[key]
    }
}