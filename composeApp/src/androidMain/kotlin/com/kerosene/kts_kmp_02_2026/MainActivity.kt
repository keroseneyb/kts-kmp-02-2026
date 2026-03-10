package com.kerosene.kts_kmp_02_2026

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.fragment.app.FragmentActivity
import com.kerosene.core.auth.util.ActivityProvider
import com.kerosene.kts_kmp_02_2026.app.App

class MainActivity : FragmentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ActivityProvider.currentActivity = this
        enableEdgeToEdge()
        setContent {
            App()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (ActivityProvider.currentActivity == this) {
            ActivityProvider.currentActivity = null
        }
    }
}