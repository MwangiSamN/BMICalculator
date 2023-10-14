package com.example.bmicalculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.bmicalculator.ui.theme.BmiCalculatorTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BmiCalculatorTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    BmiCalculatorApp()
                }
            }
        }
    }
}


@Composable
fun BmiCalculatorApp(){
    OutlinedTextFields()
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OutlinedTextFields(){
    val outlinedText = remember{ mutableStateOf("") }
    val textField = remember { mutableStateOf("") }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            value = outlinedText.value,
            onValueChange = {outlinedText.value = it},
            label = { Text(
                text = "Weight",
                style = MaterialTheme.typography.labelLarge
            )},
            placeholder = { Text(
                text = "Enter your Weight",
                style = MaterialTheme.typography.labelLarge
            )},

        )
    }
}

@Preview(showBackground = true)
@Composable
fun BmiCalculatorPreview(){
    BmiCalculatorTheme {
        BmiCalculatorApp()
    }
}