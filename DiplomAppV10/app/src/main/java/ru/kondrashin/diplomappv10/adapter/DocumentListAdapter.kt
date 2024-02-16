package ru.kondrashin.diplomappv10.adapter

import android.app.Activity
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.Button
import android.widget.Filter
import android.widget.Filterable

import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.kondrashin.diplomappv10.R
import ru.kondrashin.diplomappv10.activity.DocumentPagerActivity
import ru.kondrashin.diplomappv10.data_class.DocResponse
import ru.kondrashin.diplomappv10.data_class.Document
import ru.kondrashin.diplomappv10.data_class.User


class DocumentListAdapter(documents: MutableList<Document>, users: MutableList<User>, docresponses: MutableList<DocResponse>): RecyclerView.Adapter<RecyclerView.ViewHolder>(), Filterable {

    private var documents: MutableList<Document> = mutableListOf()
    private var documentsFilter: MutableList<Document> = mutableListOf()
    private var users: MutableList<User> = mutableListOf()
    private var docResponses: MutableList<DocResponse> = mutableListOf()
    private var lastIndexSelect: Int = -1
    var positionAdaptern: Int = 0

    init {
        this.documents = documents
        this.docResponses = docresponses
        this.documentsFilter = documents
        this.users = users
    }

    inner class ViewHolderForVacansys(item: View?): RecyclerView.ViewHolder(item!!), View.OnClickListener,
        View.OnLongClickListener{

        private lateinit var document: Document

        private var titleTextView: TextView =
            item!!.findViewById(R.id.list_item_document_title_text_view)
        private var dateTextView: TextView = item!!.findViewById(R.id.list_item_document_date_text_view)
        private var startOfSalaryTextView: TextView = item!!.findViewById(R.id.list_item_document_salary_start_text_view)
        private var userTextView: TextView = item!!.findViewById(R.id.list_item_document_author_text_view)
        private var buttonRespToItem: Button = item!!.findViewById(R.id.hideitem_button_resp)
        private var buttonHideItem: Button = item!!.findViewById(R.id.hideitem_button_hide)

        private var isOpened: Boolean = false

        fun bindDocument(doc: Document, users: List<User>){
            this.document = doc
            titleTextView.text = doc.title
            dateTextView.text =doc.date
            startOfSalaryTextView.text = doc.salary
            for (user in users)
                if (user.id == doc.userId)
                    userTextView.text = user.FIO
            itemView.setOnClickListener(this)
            itemView.setOnLongClickListener(this)

        }

        override fun onClick(v: View?) {
            val context = v!!.context
            val intent = DocumentPagerActivity.newIntent(context, document.id)
            val activity = context as Activity
            activity.startActivity(intent)
            positionAdaptern = adapterPosition
        }

        override fun onLongClick(v: View?): Boolean {
            isOpened = !isOpened
            buttonRespToItem.visibility = if (isOpened) VISIBLE else GONE
            buttonHideItem.visibility = if (isOpened) VISIBLE else GONE
            return true
        }
    }
    inner class ViewHolderForResumes(item: View?): RecyclerView.ViewHolder(item!!), View.OnClickListener,
        View.OnLongClickListener{

        private lateinit var document: Document

        private var titleTextView: TextView =
            item!!.findViewById(R.id.list_item_document_title_text_view)
        private var dateTextView: TextView = item!!.findViewById(R.id.list_item_document_date_text_view)
        private var startOfSalaryTextView: TextView = item!!.findViewById(R.id.list_item_document_salary_start_text_view)
        private var userTextView: TextView = item!!.findViewById(R.id.list_item_document_author_text_view)
        private var buttonRespToItem: Button = item!!.findViewById(R.id.hideitem_button_resp)
        private var buttonHideItem: Button = item!!.findViewById(R.id.hideitem_button_hide)

        private var isOpened: Boolean = false

        fun bindDocument(doc: Document, users: List<User>, docresponses: List<DocResponse>){
            this.document = doc
            titleTextView.text = doc.title
            dateTextView.text =doc.date
            startOfSalaryTextView.text = doc.salary
            for (user in users)
                if (user.id == doc.userId)
                    userTextView.text = user.FIO

            itemView.setOnClickListener(this)
            itemView.setOnLongClickListener(this)
            for(resp in docresponses)
                if (resp.docId == doc.id && resp.type == "Просмотр")
                    itemView.setBackgroundResource(R.drawable.border_for_dependence_item)
        }
//        val animation = ScaleAnimation(1f, 2f, 1f, 2f,
//            v.pivotX,v.pivotY)
//        animation.duration = 1000
//
//        animation.setAnimationListener(object : Animation.AnimationListener {
//            override fun onAnimationStart(p0: Animation?) {}
//            override fun onAnimationEnd(p0: Animation?) {
//                context.startActivity(intent)
//            }
//            override fun onAnimationRepeat(p0: Animation?) {}
//
//        })
//        itemView.startAnimation(animation)

        override fun onClick(v: View?) {
            val context = v!!.context
            val intent = DocumentPagerActivity.newIntent(context, document.id)

            positionAdaptern = adapterPosition
            val activity = context as Activity
            activity.startActivity(intent)
        }

        override fun onLongClick(v: View?): Boolean {
            isOpened = !isOpened
            buttonRespToItem.visibility = if (isOpened) VISIBLE else GONE
            buttonHideItem.visibility = if (isOpened) VISIBLE else GONE
            return true
        }
    }


    override fun getItemViewType(position: Int): Int {
        if (documents.get(position).type=="резюме")
            return 1
        else{
            return 2
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int
    ): RecyclerView.ViewHolder {

        if (viewType == 1){
            val layoutInflater = LayoutInflater.from(parent.context)
            val view = layoutInflater.inflate(
                R.layout.list_item_document_resume,
                parent, false)
            return ViewHolderForResumes(view)
        }
        else {
            val view = LayoutInflater.from(parent.context).inflate(
                R.layout.list_item_document_vacancy,
                parent, false
            )
            return ViewHolderForVacansys(view)
        }
    }

    fun setDocuments(docs: MutableList<Document>, users: MutableList<User>){

        this.documents = docs
        this.users = users
        this.documentsFilter = docs
    }


    override fun getItemCount() = documents.size ?: 0

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val holder1: ViewHolderForResumes
        val holder2: ViewHolderForVacansys
        val doc = documents[position]

        if(holder.itemViewType == 1){
            holder1 = holder as ViewHolderForResumes
            holder1.bindDocument(doc, users, docResponses)
        }
        else {
            holder2 = holder as ViewHolderForVacansys
            holder2.bindDocument(doc, users)


        }
    }

    override fun getFilter(): Filter {
        return  object  : Filter(){
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val resultOfFiltration = FilterResults()
                if(constraint == null || constraint.length <0){
                    resultOfFiltration.count = documentsFilter.size
                    resultOfFiltration.values = documentsFilter
                }else{
                    var str = constraint.toString().lowercase()
                    var docss = mutableListOf<Document>()
                    for(item in documentsFilter){
                        if(item.title.lowercase().contains(str)||item.contactinfo.lowercase().contains(str)||item.extra_info.lowercase().contains(str)){
                            docss.add(item)
                        }
                    }
                    resultOfFiltration.count = docss.size
                    resultOfFiltration.values = docss
                }
                return resultOfFiltration
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                documents =  results!!.values as MutableList<Document>
                notifyDataSetChanged()
            }

        }
    }
}