package com.example.mobileapp_groupproject1.Entity

import java.io.Serializable

data class Event(
    val id: String = "",
    val name: String = "",
    val image: String = "",

) : Serializable {
    constructor() : this("", "","")
}
