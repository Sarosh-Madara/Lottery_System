package com.lotterysystem.app.arch.api
import com.lotterysystem.app.ui.fragment.home.response.GetTicketsResponse
import com.lotterysystem.app.ui.fragment.tickets.response.TicketDetailResponse
import retrofit2.http.*

interface LotteryApi {
    companion object {
        // const val BASE_URL = "http://127.0.0.1:8000/"
        const val BASE_URL = "https://by82fsbdwk.execute-api.eu-west-1.amazonaws.com/"

        const val GET_TICKETS = "prod/ticket"
        const val GET_TICKET_BY_ID = "prod/ticket/{id}"

    }

    @GET(GET_TICKETS)
    suspend fun getTickets(
    ): GetTicketsResponse

    @GET(GET_TICKET_BY_ID)
    suspend fun getTicketsById(
        @Path("id") id: String
    ): TicketDetailResponse

}