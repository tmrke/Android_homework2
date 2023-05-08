package ru.ageev.android_homework2.data.remote.model.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TokenResponse(
    @SerialName("token") var token: String,
    @SerialName("userId") var userId: String
)
