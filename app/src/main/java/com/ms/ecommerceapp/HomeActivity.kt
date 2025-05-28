package com.ms.ecommerceapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ms.apimockdata.Utility
import com.ms.apimockdata.model.CartModel
import com.ms.apimockdata.model.OrderDetailModel
import com.ms.apimockdata.model.OrderModel
import com.ms.apimockdata.model.ProductListModel
import com.ms.apimockdata.model.UserListModel

class HomeActivity: AppCompatActivity() {
    lateinit var apiMockUtility: Utility
    var productList = ArrayList<ProductListModel>()
    var usersList = ArrayList<UserListModel>()
    var cartItems = ArrayList<CartModel>()
    var orderDetails = OrderDetailModel()
    var orderDetailByUser = ArrayList<CartModel>()
    var newOrder = OrderModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        apiMockUtility = Utility()
        initialSetup()
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
        println("getOrderDetails: ${orderDetails.totalPrice}")
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