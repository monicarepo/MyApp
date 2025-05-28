package com.ms.apimockdata.model

import com.google.gson.annotations.SerializedName

data class OrderModel(
    @SerializedName("order_id")
    var orderId: Int? = null,
    @SerializedName("status")
    var status: String? = null,
    @SerializedName("message")
    var message: String? = null,
)