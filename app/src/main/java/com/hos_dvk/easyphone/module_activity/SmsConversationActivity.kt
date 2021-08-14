package com.hos_dvk.easyphone.module_activity

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.provider.Telephony
import android.telephony.SmsManager
import android.text.method.ScrollingMovementMethod
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.hos_dvk.easyphone.*
import com.hos_dvk.easyphone.data_class.ContactDataClass
import com.hos_dvk.easyphone.query.ContactQuery
import com.hos_dvk.easyphone.widget.GoBack


class SmsConversationActivity : AppCompatActivity() {
    override fun onResume() {
        super.onResume()
        setContentView(R.layout.activity_conversation_sms)
        val realSms = intent
        val numberSMS = realSms.getStringExtra(CONTACT_TO_SMS).toString()
        val historySMS = realSms.getStringExtra(HISTORY_OF_SMS).toString()
        val phoneToSms: TextView = findViewById(R.id.phone_number_to_send_sms)
        val messageToSms: TextView = findViewById(R.id.history)
        messageToSms.text = historySMS
        phoneToSms.text = numberSMS
        messageToSms.movementMethod = ScrollingMovementMethod()
        phoneToSms.movementMethod = ScrollingMovementMethod()
        messageToSms.post {
            val scrollAmount =
                messageToSms.layout.getLineTop(messageToSms.lineCount) - messageToSms.height
            messageToSms.scrollTo(0, scrollAmount)
        }
        if (number != "") findContactName()
        receiveSMS()
    }
    private fun receiveSMS() {
        val br = object: BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                for (sms in Telephony.Sms.Intents.getMessagesFromIntent(intent)) {
                    findContactName()
                    val contactName = findViewById<TextView>(R.id.recipient)
                    val messageToSms = findViewById<TextView>(R.id.history)
                    messageToSms.text = getString(R.string.historic_message_other,messageToSms.text.toString(),contactName.text.toString(), sms.displayMessageBody.toString())
                    val scrollAmount = messageToSms.layout.getLineTop(messageToSms.lineCount) - messageToSms.height
                    messageToSms.scrollTo(0, scrollAmount)
                }
            }
        }
        registerReceiver(br, IntentFilter("android.provider.Telephony.SMS_RECEIVED"))
    }

    fun addContact(@Suppress("UNUSED_PARAMETER")view: View) {
        number = findViewById<TextView>(R.id.phone_number_to_send_sms).text.toString()
        val contactName = findViewById<TextView>(R.id.recipient)
        if (number != "" && contactName.text.toString() == getString(R.string.nom_contact_sms)) {
            val addContact = Intent(this, AddContactActivity::class.java).apply {
                putExtra(NUMBER_TO_CALL, number)
            }
            startActivity(addContact)
        } else {
            val intent = Intent(this, ContactActivity::class.java)
            startActivity(intent)
        }
    }

    fun sendSms(@Suppress("UNUSED_PARAMETER")view: View) {
        number = findViewById<TextView>(R.id.phone_number_to_send_sms).text.toString()
        val messageSms = findViewById<EditText>(R.id.message_to_send)
        messageSms.movementMethod = ScrollingMovementMethod()
        val sms = SmsManager.getDefault()
        sms.sendTextMessage(number, "ME", messageSms.text.toString(), null, null)
        val historyMessage = findViewById<TextView>(R.id.history)
        historyMessage.movementMethod = ScrollingMovementMethod()
        historyMessage.text = getString(R.string.historic_message_me,historyMessage.text.toString(), messageSms.text.toString())
        val scrollAmount = historyMessage.layout.getLineTop(historyMessage.lineCount) - historyMessage.height
        historyMessage.scrollTo(0, scrollAmount)
        messageSms.text = null
    }

    private fun findContactName () {
        val contactName = findViewById<TextView>(R.id.recipient)
        number = findViewById<TextView>(R.id.phone_number_to_send_sms).text.toString()
        val contactsList: MutableList<ContactDataClass> =
            ContactQuery().getAll(contentResolver, this)
        for (contact in contactsList) {
            for (numberContact in contact.number.split(", ").toTypedArray()) {
                if (numberContact.replaceFirst("0", "+33") == number.replaceFirst("0", "+33"))
                    contactName.text = contact.name
            }
        }
    }
    fun goBack(@Suppress("UNUSED_PARAMETER")view: View) {
        GoBack().goBack(this)
    }
}