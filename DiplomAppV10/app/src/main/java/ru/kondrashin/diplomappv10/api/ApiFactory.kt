package ru.kondrashin.diplomappv10.api

import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiFactory {
    private val gsonBuilder = GsonBuilder()
    private var retrofit = Retrofit.Builder()
        .baseUrl("https://rsueworksercher.serveo.net/")
        .addConverterFactory(GsonConverterFactory.create(gsonBuilder.create()))
        .build()
    val docApi = retrofit.create(DocumentsAPI::class.java)
    val userApi = retrofit.create(UsersAPI::class.java)
    val specApi = retrofit.create(SpecializationsAPI::class.java)
    val eduApi = retrofit.create(EducationsAPI::class.java)
    val dependApi = retrofit.create(DocDependenciesAPI::class.java)

}