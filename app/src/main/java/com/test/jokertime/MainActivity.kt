package com.test.jokertime

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Category
import androidx.compose.material.icons.filled.Feed
import androidx.compose.material.icons.filled.Info
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.test.jokertime.ui.theme.JokerTimeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JokerTimeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    HomeScreen()
                }
            }
        }
    }
}

data class Joke(val category: String, val description: String)

@Composable
fun JokeBox(modifier: Modifier, joke: Joke) {
    Surface(
        shape = MaterialTheme.shapes.medium,
        modifier = modifier.fillMaxWidth(),
        color = MaterialTheme.colors.secondary
    ) {
        Column {
            CategoryName(name = joke.category)
            JokeDescription(jokeText = joke.description)
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
fun GetNewJokeButton() {
    Button(
        onClick = { /*TODO*/ },
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
fun HomeScreen(modifier: Modifier = Modifier) {
    Column(modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        JokeBox(
            joke = Joke(
                "Program",
                "Java and C were telling jokes. It was C's turn, so he writes something on the wall, points to it and says \"Do you get the reference?\" But Java didn't."
            ),
            modifier = Modifier.padding(all = 8.dp)
        )
        GetNewJokeButton()
    }
}

@Preview(showBackground = true)
@Composable
fun GetNewJokeButtonPreview() {
    JokerTimeTheme {
        GetNewJokeButton()
    }
}

@Preview(showBackground = true)
@Composable
fun JokeBoxPreview() {
    JokerTimeTheme {
        JokeBox(
            joke = Joke(
                "Program",
                "Java and C were telling jokes. It was C's turn, so he writes something on the wall, points to it and says \"Do you get the reference?\" But Java didn't."
            ),
            modifier = Modifier.padding(all = 8.dp)
        )
    }
}