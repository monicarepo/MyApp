package com.ms.apimockdata.model

import com.example.example.ReviewsModel
import com.google.gson.annotations.SerializedName

data class ProductListModel (
  @SerializedName("product_id")
  var productId: Int? = null,
  @SerializedName("name")
  var name: String? = null,
  @SerializedName("description")
  var description: String? = null,
  @SerializedName("price")
  var price: Double? = null,
  @SerializedName("unit")
  var unit: String? = null,
  @SerializedName("image")
  var image: String? = null,
  @SerializedName("discount")
  var discount: Int? = null,
  @SerializedName("availability")
  var availability: Boolean? = null,
  @SerializedName("brand")
  var brand: String? = null,
  @SerializedName("category")
  var category: String? = null,
  @SerializedName("rating")
  var rating: Double? = null,
  @SerializedName("reviews")
  var reviews: ArrayList<ReviewsModel> = arrayListOf()
)