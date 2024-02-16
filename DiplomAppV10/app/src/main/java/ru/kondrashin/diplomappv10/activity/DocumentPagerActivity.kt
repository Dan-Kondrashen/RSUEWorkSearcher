package ru.kondrashin.diplomappv10.activity

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ru.kondrashin.diplomappv10.R
import ru.kondrashin.diplomappv10.fragment.DocumentFragment
import java.io.Serializable
import androidx.viewpager2.widget.ViewPager2
import ru.kondrashin.diplomappv10.adapter.DocumentPagerAdapter
import ru.kondrashin.diplomappv10.data_class.Document
import ru.kondrashin.diplomappv10.library_singleton.DocumentsLib


class DocumentPagerActivity: AppCompatActivity() {
    private lateinit var viewPager: ViewPager2
    private lateinit var documents: MutableList<Document>
    companion object{
        private const val EXTRA_DOC_ID = "activitys.doc_id"
        private const val EXTRA_USER_ID = "activitys.user_id"
        fun newIntent(packageContext: Context?, docId: Int): Intent? {
            val intent = Intent(packageContext, DocumentPagerActivity::class.java)
            intent.putExtra(EXTRA_DOC_ID, docId)
            return intent

        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pager_fragment)
//        val docId = intent.get_serializable_extra<Serializable>(EXTRA_DOC_ID)
        val docId = intent.getSerializableExtra(EXTRA_DOC_ID) as Int
//        val userId = intent.getSerializableExtra(EXTRA_USER_ID) as Int
        viewPager = findViewById(R.id.activity_document_page_view)
        viewPager.adapter = DocumentPagerAdapter(this, 1)

        documents = DocumentsLib.get(this).docs
        for(i in documents.indices)
            if(documents[i].id == docId){
                viewPager.currentItem = i
                break
            }
        makeAnim(R.anim.scaile_in_fragment_animation, R.anim.scalie_out_fragment_animation)
    }

    inline fun <reified T : Serializable> Intent.get_serializable_extra(key: String): T = when {
        Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU -> getSerializableExtra(key, T::class.java)!!
        else -> @Suppress("DEPRECATION") getSerializableExtra(key) as T
    }

    private fun makeAnim(startAnim: Int, endAnim: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
            overrideActivityTransition(
                OVERRIDE_TRANSITION_OPEN,
                startAnim, endAnim
            )
        } else {
            @Suppress("DEPRECATION")
            overridePendingTransition(
                startAnim,
                endAnim
            )
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        makeAnim(R.anim.scaile_in_fragment_animation, R.anim.scalie_out_fragment_animation)
    }

    fun restartActivity(doc: DocumentPagerActivity){
        doc.recreate()
    }
}