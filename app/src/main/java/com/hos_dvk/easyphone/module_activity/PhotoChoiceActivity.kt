package com.hos_dvk.easyphone.module_activity

import android.content.ActivityNotFoundException
import android.content.Intent
import android.provider.MediaStore
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.hos_dvk.easyphone.PERMISSIONS_REQUEST_IMAGE_CAPTURE
import com.hos_dvk.easyphone.R
import com.hos_dvk.easyphone.widget.GoBack

class PhotoChoiceActivity : AppCompatActivity() {
    override fun onResume() {
        super.onResume()
        setContentView(R.layout.activity_photo_choice)
    }
    fun chooseGallery(@Suppress("UNUSED_PARAMETER")view: View) {
        val intent = Intent(this, GalleryActivity::class.java)
        startActivity(intent)
    }
    fun chooseCamera(@Suppress("UNUSED_PARAMETER")view: View) {
        dispatchTakePictureIntent()
    }
    private fun dispatchTakePictureIntent() {
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        try {
            startActivityForResult(takePictureIntent, PERMISSIONS_REQUEST_IMAGE_CAPTURE)
        } catch (e: ActivityNotFoundException) {
            // display error state to the user
        }
    }
    fun goBack(@Suppress("UNUSED_PARAMETER")view: View) {
        GoBack().goBack(this)
    }
}