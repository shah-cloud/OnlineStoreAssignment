package com.knoldus.onlinestoreservice.model

case class Item(
  itemNo: Int,
  itemName: String,
  itemDetail: String,
  rating: Double,
  price: Double,
  vendorName: String,
  vendorContact: Long,
  itemCategory: String)
