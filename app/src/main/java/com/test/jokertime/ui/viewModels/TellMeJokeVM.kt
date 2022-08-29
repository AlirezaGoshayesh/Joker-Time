package com.test.jokertime.ui.viewModels

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.test.jokertime.data.model.JokeModel
import com.test.jokertime.domain.GetRandomJoke
import com.test.jokertime.domain.base.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TellMeJokeVM @Inject constructor(
    private val getRandomJoke: GetRandomJoke
) : ViewModel() {

    private val _jokeModel = mutableStateOf<Resource<JokeModel>>(Resource.Loading)
    val joke: State<Resource<JokeModel>> get() = _jokeModel

    /**
     * Called to get random joke
     */
    fun tellJoke(category: String) {
        viewModelScope.launch {
            _jokeModel.value = getRandomJoke(category)
        }

    }
}