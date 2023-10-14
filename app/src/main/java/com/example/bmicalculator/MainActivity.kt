package com.example.bmicalculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
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

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(40.dp)
    ) {
        Spacer(modifier = Modifier.size(60.dp))
        TopText()
        OutlinedTextFields()
        CalculateBtn {

        }
    }
}

@Composable
fun TopText(){
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(R.string.app_name),
            style = MaterialTheme.typography.displayLarge
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OutlinedTextFields(){
    val weightOutlinedText = remember{ mutableStateOf("") }
    val heightOutlinedText = remember{ mutableStateOf("") }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val focusManager = LocalFocusManager.current

        OutlinedTextField(
            value = weightOutlinedText.value,
            onValueChange = {weightOutlinedText.value = it},
            label = { Text(
                text = "Weight",
                style = MaterialTheme.typography.labelLarge
            )},
            placeholder = { Text(
                text = "Enter your Weight",
                style = MaterialTheme.typography.labelLarge
            )},
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Next
            ),
            keyboardActions = KeyboardActions(
                onNext = { focusManager.moveFocus(FocusDirection.Down) }
            ),
            shape = MaterialTheme.shapes.large,
            modifier = Modifier.width(370.dp)
        )

        Spacer(modifier = Modifier.size(20.dp))
        OutlinedTextField(
            value = heightOutlinedText.value,
            onValueChange = {heightOutlinedText.value = it},
            label = { Text(
                text = "Height",
                style = MaterialTheme.typography.labelLarge
            )},
            placeholder = { Text(
                text = "Enter your Height",
                style = MaterialTheme.typography.labelLarge
            )},
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            keyboardActions = KeyboardActions(
                onDone = { focusManager.clearFocus() }
            ),
            shape = MaterialTheme.shapes.large,
            modifier = Modifier.width(370.dp)
        )
    }
}

@Composable
fun CalculateBtn(onClick: () -> Unit) {
    Button(
        onClick = { onClick() },
        modifier = Modifier.height(40.dp).width(300.dp)
    ) {
        Text("Calculate")
    }
}

@Preview(showBackground = true)
@Composable
fun BmiCalculatorPreview(){
    BmiCalculatorTheme {
        BmiCalculatorApp()
    }
}