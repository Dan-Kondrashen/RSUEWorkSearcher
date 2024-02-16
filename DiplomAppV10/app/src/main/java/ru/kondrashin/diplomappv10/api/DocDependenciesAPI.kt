package ru.kondrashin.diplomappv10.api

import retrofit2.http.*
import ru.kondrashin.diplomappv10.data_class.AddDocDependencies
import ru.kondrashin.diplomappv10.data_class.DocDependencies
import ru.kondrashin.diplomappv10.data_class.ServerResponse


interface DocDependenciesAPI {
    @GET("documents/{doc_id}/dependencies")
    suspend fun getDependenciesAsync(@Path("doc_id") id: Int): List<DocDependencies>
    @POST("documents/{doc_id}/dependencies")
    suspend fun postDependenciesAsync(@Path("doc_id") id: Int, @Body postDependencies: AddDocDependencies): ServerResponse
    @PUT("documents/{doc_id}/dependencies/{dep_id}")
    suspend fun putDependenciesAsync(@Path("doc_id") id: Int, @Path("dep_id") depId: Int,@Body putDependencies: AddDocDependencies): ServerResponse
}