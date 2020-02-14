package com.reportedsocks.imageuploadapp.domain.model

/**
 * data class to parse response from imgur
 */
 data class ImageResponse (
     var success: Boolean,
     var status: Int,
     var data: UploadedImage? ) {


    data class UploadedImage(
        var id: String?,
        var title: String?,
        var description: String?,
        var type: String?,
        var animated: Boolean,
        var width: Int,
        var height: Int,
        var size: Int,
        var views: Int,
        var bandwidth: Int,
        var vote: String?,
        var favorite: Boolean,
        var account_url: String?,
        var deletehash: String?,
        var name: String?,
        var link: String )
}
