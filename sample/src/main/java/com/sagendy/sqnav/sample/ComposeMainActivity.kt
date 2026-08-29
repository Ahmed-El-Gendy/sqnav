package com.sagendy.sqnav.sample

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import com.sagendy.sqnav.SqNav
import com.sagendy.sqnav.SqNavItem

class ComposeMainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SqNavComposeDemo()
        }
    }
}

@Composable
fun SqNavComposeDemo() {
    var selectedTab by remember { mutableIntStateOf(1) }
    val tabName = when (selectedTab) {
        1 -> "Home"
        2 -> "Search"
        3 -> "Downloads"
        else -> "Profile"
    }

    Scaffold(
        bottomBar = {
            AndroidView(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(72.dp),
                factory = { context ->
                    SqNav(context).apply {
                        addItem(SqNavItem(1, "Home", R.drawable.ic_home))
                        addItem(SqNavItem(2, "Search", R.drawable.ic_search))
                        addItem(SqNavItem(3, "Downloads", R.drawable.ic_downloads))
                        addItem(SqNavItem(4, "Profile", R.drawable.ic_profile))

                        setOnItemSelectedListener { itemId ->
                            selectedTab = itemId
                        }
                    }
                },
                update = { sqNav ->
                    sqNav.setSelectedItemId(selectedTab, true)
                }
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFF161023))
                .padding(innerPadding),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "Jetpack Compose + SqNav",
                    color = Color.White,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Active Screen: $tabName",
                    color = Color(0xFFD4BBFF),
                    fontSize = 16.sp
                )
            }
        }
    }
}
