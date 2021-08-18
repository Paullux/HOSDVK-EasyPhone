package com.hos_dvk.easyphone.module_activity

import android.database.MatrixCursor
import android.os.Build
import android.view.View
import android.widget.ListView
import android.widget.SimpleCursorAdapter
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.hos_dvk.easyphone.R
import com.hos_dvk.easyphone.data_class.PhotoDataClass
import com.hos_dvk.easyphone.query.PhotoQuery
import com.hos_dvk.easyphone.widget.GoBack

class GalleryActivity : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onResume() {
        super.onResume()
        setContentView(R.layout.activity_gallery)
        loadPhoto()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun loadPhoto() {
        val galleryListView = findViewById<ListView>(R.id.gallery_list)
        val galleryList: MutableList<PhotoDataClass> =
            PhotoQuery().getAll(contentResolver)
        val mc = MatrixCursor(
            arrayOf(
                "_id",
                "photoUri"
            ), 16
        )
        val from = arrayOf(
            "_id",
            "photoUri")

        val to = intArrayOf(R.id.id_photo, R.id.gallery_picture)

        for (gallery in galleryList) {
            mc.addRow(arrayOf(gallery.id, gallery.photoURI))
        }

        val simple = SimpleCursorAdapter(this, R.layout.style_of_gallery_list, mc, from, to, 0)
        galleryListView.adapter = simple
    }

    fun goBack(@Suppress("UNUSED_PARAMETER")view: View) {
        GoBack().goBack(this)
    }
}