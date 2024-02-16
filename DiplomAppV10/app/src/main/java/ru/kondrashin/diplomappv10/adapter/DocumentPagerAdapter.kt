package ru.kondrashin.diplomappv10.adapter
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import ru.kondrashin.diplomappv10.data_class.Document
import ru.kondrashin.diplomappv10.fragment.DocumentFragment
import ru.kondrashin.diplomappv10.library_singleton.DocumentsLib

class DocumentPagerAdapter(activity: AppCompatActivity, userId: Int): FragmentStateAdapter(activity) {
    private val documents: List<Document> = DocumentsLib.get(activity).docs
    private var idUser = userId
    override fun getItemCount() =documents.size
    override fun createFragment(position: Int)= DocumentFragment.newInstence(documents[position].id,idUser)
}