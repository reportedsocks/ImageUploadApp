package com.reportedsocks.imageuploadapp.ui.savedlinks

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.reportedsocks.imageuploadapp.MyApp
import com.reportedsocks.imageuploadapp.R
import com.reportedsocks.imageuploadapp.data.repository.AppDatabase
import com.reportedsocks.imageuploadapp.domain.model.Link
import com.reportedsocks.imageuploadapp.ui.adapter.SavedLinksListAdapter
import kotlinx.android.synthetic.main.main_activity.*
import kotlinx.android.synthetic.main.main_activity.view.*
import kotlinx.android.synthetic.main.saved_links_fragment.*
import javax.inject.Inject

class SavedLinksFragment : Fragment() {

    companion object {
        fun newInstance() = SavedLinksFragment()
    }

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    @Inject
    lateinit var appDatabase: AppDatabase

    private lateinit var viewModel: SavedLinksViewModel
    private lateinit var listOfLinks: List<Link>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.saved_links_fragment, container, false)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (activity?.applicationContext as MyApp).appComponent.inject(this)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        //set toolbar parameters
        val toolbar = activity!!.toolbar
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp)
        toolbar.setNavigationOnClickListener { activity!!.onBackPressed() }
        toolbar.toolbar_nav_button.visibility = View.GONE

        viewModel = ViewModelProvider(this, viewModelFactory).get(SavedLinksViewModel::class.java)

        //observe LiveData with list of Links
        viewModel.getAllLinks().observe(viewLifecycleOwner, Observer { linksList ->
            listOfLinks = linksList
            saved_links_fragment_list.adapter =
                SavedLinksListAdapter(activity!!.baseContext, listOfLinks, viewModel)
        })

        // onClick event for list item
        viewModel.selectItemEvent.observe(viewLifecycleOwner, Observer { selected ->
            if (selected != null) {
                val link: Link = selected[1] as Link
                openWebPage(link.link)
            }
        })
    }

    private fun openWebPage(url: String) {
        val webPage: Uri = Uri.parse(url)
        val intent = Intent(Intent.ACTION_VIEW, webPage)
        if (intent.resolveActivity(activity!!.packageManager) != null) {
            startActivity(intent)
        }
    }

}
