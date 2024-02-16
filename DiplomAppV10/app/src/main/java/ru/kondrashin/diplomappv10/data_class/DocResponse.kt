package ru.kondrashin.diplomappv10.data_class

data class DocResponse (
    var id: Int,
    var type: String,
    var userId: Int,
    var docId: Int,
    var status: String,
)