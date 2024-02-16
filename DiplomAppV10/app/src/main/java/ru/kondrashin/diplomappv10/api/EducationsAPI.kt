package ru.kondrashin.diplomappv10.api

import retrofit2.http.GET
import retrofit2.http.Path
import ru.kondrashin.diplomappv10.data_class.Education


interface EducationsAPI {
    @GET("educations")
    suspend fun getEducationsAsync(): List<Education>
    @GET("educations/{edu_id}")
    suspend fun getEducationAsync(@Path("edu_id") id: Int): Education
}