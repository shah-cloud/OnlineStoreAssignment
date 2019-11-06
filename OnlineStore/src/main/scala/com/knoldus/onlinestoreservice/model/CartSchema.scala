package com.knoldus.onlinestoreservice.model

case class CartSchema(
  userId: Int,
  itemNo: Int,
  itemName: String,
  itemDetail: String,
  rating: Double,
  price: Double,
  vendorName: String,
  vendorContact: Long,
  itemCategory: String,
  quantity: Int)
