package com.ms.ecommerceapp.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.ms.apimockdata.Utility
import com.ms.apimockdata.model.CartModel
import com.ms.apimockdata.model.OrderDetailModel
import com.ms.apimockdata.model.OrderModel
import com.ms.apimockdata.model.ProductListModel
import com.ms.apimockdata.model.UserListModel
import com.ms.ecommerceapp.screens.MainScreen

class HomeActivity: ComponentActivity() {
    lateinit var apiMockUtility: Utility
    var productList = ArrayList<ProductListModel>()
    var usersList = ArrayList<UserListModel>()
    var cartItems = ArrayList<CartModel>()
    var orderDetails = ArrayList<OrderDetailModel>()
    var orderDetailByUser = ArrayList<CartModel>()
    var newOrder = OrderModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        apiMockUtility = Utility()
//        initialSetup()
        setContent {
            MainScreen()
        }
    }

    private fun initialSetup() {
        getProductList()
        getUserList()
        getCartItems()
        getOrderDetails()
        getOrderPlacedByUser()
        newOrderOrUpdateOder()
    }

    private fun getProductList() {
       productList = apiMockUtility.getProductsList(this)
        println("getProductDetails:${productList[0].name}")
    }

    private fun getUserList() {
        usersList = apiMockUtility.getUsersList(this)
        println("getUsersList: ${usersList[0].userName}")
    }

    private fun getCartItems() {
        cartItems = apiMockUtility.getCartItems(this)
        println("getCartItems: ${cartItems[0].cartItems}")
    }

    private fun getOrderDetails() {
        orderDetails = apiMockUtility.getOrderDetails(this)
        println("getOrderDetails: ${orderDetails[0].totalPrice}")
    }

    private fun getOrderPlacedByUser() {
        orderDetailByUser = apiMockUtility.getOrderPlacedByUser(this)
        println("getOrderPlacedByUser: ${orderDetailByUser[0].userId }")
    }

    private fun newOrderOrUpdateOder() {
        newOrder = apiMockUtility.newOrderOrUpdateOder(this)
        println("newOrderOrUpdateOder: ${newOrder.message}")
    }

}