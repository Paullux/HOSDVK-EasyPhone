package com.hos_dvk.easyphone.module_activity

import android.Manifest
import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.hos_dvk.easyphone.*
import com.hos_dvk.easyphone.data_class.ContactDataClass
import com.hos_dvk.easyphone.query.ContactQuery
import com.hos_dvk.easyphone.widget.GoBack

class RealCallActivity : AppCompatActivity() {
    override fun onResume() {
        super.onResume()
        setContentView(R.layout.activity_real_call)
        val realCall = intent
        name = realCall?.getStringExtra(NAME_TO_CALL).toString()
        number = realCall?.getStringExtra(NUMBER_TO_CALL).toString()

        val contactsList: MutableList<ContactDataClass> =
            ContactQuery().getAll(contentResolver, this)
        for (contact in contactsList) {
            if (contact.number.replace("+33","0") == number.replace("+33","0")) name = contact.name
        }
        findViewById<TextView>(R.id.i_choice_call).text = getString(R.string.call, name)
        findViewById<TextView>(R.id.i_choice_sms).text = getString(R.string.sms, number)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    fun callChoice(@Suppress("UNUSED_PARAMETER")view: View) {
        if (name != "" && number != "") {
            requestCallPermission()
            Toast.makeText(this, getString(R.string.call, name), Toast.LENGTH_LONG).show()
            val requiredPermission = Manifest.permission.CALL_PHONE
            val checkVal = checkSelfPermission(requiredPermission)
            if (checkVal == PackageManager.PERMISSION_GRANTED) {
                passCall(number)
            } else if (!firstCall) {
                Toast.makeText(
                    this,
                    getString(R.string.refuse_call),
                    Toast.LENGTH_LONG
                ).show()
            }
            if (firstCall) firstCall = false
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    fun sendSmsActivity (@Suppress("UNUSED_PARAMETER")view: View) {
        Toast.makeText(
            this,
            getString(R.string.sms,  number),
            Toast.LENGTH_LONG
        ).show()
        requestSmsPermission()
        val requiredPermission = Manifest.permission.RECEIVE_SMS
        val checkVal = checkSelfPermission(requiredPermission)
        if (checkVal == PackageManager.PERMISSION_GRANTED) {
            val realSms = Intent(this, SmsActivity::class.java).apply {
                putExtra(CONTACT_TO_SMS, number)
            }
            startActivity(realSms)
        }
    }

    private fun passCall(number: String) {
        val intent = Intent(Intent.ACTION_CALL, Uri.parse("tel:$number"))
        startActivity(intent)
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
                    )
                ) {
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
    private fun requestSmsPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.RECEIVE_SMS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(
                        this,
                        Manifest.permission.RECEIVE_SMS
                    )
                ) {
                    val builder: AlertDialog.Builder = AlertDialog.Builder(this)
                    builder.setTitle(getString(R.string.sms_perm))
                    builder.setPositiveButton(android.R.string.ok, null)
                    builder.setMessage(getString(R.string.could_call_sms))
                    builder.setOnDismissListener {
                        requestPermissions(
                            arrayOf(
                                Manifest.permission.RECEIVE_SMS
                            ), PERMISSIONS_REQUEST_RECEIVE_SMS
                        )
                    }
                    builder.show()
                } else {
                    ActivityCompat.requestPermissions(
                        this, arrayOf(Manifest.permission.RECEIVE_SMS),
                        PERMISSIONS_REQUEST_RECEIVE_SMS
                    )
                }
            }
        }
    }
    fun goBack(@Suppress("UNUSED_PARAMETER")view: View) {
        GoBack().goBack(this)
    }
}