package ru.kondrashin.diplomappv10.library_singleton

import android.content.Context
import ru.kondrashin.diplomappv10.data_class.DocResponse
import ru.kondrashin.diplomappv10.data_class.Document

class DocResponsesLib private constructor(context: Context){
    var responses = mutableListOf<DocResponse>()
    companion object {
        private var INSTANCE: DocResponsesLib? = null
        fun get(context: Context): DocResponsesLib {
            if (INSTANCE == null)
                INSTANCE =  DocResponsesLib(context)
            return INSTANCE!!
        }

    }
    fun getAllDocResponses(): MutableList<DocResponse>{
        return responses

    }




    fun getDocument(responseId: Int): DocResponse?{
        for (resp in responses) {
            if (resp.id == responseId){
                return resp
            }
        }
        return null
    }
    fun addDocument(resp: DocResponse){
        responses.add(resp)
    }
}