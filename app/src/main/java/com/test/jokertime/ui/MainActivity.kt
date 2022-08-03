package com.test.jokertime.ui

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Category
import androidx.compose.material.icons.filled.Feed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.test.jokertime.R
import com.test.jokertime.data.model.JokeModel
import com.test.jokertime.domain.base.Resource
import com.test.jokertime.ui.theme.JokerTimeTheme
import com.test.jokertime.ui.viewModels.TellMeJokeVM
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
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
fun HomeScreen(
    modifier: Modifier = Modifier,
    tellMeJokeVM: TellMeJokeVM = viewModel()
) {
    Column(modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        val jokeValue by tellMeJokeVM.joke
        when (jokeValue) {
            is Resource.Error -> ErrorBox(
                text = (jokeValue as Resource.Error).errorModel.getErrorMessage()
            )
            is Resource.Loading -> {}
            is Resource.Success -> JokeBox(
                jokeCategory = (jokeValue as Resource.Success<JokeModel>).data.category,
                jokeText = (jokeValue as Resource.Success<JokeModel>).data.joke,
                modifier = Modifier.padding(all = 8.dp)
            )
        }
        GetNewJokeButton {
            tellMeJokeVM.tellJoke()
        }
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