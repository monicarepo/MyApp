package com.ms.apimockdata.model

data class AddressModel(
    val id: Int,
    val name: String,
    val phone: String,
    val addressLine: String,
    val city: String,
    val zip: String
)