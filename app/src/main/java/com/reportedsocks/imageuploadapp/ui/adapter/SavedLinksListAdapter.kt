package com.reportedsocks.imageuploadapp.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.reportedsocks.imageuploadapp.R
import com.reportedsocks.imageuploadapp.domain.model.Link
import com.reportedsocks.imageuploadapp.ui.savedlinks.SavedLinksViewModel
import kotlinx.android.synthetic.main.saved_links_list_item.view.*

class SavedLinksListAdapter(context: Context, objects: List<Link?>, private val viewModel: SavedLinksViewModel) :
    ArrayAdapter<Link?>(context, 0, objects) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var view = convertView
        val link: Link? = getItem(position)
        if (view == null) {
            view = LayoutInflater.from(context)
                .inflate(R.layout.saved_links_list_item, parent, false)
        }

        val linkText = view!!.saved_links_list_text
        linkText.text = link?.link?:"error"
        view.setOnClickListener {
            viewModel.selectItemEvent.value = listOf(position, link?:"error")
        }
        return view
    }
}