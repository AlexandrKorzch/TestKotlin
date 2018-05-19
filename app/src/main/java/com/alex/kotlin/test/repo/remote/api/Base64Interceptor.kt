package com.alex.kotlin.test.repo.remote.api

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
        val modifiedRequestBodyString = "hello" //todo change originalRequestBodyString encode base64 decodeBase64(decodeBody)
        Log.d(TAG, "getModifiedRequestBody - modifiedRequestBodyString - $originalRequestBodyString")
        val modifiedRequestBody
                = RequestBody.create(originalRequest.body()?.contentType(), modifiedRequestBodyString)
        return modifiedRequestBody
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
        Log.d("TAG", "$responseBodyString")

        //decode
        responseBodyString = "{\"id\":\"business-insider\",\"name\":\"Business Insider\"}" // change here
        //decodeBase64(responseBodyString) // change here

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


//    Авторизация/Регистрация пользователя  /api/v1.0/user/login/  POST
//@POST("user/login") // OK
//fun getSignInRequest(@Header("Authorization") token: String,
//                     @Header("accept-language") lang: String,
//                     @Body requestBodyBase64: RequestBody): Observable<Response<ResponseBody»

//override fun getCategoriesListRequest(requestModel: RequestModel): Observable<ResponseModel> {
//    return getBaseRequestObservable(requestModel,
//            api.getCategoriesListRequest(requestModel.token, requestModel.lang))
//}
//
//private fun printRequestModel(requestModel: RequestModel) {
//    if (BuildConfig.DEBUG)
//        logD("JSON", "Authorization: ${requestModel.token} & accept-language: ${requestModel.lang} | Request JsonBody: " + requestModel.jsonBody)
//}
//
//private fun getBaseRequestObservable(requestModel: RequestModel, request: Observable<Response<ResponseBody»): Observable<ResponseModel> {
//    printRequestModel(requestModel)
//    return request.subscribeOn(Schedulers.newThread())
//            .observeOn(AndroidSchedulers.mainThread())
//            .unsubscribeOn(Schedulers.io())
//            .map<ResponseModel> { response -> getResponseModel(response) }
//            .doOnError { throwable -> logD("JSON", "OneInteractorImpl Throwable.doOnError() " + throwable.message) }
//}
//
//private fun getResponseModel(response: Response<ResponseBody>): ResponseModel? {
//    val baseResponse = ResponseModel()
//    var decodeBody = ""
//    if (response.body() != null) {
//        logD("JSON", "BaseRequestObservable response -> $response")
//        decodeBody = response.body()!!.string()                                                  // Body
//        baseResponse.isSuccessResponse = decodeBody != null
//    } else {
//        logE("JSON", "BaseRequestObservable response-error -> $response")
//        if (response.errorBody() != null)
//            decodeBody = response.errorBody()!!.string()                                         // ErrorBody
//    }
//    val json = decodeBase64(decodeBody)
//    baseResponse.errorCode = response.code()
//    baseResponse.jsonString = json
//    if (baseResponse.isSuccess) {
//        logD("JSON", "BaseRequestObservable JSON response -> $json")
//    } else {
//        logE("JSON", "BaseRequestObservable JSON response-error -> $json")
//    }
//    return todo500(baseResponse) //TODO
//}