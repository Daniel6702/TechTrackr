package com.example.techtrackr

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.techtrackr.data.remote.api.Client
import com.example.techtrackr.ui.theme.TechTrackrTheme
import org.json.JSONObject
import java.io.IOException

class MainActivity : ComponentActivity() {

    private val apiClient = Client


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



        Thread {
            try {
                val topCategories: JSONObject? = apiClient.getTopCategories()
                Log.d("MainActivity", "Top Categories: $topCategories")
            } catch (e: IOException) {
                Log.e("MainActivity", "Error fetching top categories: ${e.message}")
            } catch (e: Exception) {
                Log.e("MainActivity", "Unexpected error: ${e.message}", e)
            }
        }.start()



        enableEdgeToEdge()
        setContent {
            TechTrackrTheme {
                Scaffold( modifier = Modifier.fillMaxSize() ) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    TechTrackrTheme {
        Greeting("Android")
    }
}