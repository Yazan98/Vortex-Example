package com.culttrip.data.models

/**
 * Created By : Yazan Tarifi
 * Date : 1/5/2020
 * Time : 1:48 PM
 */

data class VortexResponse <T>(
    var message: String = "",
    var code: Int,
    var data: T
)
