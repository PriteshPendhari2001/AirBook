package com.example.airbook.View

import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.airbook.Model.BordingPass
import com.example.airbook.R
import com.example.airbook.ui.theme.skyblue
import com.example.airbook.ui.theme.waterBlue
import com.google.firebase.Firebase
import com.google.firebase.Timestamp
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Date


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun FlightReservationScreen() {

    var from by remember { mutableStateOf("PHL CRK") }
    var to by remember { mutableStateOf("JPN TYK") }
    var passengers by remember { mutableStateOf("01") }


    //From
    val airports2 = listOf("DEL", "BOM", "BLR", "HYD", "MAA", "CCU", "AMD", "VIZ", "IXU", "BHO",
        "PNQ", "LKO", "STV", "IDR", "ATQ")

    //To
    val airports = listOf("PHL CRK", "NYC JFK", "DXB", "JPN TKY", "BKK TH", "DEL IN", "ICN KR",
        "SIN SG", "LHR UK", "CDG FR", "FRA DE", "AMS NL", "MAD ES",)



    val passengerOptions = (1..5).map { it.toString().padStart(2, '0') }

    val dateFormatter = remember { DateTimeFormatter.ofPattern("MM dd") }
    val today = remember { LocalDate.now() }


    var departureDate by remember { mutableStateOf(today) }
    var returnDate by remember { mutableStateOf(today.plusDays(30)) }


    var showDeparturePicker by remember { mutableStateOf(false) }
    var showReturnPicker by remember { mutableStateOf(false) }


    // Get current user ID
    val UserId = Firebase.auth.currentUser?.uid ?: ""

    // Get Firestore instance
    val db = Firebase.firestore
    val context = LocalContext.current

    var showConfirmationDialog by remember { mutableStateOf(false) }

    fun reserveFlight() {
        val departureTimestamp = Timestamp(
            Date.from(
                departureDate.atStartOfDay(ZoneId.systemDefault()).toInstant()
            )
        )

        val returnTimestamp = Timestamp(
            Date.from(returnDate.atStartOfDay(ZoneId.systemDefault()).toInstant())
        )

        // Create boarding pass object
        val bordingPass = BordingPass(
            userId = UserId,
            from = from,
            to = to,
            passengers = passengers,
            departureDate = departureTimestamp,
            returnDate = returnTimestamp

        )

        // Add to Firestore
        db.collection("boarding_passes")
            .add(bordingPass)
            .addOnCompleteListener {
                Toast.makeText(context, "Flight reserved successfully!", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener { e ->
                Toast.makeText(context, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }


    Column(
        modifier = Modifier
            .padding(16.dp)
            .background(Color.White, RoundedCornerShape(24.dp))
            .border(1.dp, Color.LightGray, RoundedCornerShape(24.dp))
            .padding(16.dp),
    ) {
        Text("Boarding Pass", fontWeight = FontWeight.SemiBold)

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {

            //First Dropdown
            DropdownField(label = "From", value = from, options = airports2) {
                from = it
            }

            Icon(
                painter = painterResource(R.drawable.swap),
                modifier = Modifier.height(30.dp).width(30.dp),
                contentDescription = null,
                tint = waterBlue
            )

            //Second Dropdown
            DropdownField(label = "To", value = to, options = airports) {
                to = it
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {

            DateField(label = "Departure", date = departureDate.format(dateFormatter)) {
                showDeparturePicker = true
            }

            DateField(label = "Return", date = returnDate.format(dateFormatter)) {
                showReturnPicker = true
            }

            DropdownField(label = "Passenger", value = passengers, options = passengerOptions) {
                passengers = it
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = {
                /* Reserve action */
                showConfirmationDialog = true

            },
            modifier = Modifier.fillMaxWidth().height(50.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF000080)),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text("Reserve Flight", color = Color.White, fontSize = 16.sp)
        }


        // Confirmation Dialog (for reservation)
        if (showConfirmationDialog) {
            AlertDialog(
                onDismissRequest = { showConfirmationDialog = false },
                title = { Text("Confirm Reservation") },
                text = {
                    Column {
                        Text("From: $from", modifier = Modifier.padding(4.dp))
                        Text("To: $to", modifier = Modifier.padding(4.dp))
                        Text("Passengers: ${passengers.toInt()}", modifier = Modifier.padding(4.dp))
                        Text("Departure: ${departureDate.format(DateTimeFormatter.ISO_DATE)}",
                            modifier = Modifier.padding(4.dp))
                        Text("Return: ${returnDate.format(DateTimeFormatter.ISO_DATE)}",
                            modifier = Modifier.padding(4.dp))
                    }
                },

                confirmButton = {
                    Button(
                        onClick = {
                            // CALL reserveFlight here when user click ReserveFlight button
                            reserveFlight()
                            showConfirmationDialog = false
                        }
                    ) {
                        Text("Confirm")
                    }
                },

                dismissButton = {
                    OutlinedButton(
                        onClick = { showConfirmationDialog = false }
                    ) {
                        Text("Cancel")
                    }
                }
            )
        }


    }

    if (showDeparturePicker) {
        DatePickerDailog(
            onDismissRequest = { showDeparturePicker = false },
            onDateChange = {
                departureDate = it
                showDeparturePicker = false
            }
        )
    }

    if (showReturnPicker) {
        DatePickerDailog(
            onDismissRequest = { showReturnPicker = false },
            onDateChange = {
                returnDate = it
                showReturnPicker = false
            }
        )
    }
}


//DropdownField
@Composable
fun DropdownField(
    label: String,
    value: String,
    options: List<String>,
    Onselect: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Column(modifier = Modifier.width(100.dp).clickable { expanded = true }.padding(8.dp)) {

        Text(label, fontSize = 12.sp, color = Color.Gray)

        Spacer(modifier = Modifier.height(4.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {

            Text(value, fontWeight = FontWeight.Bold)
            Icon(Icons.Default.ArrowDropDown, contentDescription = null, tint = skyblue)

        }

        DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            options.forEach {
                DropdownMenuItem(onClick = {
                    Onselect(it)
                    expanded = false
                }, text = { Text(it) })
            }
        }
    }
}

//Date Field
@Composable
fun DateField(label: String, date: String, onClick: () -> Unit) {
    Column(modifier = Modifier.width(100.dp).clickable { onClick() }) {

        Text(label, fontSize = 12.sp, color = Color.Gray)

        Row(verticalAlignment = Alignment.CenterVertically) {

            Text(date, fontWeight = FontWeight.Bold)

            Icon(Icons.Default.ArrowDropDown, contentDescription = null, tint = skyblue)

        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerDailog(onDismissRequest: () -> Unit, onDateChange: (LocalDate) -> Unit) {

    val datePickerState = rememberDatePickerState()

    DatePickerDialog(
        onDismissRequest = onDismissRequest,
        confirmButton = {
            TextButton(onClick = {
                datePickerState.selectedDateMillis?.let {
                    val selectedDate = Instant.ofEpochMilli(it)
                        .atZone(ZoneId.systemDefault())
                        .toLocalDate()
                    onDateChange(selectedDate)
                }
            }) {
                Text("Ok")
            }
        },
        dismissButton = {
            TextButton(onClick = { onDismissRequest }) {
                Text("Cancel")
            }
        }
    ) {
        DatePicker(state = datePickerState)
    }
}
