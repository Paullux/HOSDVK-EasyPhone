package com.hos_dvk.easyphone.widget

import android.content.ContentResolver
import android.content.ContentValues
import android.net.Uri
import android.provider.Telephony
import android.telephony.SmsMessage

class SmsDatabase {
    fun putSmsToDatabase(contentResolver: ContentResolver, sms: SmsMessage) {
        // Create SMS row
        val values = ContentValues()
        values.put(Telephony.TextBasedSmsColumns.ADDRESS, sms.originatingAddress)
        values.put(Telephony.TextBasedSmsColumns.DATE, System.currentTimeMillis())
        values.put(Telephony.TextBasedSmsColumns.READ, "read")
        values.put(Telephony.TextBasedSmsColumns.STATUS, sms.status)
        values.put(Telephony.TextBasedSmsColumns.BODY, sms.messageBody)
        values.put(
            Telephony.TextBasedSmsColumns.TYPE,
            Telephony.TextBasedSmsColumns.MESSAGE_TYPE_INBOX
        )
        values.put(Telephony.TextBasedSmsColumns.SEEN, "seen")

        // Push row into the SMS table
        contentResolver.insert(Uri.parse("content://sms"), values)
    }
}