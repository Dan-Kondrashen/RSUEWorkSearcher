package ru.kondrashin.diplomappv10.data_class

import java.util.Date
import java.util.UUID

data class Comment (
    var id: Int,
    var content: String,
    var comment_date: String,
    var userId: Int,
    var respId: Int
)