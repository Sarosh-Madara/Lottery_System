package com.lotterysystem.app.ui.fragment.home.response

import java.io.Serializable

data class GetTicketsResponse(
    val tickets: List<Ticket>
) : Serializable