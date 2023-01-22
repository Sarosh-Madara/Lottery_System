package com.lotterysystem.app.arch.repos

import com.lotterysystem.app.arch.api.LotteryApi
import com.lotterysystem.app.ui.fragment.home.response.GetTicketsResponse
import com.lotterysystem.app.ui.fragment.tickets.response.TicketDetailResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject


class TicketRepository @Inject constructor(val apiHelper: LotteryApi) {

    fun getTickets(): Flow<GetTicketsResponse> = flow {
        val response = apiHelper.getTickets()
        emit(response)
    }.flowOn(Dispatchers.IO)

    fun getTicketById(tickedId: String): Flow<TicketDetailResponse> = flow {
        val response = apiHelper.getTicketsById(tickedId)
        emit(response)
    }.flowOn(Dispatchers.IO)


}