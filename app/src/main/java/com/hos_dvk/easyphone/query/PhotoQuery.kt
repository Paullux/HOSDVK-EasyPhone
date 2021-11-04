package com.hos_dvk.easyphone.query

import android.content.ContentResolver
import android.content.ContentUris
import android.os.Build
import android.provider.MediaStore
import androidx.annotation.RequiresApi
import com.hos_dvk.easyphone.data_class.PhotoDataClass

class PhotoQuery {
    @RequiresApi(Build.VERSION_CODES.O)
    fun getAll(
        contentResolver: ContentResolver
    ): MutableList<PhotoDataClass> {

        val photoList: MutableList<PhotoDataClass> = mutableListOf()

        contentResolver.query(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            null,
            null,
            null,
            MediaStore.Images.Media.DATE_TAKEN + " DESC")?.use { cursor ->

            val idColumn =
                cursor.getColumnIndex(MediaStore.Images.Media._ID)
            val externUriColumn =
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI
            if (cursor.moveToFirst()) {
                do {
                    val id = cursor.getLong(idColumn)
                    val photoUri = ContentUris.withAppendedId(
                        externUriColumn,
                        id).toString()
                    val photoQueryResult = PhotoDataClass(id, photoUri)
                    photoList.add(photoQueryResult)
                } while (cursor.moveToNext())
            }
            cursor.close()
        }
        return photoList
    }
}
