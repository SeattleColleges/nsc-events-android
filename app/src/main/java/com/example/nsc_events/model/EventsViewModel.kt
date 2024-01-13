package com.example.nsc_events.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nsc_events.data.Datasource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class EventsViewModel : ViewModel() {
    private val _eventsState = MutableStateFlow<DataState<List<Event>>>(DataState.Loading)
    val eventsState: StateFlow<DataState<List<Event>>> = _eventsState

    init {
        loadEvents()
    }

    private fun loadEvents() {
        viewModelScope.launch {
            try {
                val events = Datasource().loadEvents()
                _eventsState.value = DataState.Success(events)
            } catch (e: Exception) {
                _eventsState.value = DataState.Error(e)
            }
        }
    }
}

sealed class DataState<out T> {
    object Loading : DataState<Nothing>()
    data class Success<out T>(val data: T) : DataState<T>()
    data class Error(val exception: Throwable) : DataState<Nothing>()
}