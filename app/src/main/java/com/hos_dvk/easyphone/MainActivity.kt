package com.hos_dvk.easyphone

import android.Manifest
import android.app.AlertDialog
import android.content.ActivityNotFoundException
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.hos_dvk.easyphone.module_activity.ContactActivity
import com.hos_dvk.easyphone.module_activity.DialerActivity
import com.hos_dvk.easyphone.module_activity.SmsActivity
import com.hos_dvk.easyphone.widget.GoBack

//--------------------MainActivity-------------------------
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
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
        requestContactPermission()
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
        requestSmsPermission()
        val requiredPermissionReceive = Manifest.permission.RECEIVE_SMS
        val requiredPermissionSend =  Manifest.permission.SEND_SMS
        val checkValReceived = checkSelfPermission(requiredPermissionReceive)
        val checkValSend = checkSelfPermission(requiredPermissionSend)
        if (checkValReceived == PackageManager.PERMISSION_GRANTED && checkValSend == PackageManager.PERMISSION_GRANTED) {
            val intent = Intent(this, SmsActivity::class.java)
            startActivity(intent)
            val realSms = Intent(this, SmsActivity::class.java).apply {
                putExtra(CONTACT_TO_SMS, "")
            }
            startActivity(realSms)
        } else if (!firstContacts) {
            Toast.makeText(
                this,
                getString(R.string.refuse_contact),
                Toast.LENGTH_LONG
            ).show()
        }
    }

    fun photo(@Suppress("UNUSED_PARAMETER")view: View) {
        Toast.makeText(this, getString(R.string.module_photo), Toast.LENGTH_LONG).show()
        dispatchTakePictureIntent()
    }
    private fun dispatchTakePictureIntent() {
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        try {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
        } catch (e: ActivityNotFoundException) {
            // display error state to the user
        }
    }
    //------------------Globals Function------------------------------
    fun goBack(@Suppress("UNUSED_PARAMETER")view: View) {
        GoBack().goBack(this)
    }

    //-----------Permissions functions------------------------
    private fun requestContactPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.READ_CONTACTS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(
                        this,
                        Manifest.permission.READ_CONTACTS
                    )
                ) {
                    val builder: AlertDialog.Builder = AlertDialog.Builder(this)
                    builder.setTitle(getString(R.string.contact_perm))
                    builder.setPositiveButton(android.R.string.ok, null)
                    builder.setMessage(getString(R.string.could_contact_perm))
                    builder.setOnDismissListener {
                        requestPermissions(
                            arrayOf(
                                Manifest.permission.READ_CONTACTS
                            ), PERMISSIONS_REQUEST_READ_CONTACTS
                        )
                    }
                    builder.show()
                } else {
                    ActivityCompat.requestPermissions(
                        this, arrayOf(Manifest.permission.READ_CONTACTS),
                        PERMISSIONS_REQUEST_READ_CONTACTS
                    )
                }
            }
        }
    }

    private fun requestSmsPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.RECEIVE_SMS
                ) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.SEND_SMS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(
                        this,
                        Manifest.permission.RECEIVE_SMS
                    ) &&
                    ActivityCompat.shouldShowRequestPermissionRationale(
                        this,
                        Manifest.permission.SEND_SMS
                    )
                ) {
                    val builder: AlertDialog.Builder = AlertDialog.Builder(this)
                    builder.setTitle(getString(R.string.sms_perm))
                    builder.setPositiveButton(android.R.string.ok, null)
                    builder.setMessage(getString(R.string.could_call_sms))
                    builder.setOnDismissListener {
                        requestPermissions(
                            arrayOf(
                                Manifest.permission.RECEIVE_SMS, Manifest.permission.SEND_SMS
                            ), PERMISSIONS_REQUEST_RECEIVE_SMS
                        )
                    }
                    builder.show()
                } else {
                    ActivityCompat.requestPermissions(
                        this, arrayOf(Manifest.permission.RECEIVE_SMS, Manifest.permission.SEND_SMS),
                        PERMISSIONS_REQUEST_RECEIVE_SMS
                    )
                }
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray,
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            PERMISSIONS_REQUEST_READ_CONTACTS -> {
                if (grantResults.isNotEmpty()
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED
                ) {
                    val intent = Intent(this, ContactActivity::class.java)
                    startActivity(intent)
                } else {
                    Toast.makeText(
                        this,
                        getString(R.string.refuse_contact),
                        Toast.LENGTH_LONG
                    ).show()
                }
                return
            }
            PERMISSIONS_REQUEST_WRITE_CONTACTS -> {
                if (grantResults.isNotEmpty()
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED
                ) {
                    Toast.makeText(
                        this,
                        getString(R.string.accept_contact_write),
                        Toast.LENGTH_LONG
                    ).show()
                } else {
                    Toast.makeText(
                        this,
                        getString(R.string.refuse_contact_write),
                        Toast.LENGTH_LONG
                    ).show()
                }
                return
            }
            PERMISSIONS_REQUEST_CALL_PHONE -> {
                if (grantResults.isNotEmpty()
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED
                ) {
                    val number =
                        findViewById<Button>(R.id.i_choice_sms).text.toString()
                    val intent = Intent(Intent.ACTION_CALL, Uri.parse("tel:$number"))
                    startActivity(intent)
                } else {
                    Toast.makeText(
                        this,
                        getString(R.string.refuse_contact),
                        Toast.LENGTH_LONG
                    ).show()
                }
                return
            }
            PERMISSIONS_REQUEST_RECEIVE_SMS -> {
                if (grantResults.isNotEmpty()
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED
                ) {
                    val realSms = Intent(this, SmsActivity::class.java).apply {
                        putExtra(CONTACT_TO_SMS, "")
                    }
                    startActivity(realSms)
                } else {
                    Toast.makeText(
                        this,
                        getString(R.string.refuse_sms),
                        Toast.LENGTH_LONG
                    ).show()
                }
                return
            }
        }
    }
}



