package com.ms.apimockdata.model

import com.google.gson.annotations.SerializedName

data class UserListModel(
    @SerializedName("user_id")
    var userId: Int? = null,
    @SerializedName("username")
    var userName: String? = null,
    @SerializedName("email")
    var email: String? = null,
    @SerializedName("password")
    var password: String? = null
)