package com.example.newsapp.data.model

import com.google.gson.annotations.SerializedName

open class BaseResponse (
    @field:SerializedName("status")
    val status: String? = null,
    @field:SerializedName("message")
    val message: String? = null,
    @field:SerializedName("response")
    val response: String? = null

)