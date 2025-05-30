package com.example.airbook.Model

import com.google.firebase.Timestamp

data class BordingPass(

    val userId: String = "",
    val from : String = "",
    val to : String = "",
    val passengers : String = "",
    val departureDate: Timestamp = Timestamp.now(),
    val returnDate: Timestamp = Timestamp.now()
)