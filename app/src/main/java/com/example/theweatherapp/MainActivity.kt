package com.example.theweatherapp

import android.graphics.drawable.Icon
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.theweatherapp.ui.theme.BlueJC
import com.example.theweatherapp.ui.theme.DarkBlueJC
import com.example.theweatherapp.ui.theme.TheWeatherAppTheme
import java.nio.file.WatchEvent

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
        setContent {
            WeatherScreen()
        }
    }
}


@Composable
fun WeatherScreen(modifier: Modifier = Modifier) {
    // ViewModel things
    val viewModel:WeatherViewModel = viewModel()
    val weatherData by viewModel.weatherData.collectAsState()

    // City name and api key
    var city by remember { mutableStateOf("") }
    val apiKey = "260a01a2922ef4f046bb440849aa7067"

    Box(
        modifier = Modifier
            .fillMaxSize()
//            .height(200.dp)
            .paint(
                painterResource(id = R.drawable.bg),
                contentScale = ContentScale.FillWidth,
                alignment = Alignment.TopStart
            )
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {

            Spacer(modifier = Modifier.height(185.dp))

            OutlinedTextField(
                value = city,
                onValueChange = {city = it},
                singleLine = true,
                label = {Text("City")},
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(30.dp),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                    unfocusedIndicatorColor = BlueJC,
                    focusedIndicatorColor = DarkBlueJC,
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {viewModel.fetchWeather(city,apiKey)},
                colors = ButtonDefaults.buttonColors(BlueJC)
            ) {
                Text("Check Weather")
            }

            Spacer(modifier = Modifier.height(16.dp))

            weatherData?.let {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.SpaceEvenly
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {

                        WeatherCard(label = "City", value = it.name, icon = Icons.Default.Place)
                        WeatherCard(
                            label = "Temperature",
                            value = "${it.main.temp}°C",
                            icon = Icons.Default.Star
                        )
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {

                        WeatherCard(
                            label = "Humidity",
                            value = "${it.main.humidity} %",
                            icon = Icons.Default.Warning
                        )

                    }
                }
            }
            
        }
    }



}


@Composable
fun WeatherCard(
    label: String,
    value: String,
    icon: ImageVector,
    modifier: Modifier = Modifier
) {

    Card(
        modifier = Modifier
            .padding(8.dp)
            .size(150.dp),
        colors = CardDefaults.cardColors(Color.White),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {

        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Top
        ) {

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {

                androidx.compose.material3.Icon(
                    imageVector = icon,
                    contentDescription = "Icons",
                    tint = DarkBlueJC,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = label,
                    fontSize = 14.sp,
                    color = DarkBlueJC
                )

                Spacer(modifier = Modifier.height(4.dp))

            }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = value,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    color = BlueJC
                )
            }
        }
    }
}

@Preview
@Composable
fun CardPreview(modifier: Modifier = Modifier) {
    WeatherCard("Delhi","@5", icon = Icons.Default.Person)
}
@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    TheWeatherAppTheme {
        Column {
            WeatherScreen()

        }

    }


}