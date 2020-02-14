package com.reportedsocks.imageuploadapp.domain.repository

import com.reportedsocks.imageuploadapp.ui.adapter.AdapterImage

/**
 * Interface which will be implemented by and used to obtain user's image gallery
 */
interface StoredImagesRepository {
    fun loadImagesFromStorage(): List<AdapterImage>
}