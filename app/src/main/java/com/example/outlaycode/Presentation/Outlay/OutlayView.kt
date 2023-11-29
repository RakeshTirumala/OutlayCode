package com.example.outlaycode.Presentation.Outlay

import android.R
import android.content.Context
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AttachMoney
import androidx.compose.material.icons.filled.Category
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.outlaycode.ui.theme.opensans
import com.maxkeppeker.sheets.core.models.base.rememberSheetState
import com.maxkeppeler.sheets.calendar.CalendarDialog
import com.maxkeppeler.sheets.calendar.models.CalendarConfig
import com.maxkeppeler.sheets.calendar.models.CalendarSelection
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OutlayView(){
    val showDialog = remember{ mutableStateOf(false) }
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        floatingActionButton = {
            FloatingActionButton(onClick = {showDialog.value = true}) {
                Icon(Icons.Default.Add, contentDescription = null)
            }
        }
    ){innerPadding->
        Row(modifier = Modifier
            .height(100.dp)
            .padding(innerPadding)
            .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween) {
            CardForDate()
            DropDown()
        }
        Row(modifier = Modifier
            .height(100.dp)
            .padding(innerPadding)
            .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween){
            if(showDialog.value) CustomDialogExpense(value = "", setShowDialog = {showDialog.value=it})
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CardForDate(){
    val calendarState = rememberSheetState()
    var currentDate = LocalDate.now().toString().split("-")[2]
    var selectedDate by remember { mutableStateOf("${currentDate}") }
    val inputFormat = SimpleDateFormat("yyyy-MM-dd")
    val outputFormat = SimpleDateFormat("dd")

    CalendarDialog(
        state = calendarState,
        config = CalendarConfig(monthSelection = true, yearSelection = true ),
        selection = CalendarSelection.Date{ date ->
            selectedDate = date.toString()
            selectedDate = outputFormat.format(inputFormat.parse(selectedDate))
        })
    Card(
        modifier = Modifier
            .clickable { calendarState.show() }
            .padding(15.dp)
            .width(60.dp)
            .height(60.dp),
        elevation= CardDefaults.cardElevation(defaultElevation = 10.dp),
        shape = CircleShape){
        Text(
            text = selectedDate,
            fontFamily = opensans,
            fontWeight = FontWeight.Bold,
            fontStyle = FontStyle.Normal,
            fontSize = 16.sp,
            modifier = Modifier
                .background(MaterialTheme.colorScheme.primary)
                .padding(20.dp),
            color = MaterialTheme.colorScheme.onPrimary
        )
    }
}

@Composable
fun DropDown(){
    var expanded by remember { mutableStateOf(false) }
    val items = listOf("Month", "Overall")
    var selectedIndex by remember { mutableStateOf(0) }

    Box(modifier = Modifier
        .padding(top = 20.dp, end = 15.dp)
        .width(110.dp)
        .height(50.dp)
        .clip(RoundedCornerShape(5.dp))
        .border(1.dp, Color(MaterialTheme.colorScheme.primary.toArgb()))
        .background(MaterialTheme.colorScheme.primary)) {
        Text(
            fontFamily = opensans,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            text = items[selectedIndex],
            color = MaterialTheme.colorScheme.onPrimary,
            modifier = Modifier
                .width(150.dp)
                .height(50.dp)
                .clickable(onClick = { expanded = true })
                .padding(start = 20.dp, end = 20.dp, top = 15.dp))
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = {expanded=false}
            ) {
            items.forEachIndexed{index, item ->
                DropdownMenuItem(
                    text = { Text(text = item) },
                    onClick = {
                        selectedIndex=index
                        expanded = false
                    }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomDialogExpense(value:String, setShowDialog:(Boolean)->Unit){
    val expenseInUSD = remember{ mutableStateOf(value) }
    val expenseInUSDError = remember { mutableStateOf("") }
    val category = remember { mutableStateOf((value)) }
    val categoryError =  remember { mutableStateOf("") }
//    val txtFieldError = remember { mutableStateOf("") }
//    val txtField = remember { mutableStateOf(value) }

    Dialog(onDismissRequest = {setShowDialog(false)}) {
        Surface(
            shape = RoundedCornerShape(10.dp),
            color = Color.White,
            modifier = Modifier
                .fillMaxWidth()
                .height(400.dp)
                .border(width = 2.dp, color = MaterialTheme.colorScheme.primary, shape = RoundedCornerShape(10.dp))){
        }
        Box(modifier = Modifier
            .fillMaxWidth()
            .height(400.dp)
            .padding(20.dp)){
            Column{
                Row(modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center){
                    Text(
                        text = "Expense",
                        fontFamily = opensans,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black)
                }
                Spacer(modifier = Modifier.height(40.dp))
                Row{
                    TextField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .border(
                                BorderStroke(
                                    width = 2.dp,
                                    color = colorResource(id = if (expenseInUSDError.value.isEmpty()) R.color.black else R.color.holo_red_dark)
                                ),
                                shape = RoundedCornerShape(50)
                            ),
                        colors = TextFieldDefaults.textFieldColors(
                            containerColor = Color.Transparent,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            textColor = Color.Black
                        ),
                        leadingIcon = { Spacer(modifier = Modifier.height(20.dp))
                            Icon(
                                imageVector = Icons.Filled.AttachMoney,
                                contentDescription = "",
                                tint = colorResource(R.color.black),
                                modifier = Modifier
                                    .width(20.dp)
                                    .height(20.dp)
                            )
                        },
                        placeholder = { Text(text = "Enter value", color = Color.Gray) },
                        value = expenseInUSD.value,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        onValueChange = {
                            expenseInUSD.value = it.take(10)
                        })
                }
                Spacer(modifier=Modifier.height(25.dp))
                Row{
                    TextField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .border(
                                BorderStroke(
                                    width = 2.dp,
                                    color = colorResource(id = if (categoryError.value.isEmpty()) R.color.black else R.color.holo_red_dark)
                                ),
                                shape = RoundedCornerShape(50)
                            ),
                        colors = TextFieldDefaults.textFieldColors(
                            containerColor = Color.Transparent,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            textColor = Color.Black
                        ),
                        leadingIcon = { Spacer(modifier = Modifier.height(20.dp))
                            Icon(
                                imageVector = Icons.Filled.Category,
                                contentDescription = "",
                                tint = colorResource(R.color.black),
                                modifier = Modifier
                                    .width(20.dp)
                                    .height(20.dp)
                            )
                        },
                        placeholder = { Text(text = "Enter Category", color = Color.Gray) },
                        value = category.value,
                        onValueChange = {
                            category.value = it.take(10)
                        })
                }
                Spacer(modifier=Modifier.height(50.dp))
                Button(
                    onClick = {},
                    colors= ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    shape = RoundedCornerShape(20.dp)
                ) {
                    Text(
                        text = "Upload",
                        color = MaterialTheme.colorScheme.onPrimary,
                        fontFamily = opensans,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp)
                }
            }
        }
    }
}

fun handleToast(current: Context) {
    Toast.makeText(current, "You clicked me?", Toast.LENGTH_SHORT).show()
}

