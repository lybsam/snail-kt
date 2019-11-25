package  com.pluto.snail.network


import kotlinx.coroutines.Deferred
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.*

interface RestApi {
    @GET
    fun get(@Url url: String, @QueryMap params: HashMap<String, Any>): Deferred<Response<String>>


    @GET
    fun get(@Url url: String, @Body body: RequestBody): Deferred<Response<String>>

    @FormUrlEncoded
    @POST
    fun post(@Url url: String, @FieldMap params: HashMap<String, Any>): Deferred<Response<String>>

    @POST
    fun postRaw(@Url url: String, @Body body: RequestBody): Deferred<Response<String>>

    @FormUrlEncoded
    @PUT
    fun put(@Url url: String, @FieldMap params: HashMap<String, Any>): Deferred<Response<String>>

    @PUT
    fun putRaw(@Url url: String, @Body body: RequestBody): Deferred<Response<String>>

    @DELETE
    fun delete(@Url url: String, @QueryMap params: HashMap<String, Any>): Deferred<Response<String>>


    @Multipart
    @POST
    fun upload(@Url url: String, @Part file: MultipartBody.Part): Deferred<Response<String>>

    @POST
    fun upload(@Url url: String, @Body body: RequestBody): Deferred<Response<String>>

    @Streaming
    @GET
    fun download(@Url url: String): Deferred<Response<ResponseBody>>
}