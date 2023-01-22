package com.lotterysystem.app.ui.fragment.home.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lotterysystem.app.arch.api.ApiState
import com.lotterysystem.app.arch.repos.TicketRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

@HiltViewModel
public class HomeViewModel @Inject constructor(private val repository: TicketRepository) :
    ViewModel() {

    private val _userData: MutableStateFlow<ApiState> = MutableStateFlow(ApiState.EMPTY)
    val userData: StateFlow<ApiState> = _userData

    fun getTickets(
    ) {
        viewModelScope.launch {
            _userData.value = ApiState.LOADING
            repository.getTickets()
                .catch { e ->
                    _userData.value = ApiState.ERROR(e)
                }.collect { response ->
                    _userData.value = ApiState.SUCCESS(response)
                }
        }
    }
}