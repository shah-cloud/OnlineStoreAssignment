package com.knoldus.dashboardservice.data.model

case class CartSchema(userId: Int, itemNo: Int,
                      itemName: String,
                      itemDetail: String,
                      rating: Double,
                      price: Double,
                      vendorName: String,
                      vendorContact: Long,
                      itemCategory: String,
                      quantity: Int)
