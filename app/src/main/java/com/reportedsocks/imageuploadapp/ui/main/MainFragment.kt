package com.reportedsocks.imageuploadapp.ui.main

import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.reportedsocks.imageuploadapp.MyApp
import com.reportedsocks.imageuploadapp.R
import com.reportedsocks.imageuploadapp.ui.adapter.AdapterImage
import com.reportedsocks.imageuploadapp.ui.adapter.ImagesAdapter
import kotlinx.android.synthetic.main.main_activity.*
import kotlinx.android.synthetic.main.main_activity.view.*
import kotlinx.android.synthetic.main.main_fragment.*
import javax.inject.Inject

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var viewModel: MainViewModel
    private lateinit var adapter: ImagesAdapter
    private lateinit var listOfImages: List<AdapterImage>

    override fun onAttach(context: Context) {
        super.onAttach(context)
        // inject fragment with Factory
        (activity?.applicationContext as MyApp).appComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val toolbar = activity!!.toolbar
        toolbar.navigationIcon = null
        toolbar.toolbar_nav_button.visibility = View.VISIBLE

        //get ViewModel using injected Factory
        viewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)

        // check orientation and set number of columns in RecyclerView
        val orientation = resources.configuration.orientation
        if( orientation == Configuration.ORIENTATION_LANDSCAPE){
            main_fragment_images_list.layoutManager = GridLayoutManager(activity, 5)
        } else {
            main_fragment_images_list.layoutManager = GridLayoutManager(activity, 3)
        }

        // configure adapter for RecyclerView
        adapter = ImagesAdapter( viewModel , activity!!.baseContext)
        main_fragment_images_list.adapter = adapter

        // observe list of all images
        viewModel.getImageList()!!.observe(viewLifecycleOwner, Observer { list ->
            listOfImages = list
            adapter.setItems( listOfImages )
        })

        // observe error from imgur
        viewModel.getImgurErrorResponse().observe(viewLifecycleOwner, Observer { t ->
            if(t != null ) {
                   Toast.makeText(activity, t.message , Toast.LENGTH_LONG).show()
            }
        })

        // observe position of uploaded image to hide ProgressBar,
        // it will trigger both on success and error
        viewModel.getImagePosition().observe(viewLifecycleOwner, Observer { pos ->
            if(pos != null ) {
                listOfImages[pos].showProgressBar = false
                adapter.updateItem(listOfImages, pos)
            }
        })

        // observe click on the image and upload it to imgur
        viewModel.selectItemEvent.observe(viewLifecycleOwner, Observer { selected ->
            if (selected != null) {
                val pos: Int = selected[0] as Int
                viewModel.uploadImg(selected[1] as AdapterImage, pos)
                listOfImages[pos].showProgressBar = true
                adapter.updateItem(listOfImages, pos)
            }
        })
    }

}
