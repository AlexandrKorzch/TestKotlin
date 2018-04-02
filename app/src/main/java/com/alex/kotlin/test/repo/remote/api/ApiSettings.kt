package com.alex.kotlin.test.repo.remote.api

/**
 * Created by alex on 3/19/18.
 */
object ApiSettings {

    /*server*/
    private const val SCHEME = "https://"
    private const val HOSTNAME = "newsapi.org/"
    const val SERVER = SCHEME + HOSTNAME

    /*methods*/
    const val GET_NEWS = "v2/top-headlines"

    /*params*/
    const val COUNTRY = "country"
    const val CATEGORY = "category"
    const val API_KEY = "apiKey"

    /*apiKey*/
    const val KEY = "20f4273ddfff4caa89836ca9629de2af"
}