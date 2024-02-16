package ru.kondrashin.diplomappv10.fragment

import android.os.Bundle
import android.util.Log
import android.view.*
import android.view.animation.AnimationUtils
import android.widget.TextView
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.clearFragmentResult
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.GsonBuilder
import kotlinx.coroutines.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

import ru.kondrashin.diplomappv10.R
import ru.kondrashin.diplomappv10.adapter.DocumentListAdapter
import ru.kondrashin.diplomappv10.api.DocResponseAPI

import ru.kondrashin.diplomappv10.api.DocumentsAPI
import ru.kondrashin.diplomappv10.api.UsersAPI
import ru.kondrashin.diplomappv10.data_class.DocResponse
import ru.kondrashin.diplomappv10.data_class.Document
import ru.kondrashin.diplomappv10.data_class.User
import ru.kondrashin.diplomappv10.library_singleton.DocumentsLib
import ru.kondrashin.diplomappv10.library_singleton.UsersLib
import ru.kondrashin.diplomappv10.library_singleton.DocResponsesLib
import kotlin.coroutines.CoroutineContext

class MainFragmentUnReg : Fragment(), CoroutineScope {

    private var resumeRecyclerView: RecyclerView? = null
    private var vacancyRecyclerView: RecyclerView? = null
    private lateinit var searchView: SearchView
    private var adapter: DocumentListAdapter? = null
    private var adapter2: DocumentListAdapter? = null
    private var position: Int = 0
    private var loadDataFinished = false

    companion object {
        private const val TAG = "MainPageUnReg"
        private const val LOAD_DATA = "loadDataFinished"

        fun newInstance() = MainFragmentUnReg().apply {
        }
    }
    private var document: Document? = null
    private var job = SupervisorJob()
    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Main
    private val gsonBuilder = GsonBuilder()
    private var retrofit = Retrofit.Builder()
        .baseUrl("https://rsueworksercher.serveo.net/")
        .addConverterFactory(GsonConverterFactory.create(gsonBuilder.create()))
        .build()
    val docApi = retrofit.create(DocumentsAPI::class.java)
    val userApi = retrofit.create(UsersAPI::class.java)
    val respApi = retrofit.create(DocResponseAPI::class.java)

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putBoolean(LOAD_DATA,loadDataFinished)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?

    ): View {
        if (savedInstanceState != null) {
            loadDataFinished = savedInstanceState.getBoolean(LOAD_DATA, false)
        }
        val view: View = inflater.inflate(
            R.layout.main_list_page, container, false
        )
        vacancyRecyclerView = view.findViewById(R.id.document_vacancy_recycler_view)
        vacancyRecyclerView!!.layoutManager =
            LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        resumeRecyclerView = view.findViewById(R.id.document_resume_recycler_view)
        resumeRecyclerView!!.layoutManager =
            LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        searchView = view.findViewById(R.id.search_bar)
        searchView.setOnSearchClickListener{
            val animation = AnimationUtils.loadAnimation(context, R.anim.scale_in_searchview)
            searchView.startAnimation(animation)
        }
        searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(newText: String?): Boolean {
                adapter!!.filter.filter(newText)
                adapter2!!.filter.filter(newText)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                adapter!!.filter.filter(newText)
                adapter2!!.filter.filter(newText)
                return true
            }

        })


        if (loadDataFinished) updateUI()
        else loadData()
        return view
    }
    override fun onResume() {
        super.onResume()
        updateUI()
    }
    override fun onDestroy() {
        super.onDestroy()
        coroutineContext.cancelChildren()
    }
    private fun filterData(){}
    private fun updateUI() {
        val docLib = DocumentsLib.get(requireActivity())
        val userLib = UsersLib.get(requireActivity())
        val respLib = DocResponsesLib.get(requireActivity())
        var resumes = docLib.getAllResumes()
        var vacancy = docLib.getAllVacancy()
        val users = userLib.users
        val responses = respLib.responses

        if (adapter == null) {
            adapter = DocumentListAdapter(vacancy,users, responses)
            vacancyRecyclerView!!.adapter = adapter
        }
        else {
            position = adapter!!.positionAdaptern
            vacancy.clear()
            vacancy = docLib.getAllVacancy()
            adapter!!.notifyItemChanged(position)
        }
        if (adapter2 == null) {
            adapter2 = DocumentListAdapter(resumes, users, responses)
            resumeRecyclerView!!.adapter = adapter2
        }
        else {
            position = adapter2!!.positionAdaptern
            resumes.clear()
            resumes = docLib.getAllResumes()
            adapter2!!.notifyItemChanged(position)
        }
    }
    private fun loadData() = runBlocking {
        val job = launch {

            val documentLib = DocumentsLib.get(requireActivity())
            val userLib = UsersLib.get(requireActivity())
            val respLib = DocResponsesLib.get(requireActivity())
            val getRequest = docApi.getDocumentsAsync()
            val getUserRequest = userApi.getUsersAsync()
            val getRespRequest = respApi.getAllDocResponseAsync()



            try {
                documentLib.docs = getRequest as MutableList<Document>
                userLib.users = getUserRequest as MutableList<User>
                respLib.responses = getRespRequest as MutableList<DocResponse>

            } catch (e: Exception) {
                Log.d(TAG, e.message.toString())
            }
        }
        job.join()
        loadDataFinished = true
    }
}
