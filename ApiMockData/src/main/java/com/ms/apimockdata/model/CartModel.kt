package com.ms.apimockdata.model

import com.google.gson.annotations.SerializedName

data class CartModel(

    @SerializedName("cart_id")
    var cartId: Int? = null,

    @SerializedName("user_id")
    var userId: Int? = null,

    @SerializedName("items")
    var cartItems: ArrayList<CartItemModel> = arrayListOf()

)

data class CartItemModel (

    @SerializedName("product_id")
    var productId: Int? = null,

    @SerializedName("quantity")
    var quantity: Int? = null,

    @SerializedName("name")
    var name: String? = null,

    @SerializedName("price")
    var price: String? = null,

    @SerializedName("image")
    var image: String? = null,



)