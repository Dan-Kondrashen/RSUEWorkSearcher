package ru.kondrashin.diplomappv10.api

import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.DELETE
import retrofit2.http.Path
import ru.kondrashin.diplomappv10.data_class.Specialization

interface SpecializationsAPI {
    @GET("specializations")
    suspend fun getSpecsAsync(): List<Specialization>
    @GET("specializations/{spec_id}")
    suspend fun getSpecAsync(@Path("spec_id") id: Int): Specialization

}