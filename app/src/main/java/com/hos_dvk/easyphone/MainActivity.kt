package com.hos_dvk.easyphone

import android.Manifest
import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
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
        requestReadContactPermission()
        val requiredReadContactPermission = Manifest.permission.READ_CONTACTS
        val requiredWriteContactPermission = Manifest.permission.WRITE_CONTACTS
        val requiredReadSmsPermission = Manifest.permission.READ_SMS
        val requiredReceiveSmsPermission = Manifest.permission.RECEIVE_SMS
        val requiredSendSmsPermission = Manifest.permission.SEND_SMS
        val requiredCallPermission = Manifest.permission.CALL_PHONE
        val requestReadExternalStoragePermission = Manifest.permission.READ_EXTERNAL_STORAGE
        val checkValReadContact = checkSelfPermission(requiredReadContactPermission)
        val checkValWriteContact = checkSelfPermission(requiredWriteContactPermission)
        val checkValReadSms = checkSelfPermission(requiredReadSmsPermission)
        val checkValReceiveSms = checkSelfPermission(requiredReceiveSmsPermission)
        val checkValSendSms = checkSelfPermission(requiredSendSmsPermission)
        val checkValCall = checkSelfPermission(requiredCallPermission)
        val checkValReadExternalStorage = checkSelfPermission(requestReadExternalStoragePermission)
        if (checkValReadContact == PackageManager.PERMISSION_GRANTED &&
            checkValWriteContact == PackageManager.PERMISSION_GRANTED &&
            checkValReadSms == PackageManager.PERMISSION_GRANTED &&
            checkValReceiveSms == PackageManager.PERMISSION_GRANTED &&
            checkValSendSms == PackageManager.PERMISSION_GRANTED &&
            checkValCall == PackageManager.PERMISSION_GRANTED &&
            checkValReadExternalStorage == PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(
                this,
                getString(R.string.accept_permission),
                Toast.LENGTH_LONG
            ).show()
        } else if (!firstCall) {
            requestReadContactPermission()
            Toast.makeText(
                this,
                getString(R.string.refuse_permission),
                Toast.LENGTH_LONG
            ).show()
            this.finish()
        }
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

    fun applicationsInPhone(@Suppress("UNUSED_PARAMETER")view: View) {
        //startActivity(Intent(Settings.ACTION_SETTINGS))
        /**val intent = Intent(Intent.ACTION_MAIN, null)
        intent.addCategory(Intent.CATEGORY_LAUNCHER)
        startActivity(intent)**/

        var localIntent2: Intent? = Intent(Intent.ACTION_PICK_ACTIVITY)
        val localIntent3 = Intent(Intent.ACTION_MAIN, null)
        localIntent3.addCategory(Intent.CATEGORY_LAUNCHER)
        localIntent2!!.putExtra(Intent.EXTRA_INTENT, localIntent3)
        startActivity(localIntent2)
    }

    //------------------Globals Function------------------------------
    fun goBack(@Suppress("UNUSED_PARAMETER")view: View) {
        GoBack().goBack(this)
    }

    //-----------Permissions functions------------------------
    private fun requestReadContactPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.READ_CONTACTS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(
                        this,
                        Manifest.permission.READ_CONTACTS
                    )) {
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
    private fun requestWriteContactPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.WRITE_CONTACTS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(
                        this,
                        Manifest.permission.WRITE_CONTACTS
                    )) {
                    requestPermissions(
                        arrayOf(
                            Manifest.permission.WRITE_CONTACTS
                        ), PERMISSIONS_REQUEST_WRITE_CONTACTS
                    )
                } else {
                    ActivityCompat.requestPermissions(
                        this, arrayOf(Manifest.permission.WRITE_CONTACTS),
                        PERMISSIONS_REQUEST_WRITE_CONTACTS
                    )
                }
            }
        }
    }
    private fun requestReadSmsPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.READ_SMS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(
                        this,
                        Manifest.permission.READ_SMS
                    )) {
                    val builder: AlertDialog.Builder = AlertDialog.Builder(this)
                    builder.setTitle(getString(R.string.sms_perm))
                    builder.setPositiveButton(android.R.string.ok, null)
                    builder.setMessage(getString(R.string.could_call_sms))
                    builder.setOnDismissListener {
                        requestPermissions(
                            arrayOf(
                                Manifest.permission.READ_SMS
                            ), PERMISSIONS_REQUEST_READ_SMS
                        )
                    }
                    builder.show()
                } else {
                    ActivityCompat.requestPermissions(
                        this, arrayOf(Manifest.permission.READ_SMS),
                        PERMISSIONS_REQUEST_READ_SMS
                    )
                }
            }
        }
    }
    private fun requestReceiveSmsPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.RECEIVE_SMS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(
                        this,
                        Manifest.permission.RECEIVE_SMS
                    )) {
                    requestPermissions(
                        arrayOf(
                            Manifest.permission.RECEIVE_SMS
                        ), PERMISSIONS_REQUEST_RECEIVE_SMS
                    )
                } else {
                    ActivityCompat.requestPermissions(
                        this, arrayOf(
                            Manifest.permission.RECEIVE_SMS
                        ),
                        PERMISSIONS_REQUEST_RECEIVE_SMS
                    )
                }
            }
        }
    }
    private fun requestSendSmsPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.SEND_SMS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(
                        this,
                        Manifest.permission.SEND_SMS
                    )) {
                    requestPermissions(
                        arrayOf(
                            Manifest.permission.SEND_SMS
                        ), PERMISSIONS_REQUEST_SEND_SMS
                    )
                } else {
                    ActivityCompat.requestPermissions(
                        this, arrayOf(
                            Manifest.permission.SEND_SMS
                        ),
                        PERMISSIONS_REQUEST_SEND_SMS
                    )
                }
            }
        }
    }
    private fun requestCallPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.CALL_PHONE
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(
                        this,
                        Manifest.permission.CALL_PHONE
                    )) {
                    val builder: AlertDialog.Builder = AlertDialog.Builder(this)
                    builder.setTitle(getString(R.string.call_perm))
                    builder.setPositiveButton(android.R.string.ok, null)
                    builder.setMessage(getString(R.string.could_call_perm))
                    builder.setOnDismissListener {
                        requestPermissions(
                            arrayOf(
                                Manifest.permission.CALL_PHONE
                            ), PERMISSIONS_REQUEST_CALL_PHONE
                        )
                    }
                    builder.show()
                } else {
                    ActivityCompat.requestPermissions(
                        this, arrayOf(Manifest.permission.CALL_PHONE),
                        PERMISSIONS_REQUEST_CALL_PHONE
                    )
                }
            }
        }
    }
    private fun requestReadExternalStoragePermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.READ_EXTERNAL_STORAGE
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(
                        this,
                        Manifest.permission.READ_EXTERNAL_STORAGE
                    )) {
                    val builder: AlertDialog.Builder = AlertDialog.Builder(this)
                    builder.setTitle(getString(R.string.call_perm))
                    builder.setPositiveButton(android.R.string.ok, null)
                    builder.setMessage(getString(R.string.could_read_external_storage_perm))
                    builder.setOnDismissListener {
                        requestPermissions(
                            arrayOf(
                                Manifest.permission.READ_EXTERNAL_STORAGE
                            ), PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE
                        )
                    }
                    builder.show()
                } else {
                    ActivityCompat.requestPermissions(
                        this, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                        PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE
                    )
                }
            }
        }
    }
    @RequiresApi(Build.VERSION_CODES.M)
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
                    requestWriteContactPermission()
                } else {
                }
                return
            }
            PERMISSIONS_REQUEST_WRITE_CONTACTS -> {
                if (grantResults.isNotEmpty()
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED
                ) {
                    requestReadSmsPermission()
                } else {
                }
                return
            }
            PERMISSIONS_REQUEST_READ_SMS -> {
                if (grantResults.isNotEmpty()
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED
                ) {
                    requestReceiveSmsPermission()
                } else {
                }
                return
            }
            PERMISSIONS_REQUEST_RECEIVE_SMS -> {
                if (grantResults.isNotEmpty()
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED
                ) {
                    requestSendSmsPermission()
                } else {
                }
                return
            }
            PERMISSIONS_REQUEST_SEND_SMS -> {
                if (grantResults.isNotEmpty()
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED
                ) {
                    requestCallPermission()
                } else {
                }
                return
            }
            PERMISSIONS_REQUEST_CALL_PHONE -> {
                if (grantResults.isNotEmpty()
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED
                ) {
                    requestReadExternalStoragePermission()
                } else {
                }
                return
            }
            PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE -> {
                if (grantResults.isNotEmpty()
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED
                ) {
                    val requiredReadContactPermission = Manifest.permission.READ_CONTACTS
                    val requiredWriteContactPermission = Manifest.permission.WRITE_CONTACTS
                    val requiredReadSmsPermission = Manifest.permission.READ_SMS
                    val requiredReceiveSmsPermission = Manifest.permission.RECEIVE_SMS
                    val requiredSendSmsPermission = Manifest.permission.SEND_SMS
                    val requiredCallPermission = Manifest.permission.CALL_PHONE
                    val requestReadExternalStoragePermission = Manifest.permission.READ_EXTERNAL_STORAGE
                    val checkValReadContact = checkSelfPermission(requiredReadContactPermission)
                    val checkValWriteContact = checkSelfPermission(requiredWriteContactPermission)
                    val checkValReadSms = checkSelfPermission(requiredReadSmsPermission)
                    val checkValReceiveSms = checkSelfPermission(requiredReceiveSmsPermission)
                    val checkValSendSms = checkSelfPermission(requiredSendSmsPermission)
                    val checkValCall = checkSelfPermission(requiredCallPermission)
                    val checkValReadExternalStorage = checkSelfPermission(requestReadExternalStoragePermission)
                    if (checkValReadContact == PackageManager.PERMISSION_GRANTED &&
                        checkValWriteContact == PackageManager.PERMISSION_GRANTED &&
                        checkValReadSms == PackageManager.PERMISSION_GRANTED &&
                        checkValReceiveSms == PackageManager.PERMISSION_GRANTED &&
                        checkValSendSms == PackageManager.PERMISSION_GRANTED &&
                        checkValCall == PackageManager.PERMISSION_GRANTED &&
                        checkValReadExternalStorage == PackageManager.PERMISSION_GRANTED) {
                        Toast.makeText(
                            this,
                            getString(R.string.accept_permission),
                            Toast.LENGTH_LONG
                        ).show()
                    } else {
                        Toast.makeText(
                            this,
                            getString(R.string.refuse_permission),
                            Toast.LENGTH_LONG
                        ).show()}
                } else {
                    Toast.makeText(
                        this,
                        getString(R.string.refuse_permission),
                        Toast.LENGTH_LONG
                    ).show()
                    this.finish()
                }
                return
            }
        }
    }
}



