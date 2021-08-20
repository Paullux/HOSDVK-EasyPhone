package com.hos_dvk.easyphone.module_activity

import android.content.Intent
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.hos_dvk.easyphone.R
import com.hos_dvk.easyphone.query.CameraQuery

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
        val packageNameCamera = CameraQuery().getPackageName(this)
        if (packageNameCamera != "") {
            val launchIntent = packageManager.getLaunchIntentForPackage(packageNameCamera)
            startActivity(launchIntent)
        } else {
            Toast.makeText(
                this,
                getString(R.string.not_detect_cam_app), Toast.LENGTH_LONG
            ).show()
        }
    }

    fun goBack(@Suppress("UNUSED_PARAMETER")view: View) {
        this.finish()
    }
}