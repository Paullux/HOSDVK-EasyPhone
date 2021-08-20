package com.hos_dvk.easyphone.module_activity

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.hos_dvk.easyphone.*
import com.hos_dvk.easyphone.data_class.ContactDataClass
import com.hos_dvk.easyphone.query.ContactQuery

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
        val requiredPermission = Manifest.permission.RECEIVE_SMS
        val checkVal = checkSelfPermission(requiredPermission)
        if (checkVal == PackageManager.PERMISSION_GRANTED) {
            val realSms = Intent(this, SmsConversationActivity::class.java).apply {
                putExtra(CONTACT_TO_SMS, number)
            }
            startActivity(realSms)
        }
    }

    private fun passCall(number: String) {
        val intent = Intent(Intent.ACTION_CALL, Uri.parse("tel:$number"))
        startActivity(intent)
    }
    fun goBack(@Suppress("UNUSED_PARAMETER")view: View) {
        this.finish()
    }
}