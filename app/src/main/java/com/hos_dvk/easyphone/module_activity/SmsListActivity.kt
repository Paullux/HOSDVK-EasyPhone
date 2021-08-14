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
import com.hos_dvk.easyphone.data_class.ContactDataClass
import com.hos_dvk.easyphone.data_class.SmsDataClass
import com.hos_dvk.easyphone.query.ContactQuery
import com.hos_dvk.easyphone.query.SmsQuery

class SmsListActivity : AppCompatActivity() {
    override fun onResume() {
        super.onResume()
        setContentView(R.layout.activity_list_sms)
        loadSms()
    }
    private fun loadSms() {
        val smsListView = findViewById<ListView>(R.id.sms_list)
        val smsList: MutableList<SmsDataClass> =
            SmsQuery().getAll(contentResolver, this)
        val mc = MatrixCursor(
            arrayOf(
                "_id",
                "name",
                "messageData",
            ), 16
        )
        for(sms in smsList) {
            val id = sms._id
            val name = sms.name
            val messageData = sms.messageData
            mc.addRow(arrayOf(id, name, messageData))
        }
        val from = arrayOf(
            "name",
            "messageData")

        val to = intArrayOf(R.id.name_contact_sms, R.id.message_preview)

        val simple = SimpleCursorAdapter(this, R.layout.style_of_sms_list, mc, from, to, 0)
        smsListView.adapter = simple
    }
    @RequiresApi(Build.VERSION_CODES.M)
    fun defile(view: View) {
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
    private fun smsChoice(view: View) {
        number = view.findViewById<TextView>(R.id.name_contact_sms).text.toString()

        val contactsList: MutableList<ContactDataClass> =
            ContactQuery().getAll(contentResolver, this)

        for (contact in contactsList) {
            if (contact.name == number) number = contact.number
        }

        val smsList: MutableList<SmsDataClass> =
            SmsQuery().getAll(contentResolver, this)

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
}