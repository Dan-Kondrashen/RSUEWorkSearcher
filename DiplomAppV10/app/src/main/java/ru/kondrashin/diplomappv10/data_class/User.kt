package ru.kondrashin.diplomappv10.data_class

import java.util.*

data class User (
    var id: Int,
    var FIO: String,
    var phone: Long,
    var email: String,
    var type: String,
    var registration_date: String
    )
