package com.reportedsocks.imageuploadapp.ui.main

import android.annotation.SuppressLint
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.reportedsocks.imageuploadapp.data.repository.AppDatabase
import com.reportedsocks.imageuploadapp.data.repository.ImgurApiRepository
import com.reportedsocks.imageuploadapp.data.repository.StoredImagesRepositoryImpl
import com.reportedsocks.imageuploadapp.domain.model.Link
import com.reportedsocks.imageuploadapp.ui.adapter.AdapterImage
import com.reportedsocks.imageuploadapp.ui.adapter.SingleLiveEvent
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.*
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.coroutines.CoroutineContext

/**
 * ViewModel for the MainFragment. Serves to supply it with list of images
 * and then update after image upload to imgur
 * @param storedImagesRepositoryImpl used to get list of user's images from gallery
 * @param imgurApiRepository used to upload image to imgur
 */
@Singleton
class MainViewModel @Inject constructor(
    private val storedImagesRepositoryImpl: StoredImagesRepositoryImpl,
    private val imgurApiRepository: ImgurApiRepository,
    private val appDatabase: AppDatabase
)
    : ViewModel(), CoroutineScope {

    // job for coroutine
    private val job = Job()
    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Main

    // MainFragment will observe these LiveData objects
    private var imagesLiveData: MutableLiveData<List<AdapterImage>>? = null
    private var imgurErrorResponseLiveData: MutableLiveData<Throwable> = MutableLiveData()
    private var imagePositionLiveData: MutableLiveData<Int> = MutableLiveData()

    //observable event for RecyclerView item click
    internal val selectItemEvent = SingleLiveEvent<List<Any>>()

    // clear job
    override fun onCleared() {
        job.complete()
        super.onCleared()
    }

    /**
     * Returns observable LiveData with list of images
     * if LiveData is null, will create one and call getAllImages() before returning
     */
    fun getImageList(): MutableLiveData<List<AdapterImage>>? {
        if( imagesLiveData == null){
            imagesLiveData = MutableLiveData()
            getAllImages()
        }
        return imagesLiveData
    }


    /**
     * Returns observable error after image upload
     */
    fun getImgurErrorResponse(): MutableLiveData<Throwable>{
        return imgurErrorResponseLiveData
    }

    /**
     * Returns observable position of uploaded image after request was completed
     */
    fun getImagePosition(): MutableLiveData<Int> {
        return imagePositionLiveData
    }

    /**
     * Calls uploadImage() from ImgurApiRepository
     * @param img an object of AdapterImage
     * @param position int position in recyclerView of uploaded img
     */
    @SuppressLint("CheckResult")
    fun uploadImg(img: AdapterImage, position: Int){
        imgurApiRepository.uploadImage(img.bitmap)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ response ->
                if(response.success){
                    imagePositionLiveData.postValue(position)
                    saveLink(response.data!!.link)
                }
            }, { error ->
               imgurErrorResponseLiveData.postValue(error)
               imagePositionLiveData.postValue(position)
            })
    }

    /**
     * Inserts link to the DB after it was uploaded to imgur
     * @param link link to be inserted
     */
    private fun saveLink(link: String) {
        val linkObj = Link(link)
        launch (Dispatchers.Main){
            withContext(Dispatchers.IO){
                appDatabase.linksDao().insertLink(linkObj)
            }
        }

    }

    /**
     * calls loadImagesFromStorage() from StoredImagesRepositoryImpl in a coroutine
     */
    private fun getAllImages() {
        launch(Dispatchers.Main) {
            imagesLiveData!!.value = withContext(Dispatchers.IO) {
                storedImagesRepositoryImpl.loadImagesFromStorage()
            }
        }
    }

}
