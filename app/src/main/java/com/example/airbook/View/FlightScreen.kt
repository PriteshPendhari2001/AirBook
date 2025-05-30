package com.example.airbook.View

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.airbook.Model.FlightTicket
import com.example.airbook.Model.ticketList
import com.example.airbook.R
import com.example.airbook.ui.theme.antonFontFamily
import com.example.airbook.ui.theme.skyblue
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore


@Composable
fun FlightScreen() {
    val allTickets = remember { ticketList }
    var selectedAirline by remember { mutableStateOf("All Airlines") }

    val airlineOptions = listOf("All Airlines") + allTickets.map { it.airline }.distinct()
    val filteredTickets = if (selectedAirline == "All Airlines") {
        allTickets
    } else {
        allTickets.filter { it.airline == selectedAirline }
    }

    // Get Firestore instance
    val db = Firebase.firestore
    val context = LocalContext.current

    Text("Recommended Flights",
        modifier = Modifier.padding(start = 20.dp),
        color = Color.Black,
        fontSize = 25.sp,
        fontWeight = FontWeight.Bold,
        fontFamily = antonFontFamily,
    )

    Column {
        AirlineFilterDropdown(
            selectedAirline = selectedAirline,
            onAirlineSelected = { selectedAirline = it },
            airlineOptions = airlineOptions
        )

        TicketList(tickets = filteredTickets)
    }
}



//Dropdown for selecting Multiple Airlines
@Composable
fun AirlineFilterDropdown(
    selectedAirline: String,
    onAirlineSelected: (String) -> Unit,
    airlineOptions: List<String>
) {
    var expanded by remember { mutableStateOf(false) }

    Box(modifier = Modifier
        .padding(16.dp)
        .fillMaxWidth()) {
        OutlinedButton(
            onClick = { expanded = true },
            border = BorderStroke(3.dp, skyblue),
            contentPadding = PaddingValues(horizontal = 24.dp, vertical = 12.dp),
            colors = ButtonDefaults.outlinedButtonColors(
                contentColor = Color.Black
            )
        ) {
            Text(selectedAirline)
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            airlineOptions.forEach { airline ->
                DropdownMenuItem(onClick = {
                    onAirlineSelected(airline)
                    expanded = false
                }) {
                    Text(airline)
                }
            }
        }
    }
}


//Recommendation flight tickets Cards (Lazy Cards)
@Composable
fun TicketList(tickets: List<FlightTicket>) {
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(tickets) { ticket ->
            FlightTicketCard(
                departureTime = ticket.departureTime,
                departureAirport = ticket.departureAirport,
                arrivalTime = ticket.arrivalTime,
                arrivalAirport = ticket.arrivalAirport,
                duration = ticket.duration,
                airline = ticket.airline,
                price = ticket.price
            )
        }
    }
}

@Composable
fun FlightTicketCard(
    modifier: Modifier = Modifier,
    departureTime: String = "02:00 PM",
    departureAirport: String = "BOM",
    arrivalTime: String = "11:00 PM",
    arrivalAirport: String = "UAE",
    duration: String = "9 Hours",
    airline: String = "EMIRATES",
    price: String = "$1,800"
) {
    Card(
        shape = RoundedCornerShape(24.dp),
        backgroundColor = Color(0xFFFFFCF4),
        elevation = 6.dp,
        modifier = modifier
            .padding(16.dp)
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        Column(
            Modifier.padding(16.dp)
        ) {
            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(horizontalAlignment = Alignment.Start) {
                    Text(departureTime, fontWeight = FontWeight.Bold)
                    Text(departureAirport, fontSize = 12.sp)
                }

                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(painter = painterResource(R.drawable.round_airplanemode_active_24), tint = Color.Black, contentDescription = null)
                    Text(duration, fontSize = 12.sp)
                }

                Column(horizontalAlignment = Alignment.End) {
                    Text(arrivalTime, fontWeight = FontWeight.Bold)
                    Text(arrivalAirport, fontSize = 12.sp)
                }
            }

            Spacer(Modifier.height(12.dp))

            Divider(
                color = Color.Black,
                thickness = 1.dp,
                modifier = Modifier
                    .fillMaxWidth()
                    .drawWithContent {
                        val dashWidth = 10f
                        val dashGap = 10f
                        val lineHeight = size.height

                        for (x in 0 until size.width.toInt() step (dashWidth + dashGap).toInt()) {
                            drawLine(
                                color = Color.Black,
                                start = Offset(x.toFloat(), lineHeight / 2),
                                end = Offset(x + dashWidth, lineHeight / 2),
                                strokeWidth = 2f
                            )
                        }
                    }
            )

            Spacer(Modifier.height(12.dp))

            Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()) {
                Text(airline, fontWeight = FontWeight.Bold, fontSize = 20.sp)
                Text(price, fontWeight = FontWeight.Bold, fontSize = 16.sp)
            }

            androidx.compose.material.Button(
                onClick = { /* Booking action here */ },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 12.dp),
                colors = androidx.compose.material.ButtonDefaults.buttonColors(backgroundColor = Color(0xFF001F91))
            ) {
                Text("Book Now", color = Color.White)
            }
        }
    }
}
