package com.reportedsocks.imageuploadapp.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.reportedsocks.imageuploadapp.R
import com.reportedsocks.imageuploadapp.ui.main.MainViewModel
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.image_list_item.view.*

/**
 * Adapter for RecyclerView in MainFragment
 * @param viewModel is needed to process onClick() event
 */
class ImagesAdapter (private val viewModel: MainViewModel, private val context: Context)
    : RecyclerView.Adapter<ImagesAdapter.ViewHolder>() {
    private var items = listOf<AdapterImage>()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.image_list_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]

        //probably would be nice to switch to glide later
        holder.itemView.image_list_image.setImageBitmap(item.bitmap)

        if(item.showProgressBar){
            holder.itemView.image_list_progressbar.visibility = View.VISIBLE
        } else {
            holder.itemView.image_list_progressbar.visibility = View.GONE
        }

        holder.itemView.setOnClickListener {
            viewModel.selectItemEvent.value = listOf(position, item)
        }

    }

    override fun getItemCount(): Int = items.size

    /**
     * Sets initial list of images, however can be used to update existing one
     * @param items List of AdapterImage objects which will be passed to RecyclerView
     */
    fun setItems(items: List<AdapterImage>) {
        this.items = items
        notifyDataSetChanged()
    }

    /**
     * Updates selected item in RecyclerView
     * @param items List of AdapterImage objects with 1 updated item
     * @param position position of updated item
     */
    fun updateItem(items: List<AdapterImage>, position: Int){
        this.items = items
        notifyItemChanged(position)
    }



    inner class ViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView),
        LayoutContainer
}