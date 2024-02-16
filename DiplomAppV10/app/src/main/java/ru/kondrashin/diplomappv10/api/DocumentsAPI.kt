package ru.kondrashin.diplomappv10.api
import android.app.appsearch.PutDocumentsRequest
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.GET
import retrofit2.http.POST
import ru.kondrashin.diplomappv10.data_class.AddDocument
import ru.kondrashin.diplomappv10.data_class.Document
import ru.kondrashin.diplomappv10.data_class.ServerResponse

interface DocumentsAPI {
    @GET("documents")
    suspend fun getDocumentsAsync(): List<Document>
    @GET("documents/{doc_id}")
    suspend fun getDocumentAsync(@Path("doc_id") id: Int): Document
    @POST("documents")
    suspend fun postDocumentAsync(@Body postDocumentRequest: AddDocument)
    @PUT("documents/{doc_id}")
    suspend fun putDocumentAsync(@Path("doc_id") id: Int, @Body putDocumentsRequest: Document): ServerResponse
    @DELETE("documents/{doc_id}")
    suspend fun deleteDocumentAsync(@Path("doc_id") id: Int)


}
