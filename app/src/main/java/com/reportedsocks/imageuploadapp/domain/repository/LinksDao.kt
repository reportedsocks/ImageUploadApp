package com.reportedsocks.imageuploadapp.domain.repository

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.reportedsocks.imageuploadapp.domain.model.Link
import io.reactivex.Observable

@Dao
interface LinksDao {
    @Query("SELECT * FROM links")
    fun getAll(): Observable<List<Link>>

    @Insert
    fun insertLink(link: Link)
}