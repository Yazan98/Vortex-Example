package com.culttrip.data.models.response

/**
 * Created By : Yazan Tarifi
 * Date : 1/5/2020
 * Time : 2:08 PM
 */

data class AuthResponse(
    var token: String = "",
    var user: UserResponse
)
