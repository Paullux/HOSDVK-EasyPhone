package com.hos_dvk.easyphone.query

import android.content.ContentResolver
import android.database.Cursor
import com.hos_dvk.easyphone.data_class.SmsDataClass
import java.util.*
import android.provider.Telephony


class SmsQuery {
    fun getAll(contentResolver: ContentResolver): MutableList<SmsDataClass> {
        val smsList: MutableList<SmsDataClass> = mutableListOf()
        val cursor: Cursor =
            contentResolver.query(Telephony.Sms.CONTENT_URI, null, null, null, null)!!
        var smsData: SmsDataClass
        if (cursor.moveToFirst()) {
            do {
                val address: String =
                    cursor.getString(cursor.getColumnIndexOrThrow(Telephony.Sms.ADDRESS)).toString()
                val messageData: String =
                    cursor.getString(cursor.getColumnIndexOrThrow(Telephony.Sms.BODY)).toString()
                val date =
                    Date(cursor.getString(cursor.getColumnIndexOrThrow(Telephony.Sms.DATE)).toLong())

                val type = when (Integer.parseInt(cursor.getString(cursor.getColumnIndexOrThrow(Telephony.Sms.TYPE)))) {
                    Telephony.Sms.MESSAGE_TYPE_INBOX -> {
                        "inbox"
                    }
                    Telephony.Sms.MESSAGE_TYPE_SENT -> {
                        "sent"
                    }
                    Telephony.Sms.MESSAGE_TYPE_OUTBOX -> {
                        "outbox"
                    }
                    else -> {
                        "null"
                    }
                }
                smsData = SmsDataClass(address, messageData, date, type)
                smsList.add(smsData)
            } while (cursor.moveToNext())
        }
        cursor.close()
        return smsList
    }
}