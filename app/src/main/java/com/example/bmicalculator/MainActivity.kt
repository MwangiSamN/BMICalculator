package com.example.bmicalculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Calculate
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.ParagraphStyle
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.bmicalculator.ui.theme.BmiCalculatorTheme
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BmiCalculatorTheme {

                val navController: NavHostController = rememberNavController()
                val bottomBarHeight = 56.dp
                val bottomBarOffsetHeightPx = remember { mutableStateOf(0f) }

                var buttonsVisible = remember { mutableStateOf(true) }

                Scaffold(
                    bottomBar = {
                        BottomBar(
                            navController = navController,
                            state = buttonsVisible,
                            modifier = Modifier
                        )
                    }) { paddingValues ->
                    Box(
                        modifier = Modifier.padding(paddingValues)
                    ) {
                        NavigationGraph(navController = navController)
                    }
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
        Spacer(modifier = Modifier.size(50.dp))
        TopText()
        OutlinedTextFields()
        CalculateBtn {

        }
        Spacer(modifier = Modifier.size(40.dp))
        TableScreen()

    }
}

@Composable
fun TopText(){
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(R.string.app_name),
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Bold
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
            modifier = Modifier.width(370.dp),
        )
    }
}

@Composable
fun CalculateBtn(onClick: () -> Unit) {
    Button(
        onClick = { onClick() },
        modifier = Modifier
            .height(40.dp)
            .width(300.dp)
    ) {
        Text("Calculate")
    }
}

@Composable
fun TableScreen() {
    // Just a fake data... a Pair of Int and String

    // Each cell of a column must have the same weight.
    val column1Weight = .3f // 30%
    val column2Weight = .7f // 70%
    // The LazyColumn will be our table. Notice the use of the weights below
    LazyColumn(
        Modifier
            .fillMaxSize()
            .padding(dimensionResource(id = R.dimen.padding_medium))) {
        // Here is the header
        item {
            Row(Modifier.background(MaterialTheme.colorScheme.primary)) {
                TableCell(text = stringResource(R.string.bmi), weight = column1Weight)
                TableCell(text = stringResource(R.string.weight_status), weight = column2Weight)
            }
            Row(Modifier.fillMaxWidth()) {
                TableCell(text = stringResource(R.string.under_18_5), weight = column1Weight)
                TableCell(text = stringResource(R.string.underweight), weight = column2Weight)
            }
            Row(Modifier.fillMaxWidth()) {
                TableCell(text = stringResource(R.string.under_24_9), weight = column1Weight)
                TableCell(text = stringResource(R.string.normal), weight = column2Weight)
            }
            Row(Modifier.fillMaxWidth()) {
                TableCell(text = stringResource(R.string.under_29_9), weight = column1Weight)
                TableCell(text = stringResource(R.string.overweight), weight = column2Weight)
            }
            Row(Modifier.fillMaxWidth()) {
                TableCell(text = stringResource(R.string.over_30), weight = column1Weight)
                TableCell(text = stringResource(R.string.Obese), weight = column2Weight)
            }
        }
        // Here are all the lines of your table.

    }
}

@Composable
fun RowScope.TableCell(
    text: String,
    weight: Float
) {
    Text(
        text = text,
        Modifier
            .border(1.dp, Color.Black)
            .weight(weight)
            .padding(8.dp)
    )
}

sealed class Destinations(
    val route: String,
    val title: String? = null,
    val icon: ImageVector? = null
) {
    object BmiCalculator : Destinations(
        route = "calculate_screen",
        title = "Home",
        icon = Icons.Outlined.Calculate
    )

    object Information : Destinations(
        route = "info_screen",
        title = "Info",
        icon = Icons.Outlined.Info
    )

}

@Composable
fun NavigationGraph(navController: NavHostController) {
    NavHost(navController, startDestination = Destinations.BmiCalculator.route) {
        composable(Destinations.BmiCalculator.route) {
            BmiCalculatorApp()
        }
        composable(Destinations.Information.route) {
            InfoScreen()
        }
    }
}

@Composable
fun BottomBar(
    navController: NavHostController, state: MutableState<Boolean>, modifier: Modifier = Modifier
) {
    val screens = listOf(
        Destinations.BmiCalculator, Destinations.Information
    )

    NavigationBar(
        modifier = modifier
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        screens.forEach { screen ->

            NavigationBarItem(
                label = {
                    Text(text = screen.title!!)
                },
                icon = {
                    Icon(imageVector = screen.icon!!, contentDescription = "")
                },
                selected = currentRoute == screen.route,
                onClick = {
                    navController.navigate(screen.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
            )
        }
    }

}

@Composable
fun InfoScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(dimensionResource(id = R.dimen.padding_medium))
    ) {
        Text(
            buildAnnotatedString {
                withStyle(style = ParagraphStyle(lineHeight = 30.sp)) {
                    withStyle(
                        style = SpanStyle(
                            fontWeight = FontWeight.Bold
                        )
                    ) {
                        append("What is the body mass index (BMI)?\n")
                    }
                    append("The body mass index (BMI) is a measure that uses your height and weight to work out if your weight is healthy.\n" +
                            "\n" +
                            "The BMI calculation divides an adult's weight in kilograms by their height in metres squared. For example, A BMI of 25 means 25kg/m2.\n\n")
                    withStyle(
                        style = SpanStyle(
                            fontWeight = FontWeight.Bold,
                        )
                    ) {
                        append("Accuracy of BMI\n")
                    }
                    append("BMI takes into account natural variations in body shape, giving a healthy weight range for a particular height.\n" +
                            "\n" +
                            "As well as measuring your BMI, healthcare professionals may take other factors into account when assessing if you're a healthy weight.\n" +
                            "\n" +
                            "Muscle is much denser than fat, so very muscular people, such as heavyweight boxers, weight trainers and athletes, may be a healthy weight even though their BMI is classed as obese.\n")

                }
            }
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