package com.reportedsocks.imageuploadapp.ui.savedlinks

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.reportedsocks.imageuploadapp.data.repository.AppDatabase
import com.reportedsocks.imageuploadapp.domain.model.Link
import com.reportedsocks.imageuploadapp.ui.adapter.SingleLiveEvent
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SavedLinksViewModel @Inject constructor( private val appDatabase: AppDatabase) : ViewModel() {
    // observable for click event
    internal val selectItemEvent = SingleLiveEvent<List<Any>>()

    private var listOfAllLinks: MutableLiveData<List<Link>>? = null

    /**
     * Will return observable list of Links, if there is none, will create one and initialize DB query
     */
    fun getAllLinks(): MutableLiveData<List<Link>> {
        if( listOfAllLinks == null){
            listOfAllLinks = MutableLiveData()
            getLinksFromDb()
        }
        return  listOfAllLinks!!
    }

    // observes for changes in DB and updates LiveData
    @SuppressLint("CheckResult")
    private fun getLinksFromDb() {
        appDatabase.linksDao().getAll().subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe( {linksList ->
                listOfAllLinks!!.postValue(linksList)
            }, { error ->
                //do smth with error
                Log.d("MyLogs", "Error: $error")
            })
    }
}
