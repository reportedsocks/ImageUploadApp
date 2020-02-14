package com.reportedsocks.imageuploadapp.domain.model

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity (tableName = "links")
data class Link (
    @PrimaryKey(autoGenerate = true) var id: Int,
    val link: String
){
    @Ignore
    constructor(link: String= "") : this(0, link)
}