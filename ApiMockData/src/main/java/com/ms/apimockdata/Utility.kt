package com.ms.apimockdata

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.ms.apimockdata.model.AddressModel
import com.ms.apimockdata.model.CartModel
import com.ms.apimockdata.model.NotificationItem
import com.ms.apimockdata.model.OrderDetailModel
import com.ms.apimockdata.model.OrderModel
import com.ms.apimockdata.model.ProductListModel
import com.ms.apimockdata.model.UserListModel

class Utility {
    val gson = Gson()

    fun getProductsList(context: Context): ArrayList<ProductListModel> {
        val jsonString = context.assets.open("GetProductList.json").bufferedReader().use {
            it.readText()
        }


        val listType = object : TypeToken<ArrayList<ProductListModel>>() {}.type
        return gson.fromJson(jsonString, listType)
    }

    fun getUsersList(context: Context): ArrayList<UserListModel> {
        val jsonString = context.assets.open("RegisteredUserList.json").bufferedReader().use {
            it.readText()
        }
        val listType = object : TypeToken<ArrayList<UserListModel>>() {}.type
        return gson.fromJson(jsonString, listType)
    }

    fun getCartItems(context: Context): ArrayList<CartModel> {
        val jsonString = context.assets.open("CartList.json").bufferedReader().use {
            it.readText()
        }
        val listType = object : TypeToken<ArrayList<CartModel>>() {}.type
        return gson.fromJson(jsonString, listType)
    }

    fun getOrderDetails(context: Context): ArrayList<OrderDetailModel> {
        val jsonString = context.assets.open("GetOrderDetails.json").bufferedReader().use {
            it.readText()
        }
        val listType = object : TypeToken<ArrayList<OrderDetailModel>>() {}.type
        return gson.fromJson(jsonString, listType)
    }

//    fun getOrderDetails(context: Context): OrderDetailModel {
//        val jsonString = context.assets.open("GetOrderDetails.json").bufferedReader().use {
//            it.readText()
//        }
//        val listType = object : TypeToken<OrderDetailModel>() {}.type
//        return gson.fromJson(jsonString, listType)
//    }

    fun getOrderPlacedByUser(context: Context): ArrayList<CartModel> {
        val jsonString = context.assets.open("GetOrdersPlacedByUsers.json").bufferedReader().use {
            it.readText()
        }
        val listType = object : TypeToken<ArrayList<CartModel>>() {}.type
        return gson.fromJson(jsonString, listType)
    }

    fun newOrderOrUpdateOder(context: Context): OrderModel {
        val jsonString = context.assets.open("NewOrderOrUpdateOrder.json").bufferedReader().use {
            it.readText()
        }
        val listType = object : TypeToken<OrderModel>() {}.type
        return  gson.fromJson(jsonString, listType)
    }

    fun getAddressDetails(context: Context): ArrayList<AddressModel> {
        val jsonString = context.assets.open("Address.json").bufferedReader().use {
            it.readText()
        }
        val listType = object : TypeToken<ArrayList<AddressModel>>() {}.type
        return gson.fromJson(jsonString, listType)
    }

    fun getNotificationList(context: Context): ArrayList<NotificationItem> {
        val jsonString = context.assets.open("Notification.json").bufferedReader().use {
            it.readText()
        }
        val listType = object : TypeToken<ArrayList<NotificationItem>>() {}.type
        return gson.fromJson(jsonString, listType)
    }
}