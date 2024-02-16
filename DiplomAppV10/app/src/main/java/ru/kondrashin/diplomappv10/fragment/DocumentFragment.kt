package ru.kondrashin.diplomappv10.fragment
import android.app.ActivityOptions
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.EditText
import androidx.compose.animation.scaleIn
import androidx.fragment.app.Fragment
import com.google.gson.GsonBuilder
import kotlinx.coroutines.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.kondrashin.diplomappv10.MainActivity
import ru.kondrashin.diplomappv10.R
import ru.kondrashin.diplomappv10.activity.MainPageActivity
import ru.kondrashin.diplomappv10.api.DocumentsAPI
import ru.kondrashin.diplomappv10.api.EducationsAPI
import ru.kondrashin.diplomappv10.api.SpecializationsAPI
import ru.kondrashin.diplomappv10.api.UsersAPI
import ru.kondrashin.diplomappv10.data_class.Document
import ru.kondrashin.diplomappv10.library_singleton.DocumentsLib
import ru.kondrashin.diplomappv10.api.ApiFactory
import java.io.Serializable

import kotlin.coroutines.CoroutineContext

class DocumentFragment : Fragment() ,CoroutineScope {


    companion object{
        private const val ARG_DOCUMENT_ID = "doc_id"
        private const val ARG_USER_ID = "user_id"
        private const val TAG = "DocumentInfo"
        private const val LOAD_DATA = "loadDataFinished"

        fun newInstence(docId: Int, userId: Int) = DocumentFragment().apply {
            arguments = Bundle().apply{
                putSerializable(ARG_DOCUMENT_ID, docId)
                putSerializable(ARG_USER_ID, userId)

            }
        }

    }
//    inline fun <reified T : Serializable> Bundle.get_serializable(key: String): T? = when {
//        Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU -> getSerializable(key, T::class.java)
//        else -> @Suppress("DEPRECATION") getSerializable(key) as? T
//    }
    private var document: Document? = null
    private var loadDataFinished = false
    private lateinit var titleField: EditText
    private lateinit var contact_infoField: EditText
    private lateinit var extra_infoField: EditText
    private lateinit var salaryField: EditText
    private lateinit var dateButton: Button
    private lateinit var deleteButton: Button
    private lateinit var updateButton: Button
    private lateinit var backButton: Button

    private val job = SupervisorJob()
    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Main
    private val gsonBuilder = GsonBuilder()
    private var retrofit = Retrofit.Builder().baseUrl("https://rsueworksercher.serveo.net/")
        .addConverterFactory(GsonConverterFactory.create(gsonBuilder.create())).build()
    val docApi = retrofit.create(DocumentsAPI::class.java)
    val userApi = retrofit.create(UsersAPI::class.java)
//    val eduApi = retrofit.create(SpecializationsAPI::class.java)
//    val specApi = retrofit.create(EducationsAPI::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val docId = requireArguments().getSerializable(ARG_DOCUMENT_ID) as Int
        val userId = requireArguments().getSerializable(ARG_USER_ID) as Int
        loadData()
        document = DocumentsLib.get(requireActivity()).getDocument(docId)
//        val animationOnOpenFr = AnimationUtils.loadAnimation(requireContext(), R.anim.scalie_in_fragment_animation)
//        view?.startAnimation(animationOnOpenFr)
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        if (savedInstanceState != null){
            loadDataFinished = savedInstanceState.getBoolean(DocumentFragment.LOAD_DATA, false)
        }


        if (document?.type =="резюме") {
            val v = inflater.inflate(R.layout.fragment_document, container, false)
            titleField = v.findViewById(R.id.document_title)
            titleField.setText(document?.title)

            contact_infoField = v.findViewById(R.id.document_contact_info)
            contact_infoField.setText(document?.contactinfo)

            extra_infoField = v.findViewById(R.id.document_extra_info)
            extra_infoField.setText(document?.extra_info)

            salaryField = v.findViewById(R.id.document_salary)
            salaryField.setText(document?.salary)

            dateButton = v.findViewById(R.id.document_date)
            dateButton.text =document?.date

            return v
        }
        else if (document?.type =="Вакансия"){
            val v = inflater.inflate(R.layout.fragment_document, container, false)
            titleField = v.findViewById(R.id.document_title)
            titleField.setText(document?.title)

            contact_infoField = v.findViewById(R.id.document_contact_info)
            contact_infoField.setText(document?.contactinfo)

            extra_infoField = v.findViewById(R.id.document_extra_info)
            extra_infoField.setText(document?.extra_info)

            salaryField = v.findViewById(R.id.document_salary)
            salaryField.setText(document?.salary)

            dateButton = v.findViewById(R.id.document_date)
            dateButton.text =document?.date
            return v
        }
        else  { return null }


    }

    private fun loadData() {
        val documentLib = DocumentsLib.get(MainPageActivity())
    }
//    private fun loadData() = runBlocking { // (1)
//        val job = launch { // (2)
//
//            val documentLib = DocumentsLib.get(requireActivity())
//            val postRequest = docApi.getDocumentsAsync()
//
//
//            try {
//                documentLib.docs = postRequest as MutableList<Document>
//
//            } catch (e: Exception) {
//                Log.d(DocumentFragment.TAG, e.message.toString())
//            }
//
//        }
//        job.join()
//        loadDataFinished = true
//    }
}