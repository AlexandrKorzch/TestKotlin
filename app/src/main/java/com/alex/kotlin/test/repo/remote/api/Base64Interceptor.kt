package com.alex.kotlin.test.repo.remote.api

import android.util.Base64
import android.util.Log
import okhttp3.*
import okio.Buffer
import okio.ByteString.decodeBase64
import java.io.IOException
import java.lang.Long
import java.nio.charset.Charset


class Base64Interceptor(val identificator: String) : Interceptor {

    val TAG = "Base64Interceptor"

    val token = "TOKEN"
    val language = "LANGUAGE"

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        log("originalRequest", originalRequest)
        val modifiedRequest = getModifiedRequest(originalRequest, token, language)
        log("modifiedRequest", modifiedRequest)
        val originalResponse = chain.proceed(modifiedRequest)
        log("originalResponse", originalResponse)
        val modifiedResponse = decodeResponseFromBase64(originalResponse)
        log("modifiedResponse", modifiedResponse)
        return modifiedResponse
    }

    //request
    private fun getModifiedRequest(originalRequest: Request, token: String, language: String): Request? {
        return if (originalRequest.body() == null) {
            Log.d(TAG, "getModifiedRequest - originalRequest.body() = null")
            modifiedRequestCreator(originalRequest, originalRequest.body(), token, language)
        } else {
            Log.d(TAG, "getModifiedRequest - originalRequest.body() != null")
            val modifiedRequestBody = getModifiedRequestBody(originalRequest)
            modifiedRequestCreator(originalRequest, modifiedRequestBody, token, language)
        }
    }

    private fun getModifiedRequestBody(originalRequest: Request): RequestBody? {
        val originalRequestBodyString = bodyToString(originalRequest.body())
        Log.d(TAG, "getModifiedRequestBody - originalRequestBodyString - $originalRequestBodyString")
        val data = originalRequestBodyString.toString().toByteArray(Charset.forName("UTF-8"))
        val modifiedRequestBodyString = Base64.encodeToString(data, Base64.DEFAULT)
        Log.d(TAG, "getModifiedRequestBody - modifiedRequestBodyString - $originalRequestBodyString")
        return RequestBody.create(originalRequest.body()?.contentType(), modifiedRequestBodyString)
    }

    private fun modifiedRequestCreator(originalRequest: Request, body: RequestBody?,
                                       token: String, language: String): Request? {
        return originalRequest.newBuilder()
                .header("Authorization", token)
                .header("accept-language", language)
                .method(originalRequest.method(), body)
                .build()
    }

    private fun bodyToString(request: RequestBody?): String? {
        try {
            val buffer = Buffer()
            if (request != null) {
                request.writeTo(buffer)
            }else {
                return null
            }
            return buffer.readUtf8()
        } catch (e: IOException) {
            Log.d("TAG", "did not work")
            return null
        }

    }

    //response
    private fun decodeResponseFromBase64(originalResponse: Response): Response {
        val source = originalResponse.body()?.source()
        source?.request(Long.MAX_VALUE)
        val buffer = source?.buffer()
        val contentType = originalResponse.body()?.contentType()//contentType - application/json; charset=utf-8
        var responseBodyString = buffer?.clone()?.readString(Charset.forName("UTF-8"))
        Log.d(TAG, "$responseBodyString")
        //decode for test
//        responseBodyString ="eyJzdGF0dXMiOiJvayIsInRvdGFsUmVzdWx0cyI6MjAsImFydGljbGVzIjpbeyJzb3VyY2UiOnsiaWQiOm51bGwsIm5hbWUiOiJMYXRpbWVzLmNvbSJ9LCJhdXRob3IiOiJIdWdvIE1hcnRpbiIsInRpdGxlIjoiQWxhc2thIEFpcmxpbmVzIHRvIGNsb3NlIE5ldyBZb3JrIHBpbG90IGJhc2UgdG8gZm9jdXMgb24gV2VzdCBDb2FzdCBkZW1hbmQiLCJkZXNjcmlwdGlvbiI6IlRoZSBTZWF0dGxlLWJhc2VkIGNhcnJpZXIgaXMgYXNraW5nIG1vcmUgdGhhbiAxMDAgcGlsb3RzIHRvIHJlbG9jYXRlIHRvIENhbGlmb3JuaWEuIiwidXJsIjoiaHR0cDovL3d3dy5sYXRpbWVzLmNvbS9idXNpbmVzcy9sYS1maS10cmF2ZWwtYnJpZWZjYXNlMy1hbGFza2Etd2VzdC1jb2FzdC0yMDE4MDUxOS1zdG9yeS5odG1sIiwidXJsVG9JbWFnZSI6Imh0dHA6Ly93d3cubGF0aW1lcy5jb20vcmVzaXplci9YX1lYQ1hzczFXUVFLUnBTRklPQ0VSWGYyX1E9LzEyMDB4MC9hcmMtYW5nbGVyZmlzaC1hcmMyLXByb2QtdHJvbmMuczMuYW1hem9uYXdzLmNvbS9wdWJsaWMvV0VOSVFWVFg1SkcySERURlVSTUVCQVNGTEEuanBnIiwicHVibGlzaGVkQXQiOiIyMDE4LTA1LTE5VDEzOjAxOjMwWiJ9XX0=" // change here
        responseBodyString = decodeBase64(responseBodyString)?.string(Charset.forName("UTF-8"))// change here
        val newBody = ResponseBody.create(contentType, responseBodyString)
        return originalResponse.newBuilder()
                .body(newBody)
                .build()
    }

    //log
    private fun log(tag:String, request : Request?){
        Log.d(TAG, "$identificator $tag headers - ${request?.headers()}")
        Log.d(TAG, "$identificator $tag method - ${request?.method()}")
        Log.d(TAG, "$identificator $tag body - ${request?.body()}")
    }

    private fun log(tag:String, response: Response?){
        Log.d(TAG, "$identificator $tag headers - ${response?.headers()}")
        Log.d(TAG, "$identificator $tag Authorization - ${response?.header("Authorization")}")
        Log.d(TAG, "$identificator $tag body - ${response?.body()}")
    }
}
