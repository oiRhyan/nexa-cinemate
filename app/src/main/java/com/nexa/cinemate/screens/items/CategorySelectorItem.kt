package com.nexa.cinemate.screens.items


import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.*

@Composable
fun CategorySelectorItem(
    selectorItem: String,
    selectedCategory: String,
    onCategorySelected: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .clickable { onCategorySelected(selectorItem) }
            .padding(8.dp),
        horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally
    ) {
        Text(
            text = selectorItem,
            color = Color.White,
            fontWeight = if (selectedCategory == selectorItem) FontWeight.Bold else FontWeight.Normal
        )
        Spacer(modifier = Modifier.height(4.dp))
        if (selectedCategory == selectorItem) {
            Box(
                modifier = Modifier
                    .height(3.dp)
                    .width(40.dp)
                    .background(Color(0xFF5D65BC), shape = RoundedCornerShape(50))
            )
        }
    }
}

@Composable
fun CategorySelector() {
    var selectedCategory by remember { mutableStateOf("Todos") }
    val categories = listOf("Todos", "Ação", "Comédia", "Drama")

    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
        categories.forEach { category ->
            CategorySelectorItem(
                selectorItem = category,
                selectedCategory = selectedCategory,
                onCategorySelected = { selectedCategory = it }
            )
        }
    }
}
