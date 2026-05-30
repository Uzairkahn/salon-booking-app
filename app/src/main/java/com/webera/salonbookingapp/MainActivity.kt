package com.webera.salonbookingapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.webera.salonbookingapp.navigation.AppNavigation
import com.webera.salonbookingapp.ui.theme.SalonBookingAppTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            SalonBookingAppTheme {
                AppNavigation()
            }
        }
    }
}