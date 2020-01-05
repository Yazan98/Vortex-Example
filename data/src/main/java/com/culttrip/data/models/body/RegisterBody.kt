package com.culttrip.data.models.body

import com.culttrip.data.models.response.ProfileLocation

/**
 * Created By : Yazan Tarifi
 * Date : 1/5/2020
 * Time : 6:21 PM
 */
  
data class RegisterBody(
    var username: String = "",
    var email: String = "",
    var phoneNumber: String = "",
    var image: String = "",
    var enabled: Boolean = false,
    var accountStatus: String = "",
    var pinCode: String = "",
    var location: ProfileLocation,
    var password: String = ""
)