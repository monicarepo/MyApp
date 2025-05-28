package com.example.example

import com.google.gson.annotations.SerializedName


data class ReviewsModel (

  @SerializedName("user_id" ) var userId  : Int?    = null,
  @SerializedName("rating"  ) var rating  : Int?    = null,
  @SerializedName("comment" ) var comment : String? = null

)