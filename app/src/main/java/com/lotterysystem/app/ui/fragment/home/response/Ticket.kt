package com.lotterysystem.app.ui.fragment.home.response

import java.io.Serializable

data class Ticket(
    val created: Long,
    val id: Int
) : Serializable