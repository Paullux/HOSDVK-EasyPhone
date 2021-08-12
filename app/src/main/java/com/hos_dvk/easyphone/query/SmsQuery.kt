package com.hos_dvk.easyphone.query

import android.content.ContentResolver
import android.database.Cursor
import android.net.Uri
import com.hos_dvk.easyphone.data_class.SmsDataClass
import java.util.*

class SmsQuery {
    fun getAll(contentResolver: ContentResolver): MutableList<SmsDataClass> {
        var smsList: MutableList<SmsDataClass> = mutableListOf()
        val cursor: Cursor =
            contentResolver.query(Uri.parse("content://sms/inbox"), null, null, null, null)!!
        println("****************************************")
        if (cursor.moveToFirst()) {
            val name = cursor.getColumnIndex("address")
            val messageData = cursor.getColumnIndex("body")
            val dateID = cursor.getColumnIndex("date")
            println(cursor.getColumnIndex("address").toString())
            println(cursor.getColumnIndex("body").toString())
            println(cursor.getColumnIndex("date").toString())
            do {
                println("----------------------------------------")
                val dateString = cursor.getString(dateID)
                val smsData: SmsDataClass? = null
                smsData?.name = cursor.getString(name)
                smsData?.messageData = cursor.getString(messageData)
                smsData?.date = Date(dateString.toLong())
                if (smsData != null) {
                    smsList.add(smsData)
                }
                println(smsData?.name)
                println(smsData?.messageData)
                println(smsData?.date)
                println("----------------------------------------")
            } while (cursor.moveToNext())
        println("****************************************")
        }  else {
            smsList = mutableListOf()// empty box, no SMS
        }
        return smsList
    }
}