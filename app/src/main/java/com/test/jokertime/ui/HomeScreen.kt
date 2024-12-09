package com.test.jokertime.ui

import androidx.compose.animation.animateColor
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.SentimentVerySatisfied
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.BaselineShift
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.test.jokertime.R
import com.test.jokertime.ui.theme.JokerTimeTheme
import kotlinx.coroutines.launch


@Composable
fun HomeScreen(
    scaffoldState: ScaffoldState,
    navController: NavController,
    modifier: Modifier = Modifier
) {
    var selectedCategories: List<String> by rememberSaveable() {
        mutableStateOf(listOf("Programming"))
    }
    val coroutineScope = rememberCoroutineScope()
    Column(
        modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        TopHeader()
        JokerAnimation(modifier = Modifier.weight(1f))
        CategoriesSection(
            categories = listOf("Programming", "Misc", "Dark", "Pun"),
            selectedCategories = selectedCategories,
            onSelectionChanged = { category ->
                val list = selectedCategories.toMutableList()
                if (list.contains(category))
                    list.remove(category)
                else
                    list.add(category)
                selectedCategories = list
            },
            onDoneClicked = {
                if (selectedCategories.isEmpty())
                    coroutineScope.launch {
                        scaffoldState.snackbarHostState.showSnackbar(
                            message = "Choose at least one category!"
                        )
                    }
                else
                    navController.navigate(
                        Screen.JokeScreen.withArgs(
                            selectedCategories.joinToString(",")
                        )
                    )
            }
        )
    }
}

@Composable
fun JokerAnimation(modifier: Modifier = Modifier) {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.anim))
    LottieAnimation(
        modifier = modifier,
        composition = composition,
        iterations = LottieConstants.IterateForever
    )
}

@Composable
fun TopHeader(modifier: Modifier = Modifier) {
    val infiniteTransition = rememberInfiniteTransition()
    val color by infiniteTransition.animateColor(
        initialValue = MaterialTheme.colors.primary,
        targetValue = MaterialTheme.colors.primaryVariant,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1500, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        )
    )
    val float by infiniteTransition.animateFloat(
        initialValue = 5f,
        targetValue = 30f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1500, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        )
    )
    Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        Text(modifier = modifier, text = buildAnnotatedString {
            withStyle(
                style = SpanStyle(
                    baselineShift = BaselineShift(-2f),
                    fontSize = 150.sp,
                    letterSpacing = 16.sp,
                    color = MaterialTheme.colors.primaryVariant,
                    fontWeight = FontWeight.ExtraBold,
                    shadow = Shadow(
                        color = MaterialTheme.colors.primary,
                        offset = Offset(float, float - 5),
                        blurRadius = 50f
                    )
                )

            ) {
                append("J")
            }
            withStyle(
                style = SpanStyle(
                    color = MaterialTheme.colors.primaryVariant,
                    fontSize = 48.sp
                )
            ) {
                append("OKER")
            }
        }

        )
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
                .offset(x = 60.dp, y = (-60).dp)
        ) {
            Text(
                text = "tells you jokes",
                color = color,
                fontSize = 14.sp
            )
            Spacer(modifier = Modifier.width(4.dp))
            Icon(
                imageVector = Icons.Default.SentimentVerySatisfied,
                contentDescription = "laugh",
                tint = color
            )
        }
    }
}

@Composable
fun CategoriesSection(
    categories: List<String>,
    selectedCategories: List<String>,
    onSelectionChanged: (String) -> Unit,
    onDoneClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                text = "Choose categories and click",
                color = MaterialTheme.colors.primary,
                fontSize = 18.sp
            )
            Icon(
                modifier = Modifier
                    .size(32.dp)
                    .background(color = MaterialTheme.colors.primary, shape = CircleShape)
                    .padding(4.dp)
                    .testTag("Done")
                    .clickable { onDoneClicked() },
                imageVector = Icons.Default.ArrowForward,
                contentDescription = "next",
                tint = MaterialTheme.colors.onPrimary
            )
        }
        Spacer(modifier = modifier.height(8.dp))
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(8.dp)
        ) {
            items(categories) { item ->
                CategoryChip(
                    text = item,
                    isSelected = selectedCategories.contains(item),
                    onClicked = onSelectionChanged
                )
            }
        }
    }
}

@Composable
fun CategoryChip(
    text: String,
    isSelected: Boolean = false,
    onClicked: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .heightIn(min = 40.dp)
            .clip(RoundedCornerShape(8.dp))
            .fillMaxWidth()
            .background(if (isSelected) MaterialTheme.colors.primary else Color.LightGray)
            .toggleable(
                value = isSelected,
                onValueChange = {
                    onClicked(text)
                }
            ),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            color = MaterialTheme.colors.onPrimary,
            fontSize = 18.sp,
            textAlign = TextAlign.Center
        )
    }
}

@Preview(showBackground = true)
@Composable
fun TopHeaderPreview() {
    JokerTimeTheme {
        TopHeader()
    }
}