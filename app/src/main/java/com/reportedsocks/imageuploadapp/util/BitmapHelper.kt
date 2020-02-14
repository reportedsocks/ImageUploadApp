package com.reportedsocks.imageuploadapp.util

import android.graphics.Bitmap
import android.util.Base64
import java.io.ByteArrayOutputStream


class BitmapHelper {
    /**
     * Converts bitmap to base64 String
     * @param bmp Bitmap to be converted
     */
    fun get64BaseImage(bmp: Bitmap): String {
        val baos = ByteArrayOutputStream()
        bmp.compress(Bitmap.CompressFormat.PNG, 100, baos)
        val imageBytes = baos.toByteArray()
        return Base64.encodeToString(imageBytes, Base64.DEFAULT)
    }
}