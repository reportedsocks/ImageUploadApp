package com.reportedsocks.imageuploadapp.data.repository

import android.graphics.Bitmap
import com.reportedsocks.imageuploadapp.domain.model.ImageResponse
import com.reportedsocks.imageuploadapp.domain.repository.ImgurAPI
import com.reportedsocks.imageuploadapp.util.BitmapHelper
import io.reactivex.Observable
import okhttp3.MultipartBody
import javax.inject.Inject


class ImgurApiRepository @Inject constructor( private val imgurAPI: ImgurAPI) {

    /**
     * Makes async call to upload image to imgur
     * @param image Bitmap
     */
    fun uploadImage( image: Bitmap ): Observable<ImageResponse>{
         return imgurAPI.postImage(
            "Client-ID ac3de6aa28eba20",
            null,
            null,
            null,
            null,
            MultipartBody.Part.createFormData("image", BitmapHelper().get64BaseImage(image))
        )
    }
}