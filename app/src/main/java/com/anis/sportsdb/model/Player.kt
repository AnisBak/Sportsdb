package com.anis.sportsdb.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Player(
    val strPlayer: String?,
    val strSport:String?,
    val strBirthLocation:String?,
    val dateBorn: String?,
    val strHeight: String?,
    val strTeam: String?,
    val strTeam2: String?,
    val strThumb: String?,
    val strDescriptionEN: String?,
    val strCutout: String?
)
