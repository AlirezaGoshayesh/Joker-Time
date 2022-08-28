package com.test.jokertime.ui.viewModels

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Category
import androidx.compose.material.icons.filled.Feed
import androidx.compose.material.icons.filled.SentimentSatisfied
import androidx.compose.material.icons.filled.SentimentVerySatisfied
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.BaselineShift
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.test.jokertime.R
import com.test.jokertime.ui.theme.JokerTimeTheme


@Composable
fun HomeScreen(
    modifier: Modifier = Modifier
) {
    Column(
        modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        TopHeader()
        JokerAnimation(modifier = Modifier.weight(1f))
        CategoriesSection(list = listOf("Misc", "Programming", "Dark", "Spooky"))
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
                        offset = Offset(float, float-5),
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
                text = "will tell you jokes",
                color = MaterialTheme.colors.primaryVariant,
                fontSize = 14.sp
            )
            Spacer(modifier = Modifier.width(4.dp))
            Icon(
                imageVector = Icons.Default.SentimentVerySatisfied,
                contentDescription = "laugh",
                tint = MaterialTheme.colors.primary
            )
        }
    }
}

@Composable
fun CategoriesSection(list: List<String>, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Choose one category!", color = MaterialTheme.colors.primary, fontSize = 18.sp)
        Spacer(modifier = modifier.height(8.dp))
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(8.dp)
        ) {
            items(list) { item ->
                CategoryButton(text = item)
            }
        }
    }
}

@Composable
fun CategoryButton(text: String, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .heightIn(min = 40.dp)
            .clip(RoundedCornerShape(8.dp))
            .fillMaxWidth()
            .background(MaterialTheme.colors.primary),
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


@Composable
fun JokeBox(modifier: Modifier, jokeCategory: String, jokeText: String) {
    Surface(
        shape = MaterialTheme.shapes.medium,
        modifier = modifier.fillMaxWidth(),
        color = MaterialTheme.colors.secondary
    ) {
        Column {
            CategoryName(name = jokeCategory)
            JokeDescription(jokeText = jokeText)
        }
    }
}

@Composable
fun CategoryName(name: String) {
    Row(
        Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center

    ) {
        Icon(
            imageVector = Icons.Default.Category,
            contentDescription = null,
            tint = MaterialTheme.colors.primary
        )
        Text(
            color = MaterialTheme.colors.primary,
            text = name,
            style = MaterialTheme.typography.h5,
            modifier = Modifier
                .padding(horizontal = 16.dp)
        )
    }
}

@Composable
fun JokeDescription(jokeText: String) {
    Text(
        text = jokeText,
        Modifier
            .fillMaxWidth()
            .padding(all = 8.dp),
        style = MaterialTheme.typography.body1
    )
}

@Composable
fun GetNewJokeButton(onClicked: () -> Unit) {
    Button(
        onClick = onClicked,
        Modifier
            .padding(8.dp)
    ) {
        Icon(
            imageVector = Icons.Default.Feed,
            contentDescription = null,
            Modifier.padding(horizontal = 8.dp)
        )
        Text(
            text = stringResource(R.string.tell_toke),
            color = MaterialTheme.colors.onPrimary,
            style = MaterialTheme.typography.button
        )
    }
}

@Composable
fun ErrorBox(text: String, modifier: Modifier = Modifier) {
    Surface(
        modifier = modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.small,
        color = MaterialTheme.colors.error
    ) {
        Text(text = text, modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center)
    }
}

@Composable
fun Loading(modifier: Modifier = Modifier) {
    CircularProgressIndicator()
}

@Preview(showBackground = true)
@Composable
fun GetNewJokeButtonPreview() {
    JokerTimeTheme {
        GetNewJokeButton {}
    }
}

@Preview(showBackground = true)
@Composable
fun JokeBoxPreview() {
    JokerTimeTheme {
        JokeBox(
            modifier = Modifier.padding(all = 8.dp),
            jokeCategory = "Program",
            jokeText = "Java and C were telling jokes. It was C's turn, so he writes something on the wall, points to it and says \"Do you get the reference?\" But Java didn't."
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