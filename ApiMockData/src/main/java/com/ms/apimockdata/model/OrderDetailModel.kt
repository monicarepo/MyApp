package com.ms.apimockdata.model

import com.google.gson.annotations.SerializedName

data class OrderDetailModel(
    @SerializedName("order_id")
    var orderId: Int? = null,
    @SerializedName("user_id")
    var userId: Int? = null,
    @SerializedName("status")
    var status: String? = null,
    @SerializedName("total_price")
    var totalPrice: Double? = null,
    @SerializedName("items")
    var cartItems: ArrayList<CartItemModel> = arrayListOf()
)
