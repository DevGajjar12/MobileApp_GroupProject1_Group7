package com.example.mobileapp_groupproject1.Entity

import java.io.Serializable

data class Recruit(
    val id: String = "",
    val name: String = "",
    val description: String = "",
    val skills: String = "",
    val image: String = ""
) : Serializable {
    constructor() : this("", "", "", "", "")
}
