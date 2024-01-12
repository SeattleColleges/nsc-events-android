package com.example.nsc_events.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nsc_events.data.Datasource
import kotlinx.coroutines.launch

/*
    Create a simple viewModel to hold data incoming from the DB
*/
class EventsViewModel : ViewModel() {
    private val _events = MutableLiveData<List<Event>>()
    val events: LiveData<List<Event>> = _events

    init {
        viewModelScope.launch {
            _events.value = Datasource().loadEvents()
        }
    }
}