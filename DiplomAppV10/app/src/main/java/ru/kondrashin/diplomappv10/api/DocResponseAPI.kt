package ru.kondrashin.diplomappv10.api

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import ru.kondrashin.diplomappv10.data_class.AddDocResponse
import ru.kondrashin.diplomappv10.data_class.DocResponse

interface DocResponseAPI {
    @GET("/docresponse")
    suspend fun getAllDocResponseAsync(): List<DocResponse>
    @POST("/docresponse")
    suspend fun postDocResponseAsync(@Body postDocResponse: AddDocResponse)
}