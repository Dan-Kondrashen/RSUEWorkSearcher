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
import ru.kondrashin.diplomappv10.R
import ru.kondrashin.diplomappv10.api.DocumentsAPI
import ru.kondrashin.diplomappv10.api.EducationsAPI
import ru.kondrashin.diplomappv10.api.SpecializationsAPI
import ru.kondrashin.diplomappv10.api.UsersAPI
import ru.kondrashin.diplomappv10.data_class.Document
import ru.kondrashin.diplomappv10.library_singleton.DocumentsLib
import ru.kondrashin.diplomappv10.api.ApiFactory
import ru.kondrashin.diplomappv10.data_class.AddDocument
import java.io.Serializable

import kotlin.coroutines.CoroutineContext

class NewDocumentFragment : Fragment() ,CoroutineScope {


    companion object{
        private const val ARG_DOCUMENT_TYPE = "doc_type"
        private const val ARG_USER_ID = "user_id"
        private const val TAG = "DocumentInfo"
        private const val LOAD_DATA = "loadDataFinished"

        fun newInstence(docType: String, userId: Int) = NewDocumentFragment().apply {
            arguments = Bundle().apply{
                putSerializable(ARG_DOCUMENT_TYPE, docType)
                putSerializable(ARG_USER_ID, userId)

            }
        }

    }
//    inline fun <reified T : Serializable> Bundle.get_serializable(key: String): T? = when {
//        Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU -> getSerializable(key, T::class.java)
//        else -> @Suppress("DEPRECATION") getSerializable(key) as? T
//    }

    private lateinit var titleField: EditText
    private lateinit var contact_infoField: EditText
    private lateinit var extra_infoField: EditText
    private lateinit var salaryField: EditText
    private lateinit var dateButton: Button

    private lateinit var addButton: Button
    private lateinit var backButton: Button

    private val job = SupervisorJob()
    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Main
    private val gsonBuilder = GsonBuilder()
    private var type = ""
    private var retrofit = Retrofit.Builder().baseUrl("https://rsueworksercher.serveo.net/")
        .addConverterFactory(GsonConverterFactory.create(gsonBuilder.create())).build()
    val docApi = retrofit.create(DocumentsAPI::class.java)

//    val eduApi = retrofit.create(SpecializationsAPI::class.java)
//    val specApi = retrofit.create(EducationsAPI::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val userId = requireArguments().getSerializable(ARG_USER_ID) as Int

//        val animationOnOpenFr = AnimationUtils.loadAnimation(requireContext(), R.anim.scalie_in_fragment_animation)
//        view?.startAnimation(animationOnOpenFr)
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        if (type =="резюме") {
            val v = inflater.inflate(R.layout.add_document_fragment, container, false)
            titleField = v.findViewById(R.id.document_title)
            contact_infoField = v.findViewById(R.id.document_contact_info)
            extra_infoField = v.findViewById(R.id.document_extra_info)
            salaryField = v.findViewById(R.id.document_salary)


            return v
        }
        else if (type =="Вакансия"){
            val v = inflater.inflate(R.layout.add_document_fragment, container, false)
            titleField = v.findViewById(R.id.document_title)
            contact_infoField = v.findViewById(R.id.document_contact_info)
            extra_infoField = v.findViewById(R.id.document_extra_info)
            salaryField = v.findViewById(R.id.document_salary)
            addButton = v.findViewById(R.id.add_document)
            addButton.setOnClickListener {
                CoroutineScope(Dispatchers.IO).launch {

                    val blog = docApi.postDocumentAsync(
                        AddDocument(
                            titleField.text.toString(),
                            salaryField.text.toString(),
                            extra_infoField.text.toString(),
                            contact_infoField.text.toString(),
                            type,
                            1
                        )
                    )
                }
            }
            return v
        }
        else  { return null }


    }


}