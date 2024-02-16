package ru.kondrashin.diplomappv10.api

import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import ru.kondrashin.diplomappv10.data_class.AddUser
import ru.kondrashin.diplomappv10.data_class.ServerResponse
import ru.kondrashin.diplomappv10.data_class.User

interface UsersAPI {
    @GET("users")
    suspend fun getUsersAsync(): List<User>
    @GET("users/{user_id}")
    suspend fun getUserAsync(@Path("user_id") id: Int): User
    @POST("users")
    suspend fun postUserAsync(@Body postUserRequest: AddUser): ServerResponse
    @PUT("users/{user_id}")
    suspend fun putUserAsync(@Path("user_id") id: Int, @Body putUserRequest: User): ServerResponse
    @DELETE
    suspend fun  deleteUserAsync(@Path("user_id") id: Int): ServerResponse
}