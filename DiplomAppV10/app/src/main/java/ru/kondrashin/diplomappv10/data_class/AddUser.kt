package ru.kondrashin.diplomappv10.data_class

data class AddUser(
    var FIO: String,
    var type: String,
    var phone: Long,
    var email: String,
    var password: String
)