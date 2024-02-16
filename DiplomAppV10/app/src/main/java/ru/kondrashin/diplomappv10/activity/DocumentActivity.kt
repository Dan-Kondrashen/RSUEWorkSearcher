package ru.kondrashin.diplomappv10.activity

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import ru.kondrashin.diplomappv10.R
import ru.kondrashin.diplomappv10.fragment.DocumentFragment
import java.io.Serializable
import androidx.viewpager2.widget.ViewPager2
import ru.kondrashin.diplomappv10.data_class.Document


class DocumentActivity: SingleFragmentActivity() {

    private lateinit var viewPager: ViewPager2
    private lateinit var documents: MutableList<Document>
    companion object{
        private const val EXTRA_DOC_ID = "activitys.doc_id"
        private const val EXTRA_USER_ID = "activitys.user_id"
        fun newIntent(packageContext: Context?, docId: Int): Intent? {
            val intent = Intent(packageContext, DocumentActivity::class.java)
            intent.putExtra(EXTRA_DOC_ID, docId)
            return intent

        }
    }

//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_pager_fragment)
////        val docId = intent.get_serializable_extra<Serializable>(EXTRA_DOC_ID)
//        val docId = intent.getSerializableExtra(EXTRA_DOC_ID)
//        val userId = intent.getSerializableExtra(EXTRA_DOC_ID)
//    }

    inline fun <reified T : Serializable> Intent.get_serializable_extra(key: String): T = when {
        Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU -> getSerializableExtra(key, T::class.java)!!
        else -> @Suppress("DEPRECATION") getSerializableExtra(key) as T
    }


    override fun onBackPressed() {
        super.onBackPressed()
        makeAnim(R.anim.scaile_in_fragment_animation, R.anim.scalie_out_fragment_animation)
    }

//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_fragment)
//        val fm = supportFragmentManager
//        var fragment = fm.findFragmentById(R.id.fragmentContainer)
//        if (fragment == null){
//            fragment = createFragment()
//            fm.beginTransaction().add(R.id.fragmentContainer, fragment)
//                .commit()
//        }
//    overridePendingTransition(R.anim.scalie_in_fragment_animation, R.anim.scalie_out_fragment_animation)
//    }



    override fun createFragment(): DocumentFragment {
        val docId = intent.getSerializableExtra(EXTRA_DOC_ID) as Int
//        val docId = intent.getSerializableExtra(EXTRA_DOC_ID) as Int
//        val userId = intent.getSerializableExtra(EXTRA_USER_ID) as Int
        return DocumentFragment.newInstence(docId,1)
    }
    fun restartActivity(doc: DocumentActivity){
        doc.recreate()
    }
}