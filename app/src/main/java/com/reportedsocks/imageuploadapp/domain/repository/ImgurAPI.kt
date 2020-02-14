package com.reportedsocks.imageuploadapp.domain.repository

import com.reportedsocks.imageuploadapp.domain.model.ImageResponse
import io.reactivex.Observable
import okhttp3.MultipartBody
import retrofit2.http.*

interface ImgurAPI {

    /**
     * Upload image to Imgur
     * @param auth        #Type of authorization for upload
     * @param title       #Title of image
     * @param description #Description of image
     * @param albumId     #ID for album (if the user is adding this image to an album)
     * @param username    username for upload
     * @param file        image
     */
    @Multipart
    @POST("/3/image")
    fun postImage(
        @Header("Authorization") auth: String?,
        @Query("title") title: String?,
        @Query("description") description: String?,
        @Query("album") albumId: String?,
        @Query("account_url") username: String?,
        @Part file: MultipartBody.Part
    ) : Observable<ImageResponse>

    companion object {
        const val server = "https://api.imgur.com"
    }
}