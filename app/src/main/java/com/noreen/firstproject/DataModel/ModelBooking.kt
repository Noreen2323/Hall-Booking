package com.noreen.firstproject.DataModel
data class ModelBooking(
    val id: String,
    val email: String,
    val roomName: String,
    val date: String,
    val time:String,
    var status:String="Pending",
    )