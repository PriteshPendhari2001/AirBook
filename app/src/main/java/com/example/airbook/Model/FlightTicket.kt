package com.example.airbook.Model


data class FlightTicket(
    val departureTime: String,
    val departureAirport: String,
    val arrivalTime: String,
    val arrivalAirport: String,
    val duration: String,
    val airline: String,
    val price: String
)

val ticketList = listOf(
    FlightTicket("02:00 PM", "IND BOM", "11:00 PM", "UAE", "9 Hours", "EMIRATES", "$1,800"),
    FlightTicket("06:30 AM", "IND DEL", "08:45 PM", "LHR UK", "12 Hours", "BRITISH AIRWAYS", "$2,200"),
    FlightTicket("01:15 AM", "IND PNQ", "10:00 AM", "ICN KR", "8.5 Hours", "KOREAN AIR", "$1,900"),
    FlightTicket("09:45 AM", "IND HYD", "05:30 PM", "SIN SG", "7 Hours", "SINGAPORE AIR", "$900")
)

