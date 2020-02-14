package com.reportedsocks.imageuploadapp.data.repository

import android.content.ContentUris
import android.content.Context
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.provider.MediaStore
import com.reportedsocks.imageuploadapp.domain.repository.StoredImagesRepository
import com.reportedsocks.imageuploadapp.ui.adapter.AdapterImage
import javax.inject.Inject

/**
 * Gets all images from user library
 *
 * Required Storage Permission
 *
 * @return List with AdapterImage objects
 */
class StoredImagesRepositoryImpl @Inject constructor(private val context: Context): StoredImagesRepository {
    override fun loadImagesFromStorage(): List<AdapterImage> {

        val uri: Uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        val cursor: Cursor?
        val columnIndexId: Int
        val listOfAllImages = mutableListOf<AdapterImage>()
        val projection = arrayOf(MediaStore.Images.Media._ID)
        cursor = context.contentResolver
            .query( uri, projection, null, null, null)

        if ( cursor != null ){
            columnIndexId = cursor.getColumnIndexOrThrow(MediaStore.Images.Media._ID)
            while (cursor.moveToNext()){

                val contentUri = ContentUris.withAppendedId(uri, cursor.getLong(columnIndexId))
                // initially I planned to get the absolute path to image but MediaStore.Images.Media.DATA
                // got depricated so here I obtain a bitmap
                var image: Bitmap
                context.contentResolver.openFileDescriptor(contentUri, "r").use { pfd ->
                    if( pfd != null ){
                        image = BitmapFactory.decodeFileDescriptor(pfd.fileDescriptor)
                        listOfAllImages.add(AdapterImage(image))
                    }
                }

            }
            cursor.close()
        }

        return listOfAllImages
    }
}