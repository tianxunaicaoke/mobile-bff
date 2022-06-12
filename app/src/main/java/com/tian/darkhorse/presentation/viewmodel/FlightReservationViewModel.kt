package com.tian.darkhorse.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tian.darkhorse.data.entity.PaymentResult
import com.tian.darkhorse.domain.FakeTicketDataUseCase
import com.tian.darkhorse.domain.GetItineraryUseCase
import com.tian.darkhorse.domain.TicketPaymentUseCase
import com.tian.darkhorse.data.network.fold
import com.tian.darkhorse.presentation.model.AlertMessage
import com.tian.darkhorse.presentation.model.Itinerary
import com.tian.darkhorse.presentation.model.Ticket
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

@HiltViewModel
class FlightReservationViewModel @Inject constructor(
    private val getItineraryUseCase: GetItineraryUseCase,
    private val payUseCase: TicketPaymentUseCase,
    private val getTicketDataUseCase: FakeTicketDataUseCase,
) : ViewModel() {
    private val _uiState = MutableStateFlow(State(State.Result.Welcome))
    val uiState = _uiState.asStateFlow()

    private val _uiEvent = Channel<Event>(Channel.BUFFERED)
    val uiEvent: Flow<Event> = _uiEvent.receiveAsFlow()

    init {
        action(Action.RequestTicket)
    }

    fun action(action: Action) {
        val newState = reduce(uiState.value, action)
        updateState(newState)
        runSideEffect(action)
    }

    private fun sendEvent(event: Event) {
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }

    private fun updateState(newState: State) {
        viewModelScope.launch {
            _uiState.value = newState
        }
    }

    private fun reduce(currentState: State, action: Action): State {
        return when (action) {
            is Action.RequestPay,
            is Action.RequestTicket,
            is Action.RequestItinerary -> {
                currentState.copy(result = State.Result.Loading)
            }
        }
    }

    private fun runSideEffect(action: Action) {
        when (action) {
            is Action.RequestPay -> requestPay(action.ticketId)
            is Action.RequestItinerary -> requestItinerary()
            Action.RequestTicket -> requestTickets()
        }
    }

    private fun requestItinerary() {
        viewModelScope.launch {
            getItineraryUseCase().collect { result ->
                result.fold(onSuccess = {
                    updateState(uiState.value.copy(result = State.Result.ItinerarySuccess(it)))
                }, onFailure = {
                    updateState(uiState.value.copy(result = State.Result.Error(it.message.toString())))
                })
            }
        }
    }

    private fun requestPay(id: String) {
        viewModelScope.launch {
            payUseCase(id).collect { result ->
                result.fold(onSuccess = {
                    sendEvent(Event.PopupMessage(it.toMessage()))
                }, onFailure = {
                    sendEvent(Event.PopupMessage(PaymentResult(0, "").toMessage()))
                })
            }
        }
    }

    private fun requestTickets() {
        viewModelScope.launch {
            getTicketDataUseCase().collect { result ->
                result.fold(onSuccess = {
                    updateState(uiState.value.copy(result = State.Result.TicketSuccess(it)))
                }, onFailure = {
                    updateState(uiState.value.copy(result = State.Result.Error(it.message.toString())))
                })
            }
        }
    }

    sealed class Action {
        data class RequestPay(val ticketId: String) : Action()
        object RequestItinerary : Action()
        object RequestTicket : Action()
    }

    sealed class Event {
        data class PopupMessage(val message: AlertMessage) : Event()
    }

    data class State(
        val result: Result
    ) {
        sealed class Result {
            object Welcome : Result()
            object Loading : Result()
            data class ItinerarySuccess(val success: List<Itinerary>) : Result()
            data class TicketSuccess(val success: List<Ticket>) : Result()
            data class Error(val error: String) : Result()
        }
    }

    private fun PaymentResult.toMessage(): AlertMessage {
        return when (this.code) {
            200 -> AlertMessage(
                "支付成功",
                "感谢您使用任你行预定机票，请按时登记，详细信息可以查询订单"
            )
            408 -> AlertMessage(
                "支付超时",
                "请您务必在30分钟内支付，本次超时，请刷新"
            )
            500 -> AlertMessage(
                "支付失败",
                "不好意思，后台出现问题，请您稍后刷新"
            )
            else -> AlertMessage(
                "网络出现异常",
                "您的网络出现异常，请检查后刷新."
            )
        }
    }
}