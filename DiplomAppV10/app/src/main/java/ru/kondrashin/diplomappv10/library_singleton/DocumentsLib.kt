package ru.kondrashin.diplomappv10.library_singleton

import android.content.Context
import ru.kondrashin.diplomappv10.data_class.Document

class DocumentsLib private constructor(context: Context){
    var docs = mutableListOf<Document>()
    companion object {
        private var INSTANCE: DocumentsLib? = null
        fun get(context: Context): DocumentsLib {
            if (INSTANCE == null)
                INSTANCE =  DocumentsLib(context)
            return INSTANCE!!
        }

    }
    fun getAllDocuments(): MutableList<Document>{
        return docs

    }
    private var resumes: MutableList<Document> = mutableListOf()
    var vacancy: MutableList<Document> = mutableListOf()

    fun getAllResumes(): MutableList<Document>{
        for (doc in docs){
            if (doc.type == "резюме"){
                resumes.add(doc)
            }
        }
        return resumes
    }

    fun getAllVacancy(): MutableList<Document>{
        for (doc in docs){
            if (doc.type == "Вакансия"){
                vacancy.add(doc)
            }
        }
        return vacancy
    }

    fun getDocument(docId: Int): Document?{
        for (doc in docs) {
            if (doc.id == docId){
                return doc
            }
        }
        return null
    }
    fun addDocument(doc: Document){
        docs.add(doc)
    }
}