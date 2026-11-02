package com.example.a1dzvkkotlin

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.integerResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                SquareGridScreen()
            }
        }
    }
}

@Composable
fun SquareGridScreen() {
    var squaresCount by rememberSaveable { mutableIntStateOf(0) }

    val scrollState = rememberLazyGridState()
    val scope = rememberCoroutineScope()
    val columnsCount = integerResource(id = R.integer.span_count)

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    squaresCount++
                    scope.launch {
                        scrollState.animateScrollToItem(if (squaresCount > 0) squaresCount - 1 else 0)
                    }
                }
            ) {
                // ВМЕСТО ИКОНКИ ТЕПЕРЬ ПРОСТО ТЕКСТ С ПЛЮСОМ
                Text(
                    text = "+",
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Light
                )
            }
        }
    ) { scaffoldPaddings ->
        LazyVerticalGrid(
            columns = GridCells.Fixed(columnsCount),
            state = scrollState,
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(
                start = 8.dp,
                end = 8.dp,
                top = scaffoldPaddings.calculateTopPadding() + 8.dp,
                bottom = scaffoldPaddings.calculateBottomPadding() + 88.dp
            ),
            modifier = Modifier.fillMaxSize()
        ) {
            items(squaresCount) { index ->
                SquareItem(squareIndex = index)
            }
        }
    }
}

@Composable
fun SquareItem(squareIndex: Int) {
    val squareColor = if (squareIndex % 2 == 0) {
        colorResource(id = R.color.red_even)
    } else {
        colorResource(id = R.color.blue_odd)
    }

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .aspectRatio(1f)
            .background(squareColor)
    ) {
        Text(
            text = squareIndex.toString(),
            color = colorResource(id = R.color.white_text),
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )
    }
}
