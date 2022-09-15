package com.anis.sportsdb.api

import com.anis.sportsdb.model.Player
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class TheSportsDbResponse(
    val player : List<Player>?
)
