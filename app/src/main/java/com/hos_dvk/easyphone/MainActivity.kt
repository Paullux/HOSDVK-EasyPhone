package com.hos_dvk.easyphone

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.hos_dvk.easyphone.module_activity.ContactActivity
import com.hos_dvk.easyphone.module_activity.DialerActivity
import com.hos_dvk.easyphone.module_activity.PhotoChoiceActivity
import com.hos_dvk.easyphone.module_activity.SmsListActivity
import com.hos_dvk.easyphone.widget.GoBack


//--------------------MainActivity-------------------------
class MainActivity : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //------------------Permissions------------------------------
        val permissionAll = 1
        val permissions: Array<String> = arrayOf(
            Manifest.permission.READ_CONTACTS,
            Manifest.permission.WRITE_CONTACTS,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_SMS,
            Manifest.permission.SEND_SMS,
            Manifest.permission.RECEIVE_SMS,
            Manifest.permission.CALL_PHONE,
            Manifest.permission.CAMERA
        )

        if (!hasPermissions(this, *permissions)) {
            ActivityCompat.requestPermissions(this, permissions, permissionAll)
        }

        val requiredReadContactPermission = Manifest.permission.READ_CONTACTS
        val requiredWriteContactPermission = Manifest.permission.WRITE_CONTACTS
        val requiredReadSmsPermission = Manifest.permission.READ_SMS
        val requiredReceiveSmsPermission = Manifest.permission.RECEIVE_SMS
        val requiredSendSmsPermission = Manifest.permission.SEND_SMS
        val requiredCallPermission = Manifest.permission.CALL_PHONE
        val requestReadExternalStoragePermission = Manifest.permission.READ_EXTERNAL_STORAGE
        val requestWriteExternalStoragePermission = Manifest.permission.WRITE_EXTERNAL_STORAGE
        val requestCameraPermission = Manifest.permission.CAMERA
        val checkValReadContact = checkSelfPermission(requiredReadContactPermission)
        val checkValWriteContact = checkSelfPermission(requiredWriteContactPermission)
        val checkValReadSms = checkSelfPermission(requiredReadSmsPermission)
        val checkValReceiveSms = checkSelfPermission(requiredReceiveSmsPermission)
        val checkValSendSms = checkSelfPermission(requiredSendSmsPermission)
        val checkValCall = checkSelfPermission(requiredCallPermission)
        val checkValReadExternalStorage = checkSelfPermission(requestReadExternalStoragePermission)
        val checkValWriteExternalStorage = checkSelfPermission(requestWriteExternalStoragePermission)
        val checkValCamera = checkSelfPermission(requestCameraPermission)
        if (checkValReadContact == PackageManager.PERMISSION_GRANTED &&
            checkValWriteContact == PackageManager.PERMISSION_GRANTED &&
            checkValReadSms == PackageManager.PERMISSION_GRANTED &&
            checkValReceiveSms == PackageManager.PERMISSION_GRANTED &&
            checkValSendSms == PackageManager.PERMISSION_GRANTED &&
            checkValCall == PackageManager.PERMISSION_GRANTED &&
            checkValReadExternalStorage == PackageManager.PERMISSION_GRANTED &&
            checkValWriteExternalStorage == PackageManager.PERMISSION_GRANTED &&
            checkValCamera == PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(
                this,
                getString(R.string.accept_permission),
                Toast.LENGTH_LONG
            ).show()
        }
    }
    private fun hasPermissions(context: Context, vararg permissions: String): Boolean = permissions.all {
        ActivityCompat.checkSelfPermission(context, it) == PackageManager.PERMISSION_GRANTED
    }
    //----------------basic function-------------------------

    fun dialer(@Suppress("UNUSED_PARAMETER")view: View) {
        Toast.makeText(this, getString(R.string.module_dialer), Toast.LENGTH_LONG).show()
        val intent = Intent(this, DialerActivity::class.java)
        startActivity(intent)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    fun contact(@Suppress("UNUSED_PARAMETER")view: View) {
        Toast.makeText(this, getString(R.string.module_contacts), Toast.LENGTH_LONG).show()
        val requiredPermission = Manifest.permission.READ_CONTACTS
        val checkVal = checkSelfPermission(requiredPermission)
        if (checkVal == PackageManager.PERMISSION_GRANTED) {
            val intent = Intent(this, ContactActivity::class.java)
            startActivity(intent)
        } else if (!firstContacts) {
            Toast.makeText(
                this,
                getString(R.string.refuse_contact),
                Toast.LENGTH_LONG
            ).show()
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    fun sms(@Suppress("UNUSED_PARAMETER")view: View) {
        Toast.makeText(this, getString(R.string.module_sms), Toast.LENGTH_LONG).show()
        val requiredPermissionReceive = Manifest.permission.RECEIVE_SMS
        val requiredPermissionSend =  Manifest.permission.SEND_SMS
        val requiredPermissionRead=  Manifest.permission.READ_SMS
        val checkValReceived = checkSelfPermission(requiredPermissionReceive)
        val checkValSend = checkSelfPermission(requiredPermissionSend)
        val checkValRead = checkSelfPermission(requiredPermissionRead)
        if (checkValRead == PackageManager.PERMISSION_GRANTED && checkValReceived == PackageManager.PERMISSION_GRANTED && checkValSend == PackageManager.PERMISSION_GRANTED) {
            val intent = Intent(this, SmsListActivity::class.java)
            startActivity(intent)
        } else if (!firstContacts) {
            Toast.makeText(
                this,
                getString(R.string.refuse_contact),
                Toast.LENGTH_LONG
            ).show()
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun photo(@Suppress("UNUSED_PARAMETER")view: View) {
        Toast.makeText(this, getString(R.string.module_photo), Toast.LENGTH_LONG).show()
        val intent = Intent(this, PhotoChoiceActivity::class.java)
        startActivity(intent)
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    fun goToOthersLaunchers(@Suppress("UNUSED_PARAMETER")view: View) {
        val callHomeSettingIntent = Intent(Settings.ACTION_HOME_SETTINGS)
        startActivity(callHomeSettingIntent)
    }

    //------------------Globals Function------------------------------
    fun goBack(@Suppress("UNUSED_PARAMETER")view: View) {
        GoBack().goBack(this)
    }
}



