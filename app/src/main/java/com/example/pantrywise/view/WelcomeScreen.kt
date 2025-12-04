package com.example.pantrywise.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.pantrywise.R
import com.example.pantrywise.ui.theme.PantryWiseTheme

@Composable
fun WelcomeScreen(onStartClick: () -> Unit) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Hello!",
            style = MaterialTheme.typography.headlineLarge

        )
        Text(
            text = "Shop smarter, waste less, stay organized.",
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.headlineSmall
        )
        Button(
            onClick = onStartClick,
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
            modifier = Modifier.fillMaxWidth().padding(16.dp)
        ) {
            Text("Let's Start")
        }
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Image(
                imageVector = ImageVector.vectorResource(R.drawable.bg_doodle_1),
                contentDescription = null,
                modifier = Modifier.align(Alignment.BottomEnd)
            )
            Image(
                imageVector = ImageVector.vectorResource(R.drawable.bg_doodle_2),
                contentDescription = null,
                modifier = Modifier.align(Alignment.TopEnd)
            )
            Image(
                imageVector = ImageVector.vectorResource(R.drawable.bg_doodle_3),
                contentDescription = null,
                modifier = Modifier.align(Alignment.CenterStart)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun WelcomeScreenPreview() {
    PantryWiseTheme {
        WelcomeScreen(onStartClick = {})
    }
}