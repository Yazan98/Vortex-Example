package com.culttrip.data.models.response

/**
 * Created By : Yazan Tarifi
 * Date : 1/5/2020
 * Time : 2:08 PM
 */

data class UserResponse(
    var id: Long = 0,
    var username: String = "",
    var email: String = "",
    var phoneNumber: String = "",
    var image: String = "",
    var enabled: Boolean = false,
    var accountStatus: String = "",
    var location: ProfileLocation
)

data class ProfileLocation(
    var latitude: Double = 0.0,
    var longitude: Double = 0.0,
    var name: String = ""
)
