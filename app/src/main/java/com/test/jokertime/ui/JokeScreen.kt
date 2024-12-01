package com.test.jokertime.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Category
import androidx.compose.material.icons.filled.Feed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.test.jokertime.R
import com.test.jokertime.data.model.JokeModel
import com.test.jokertime.domain.base.Resource
import com.test.jokertime.ui.theme.JokerTimeTheme
import com.test.jokertime.ui.viewModels.TellMeJokeVM

@Composable
fun JokeScreen(
    navController: NavController,
    categories: String,
    modifier: Modifier = Modifier,
    tellMeJokeVM: TellMeJokeVM
) {
    var isTelling by rememberSaveable {
        mutableStateOf(false)
    }
    Column(
        modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        BackButton(onClicked = {
            navController.navigateUp()
        })
        val jokeValue by tellMeJokeVM.joke
        if (!isTelling)
            HelpView()
        when (jokeValue) {
            is Resource.Error -> ErrorBox(
                text = (jokeValue as Resource.Error).errorModel.getErrorMessage()
            )
            is Resource.Loading -> if (isTelling) Loading()
            is Resource.Success -> JokeBox(
                jokeCategory = (jokeValue as Resource.Success<JokeModel>).data.category,
                jokeText = (jokeValue as Resource.Success<JokeModel>).data.joke,
                modifier = Modifier.padding(all = 8.dp)
            )
        }
        GetNewJokeButton {
            isTelling = true
            tellMeJokeVM.tellJoke(categories)
        }
    }
}

@Composable
fun HelpView(modifier: Modifier = Modifier) {
    Text(modifier = modifier, text = buildAnnotatedString {
        withStyle(
            style = SpanStyle(
                color = MaterialTheme.colors.primary,
                fontSize = 14.sp
            )
        ) {
            append("Click")
        }
        withStyle(
            style = SpanStyle(
                color = MaterialTheme.colors.secondary,
                fontSize = 24.sp,
                fontWeight = FontWeight.ExtraBold,
                fontStyle = FontStyle.Italic
            )
        ) {
            append(" Tell Me Joker! ")
        }
        withStyle(
            style = SpanStyle(
                color = MaterialTheme.colors.primary,
                fontSize = 14.sp
            )
        ) {
            append(" button to get new joke!")
        }
    })
}

@Composable
fun BackButton(onClicked: () -> Unit) {
    Box(
        modifier = Modifier
            .background(MaterialTheme.colors.background)
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Icon(
            modifier = Modifier
                .size(32.dp)
                .background(color = MaterialTheme.colors.primary, shape = CircleShape)
                .padding(4.dp)
                .clickable { onClicked() },
            imageVector = Icons.Default.ArrowBack,
            contentDescription = "back",
            tint = MaterialTheme.colors.onPrimary
        )
    }
}

@Composable
fun JokeBox(modifier: Modifier, jokeCategory: String, jokeText: String) {
    Surface(
        shape = MaterialTheme.shapes.medium,
        modifier = modifier.fillMaxWidth(0.9f),
        color = MaterialTheme.colors.primary
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
            tint = MaterialTheme.colors.onPrimary
        )
        Text(
            color = MaterialTheme.colors.onPrimary,
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
        style = MaterialTheme.typography.body1,
        color = MaterialTheme.colors.onPrimary
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
    CircularProgressIndicator(modifier = modifier)
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