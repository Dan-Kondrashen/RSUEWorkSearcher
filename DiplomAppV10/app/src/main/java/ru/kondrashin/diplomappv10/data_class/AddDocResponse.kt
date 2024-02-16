package ru.kondrashin.diplomappv10.data_class

data class AddDocResponse (

    var type: String,
    var userId: Int,
    var docId: Int,
    var status: String,
)