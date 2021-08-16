package com.hos_dvk.easyphone.module_activity

import android.content.Intent
import android.database.MatrixCursor
import android.os.Build
import android.view.View
import android.widget.ListView
import android.widget.SimpleCursorAdapter
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.iterator
import com.hos_dvk.easyphone.*
import com.hos_dvk.easyphone.data_class.SmsDataClass
import com.hos_dvk.easyphone.query.SmsQuery
import com.hos_dvk.easyphone.widget.GoBack

class SmsListActivity : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onResume() {
        super.onResume()
        setContentView(R.layout.activity_list_sms)
        loadSms()
    }
    @RequiresApi(Build.VERSION_CODES.O)
    private fun loadSms() {
        val smsListView = findViewById<ListView>(R.id.sms_list)
        val smsList: MutableList<SmsDataClass> =
            SmsQuery().getAll(contentResolver, this, " DESC")
        val mc = MatrixCursor(
            arrayOf(
                "_id",
                "name",
                "messageData",
                "address"
            ), 16
        )
        for(sms in smsList) {
            val id = sms._id
            val name = sms.name
            val messageData = sms.messageData
            val address = sms.address
            mc.addRow(arrayOf(id, name, messageData, address))
        }
        val from = arrayOf(
            "name",
            "messageData",
            "address")

        val to = intArrayOf(R.id.name_contact_sms, R.id.message_preview, R.id.message_number)

        val simple = SimpleCursorAdapter(this, R.layout.style_of_sms_list, mc, from, to, 0)
        smsListView.adapter = simple
    }
    @RequiresApi(Build.VERSION_CODES.O)
    fun smsDefile(view: View) {
        val personName = view.findViewById<TextView>(R.id.name_contact_sms)
        val personMessage = view.findViewById<TextView>(R.id.message_preview)
        val allPerson = findViewById<ListView>(R.id.sms_list)

        if (personName.isSelected) {
            smsChoice(view)
            personName.isSelected = false
            personMessage.isSelected = false
        }
        for (person in allPerson) {
            val personNameHere = person.findViewById<TextView>(R.id.name_contact_sms)
            val personMessageHere = person.findViewById<TextView>(R.id.message_preview)
            personNameHere.isSelected = false
            personMessageHere.isSelected = false
        }
        personName.isSelected = true
        personMessage.isSelected = true
    }
    @RequiresApi(Build.VERSION_CODES.O)
    private fun smsChoice(view: View) {
        number = view.findViewById<TextView>(R.id.message_number).text.toString()

        val smsList: MutableList<SmsDataClass> =
            SmsQuery().getAll(contentResolver, this, " DESC")

        history = ""
        for (sms in smsList) {
            if (sms.address == number) history = sms.messageData!!
        }
        val realSms = Intent(this, SmsConversationActivity::class.java).apply {
            putExtra(CONTACT_TO_SMS, number)
            putExtra(HISTORY_OF_SMS, history)
        }
        startActivity(realSms)
    }
    fun goBack(@Suppress("UNUSED_PARAMETER")view: View) {
        GoBack().goBack(this)
    }
}