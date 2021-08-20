package com.hos_dvk.easyphone.query

import android.content.ContentResolver
import android.content.Context
import android.database.Cursor
import android.os.Build
import android.provider.Telephony
import androidx.annotation.RequiresApi
import com.hos_dvk.easyphone.data_class.ContactDataClass
import com.hos_dvk.easyphone.data_class.SmsDataClass
import java.text.SimpleDateFormat
import java.util.*


class SmsQuery {
    @RequiresApi(Build.VERSION_CODES.O)
    fun getAll(
        contentResolver: ContentResolver,
        context: Context, order: String,
    ):
            MutableList<SmsDataClass> {

        val smsList: MutableList<SmsDataClass> = mutableListOf()

        val cursor: Cursor? =
            contentResolver.query(
                Telephony.Sms.CONTENT_URI,
                arrayOf(
                    Telephony.Sms._ID,
                    Telephony.Sms.ADDRESS,
                    Telephony.Sms.BODY,
                    Telephony.Sms.DATE,
                    Telephony.Sms.TYPE),
                null,
                null,
                Telephony.Sms.DATE + order)!!

        var smsData: SmsDataClass

        val conversationsSms: MutableMap<String, MutableList<SmsDataClass>> = mutableMapOf()
        val positionCursor: Int = cursor?.count?.minus(500)!!

        val contactsList: MutableList<ContactDataClass> =
            ContactQuery().getAll(contentResolver, context)
        if (cursor.count != 0) {
            when (order) {
                " ASC" -> {
                    if (positionCursor > 0) {
                        cursor.move(positionCursor)
                    } else {
                        cursor.moveToFirst()
                    }

                    do {
                        val id: Int? =
                            cursor.getInt(cursor.getColumnIndex(Telephony.Sms._ID))

                        var address: String? =
                            cursor.getString(cursor.getColumnIndex(Telephony.Sms.ADDRESS))

                        if (address!!.matches("-?\\d+(\\.\\d+)?".toRegex()))
                            address = ToInternationalNumberPhone().transform(address, context)

                        val messageData: String? =
                            cursor.getString(cursor.getColumnIndex(Telephony.Sms.BODY))

                        val date: Date? =
                            Date(cursor.getLong(cursor.getColumnIndex(Telephony.Sms.DATE)))

                        val type: Int? = cursor.getInt(cursor.getColumnIndex(Telephony.Sms.TYPE))

                        var name = address
                        for (contact in contactsList) {
                            for (numberContact in contact.number.split(", ").toTypedArray()) {
                                if (numberContact == address)
                                    name = contact.name
                            }
                        }
                        if (!conversationsSms.containsKey(name)) {
                            conversationsSms[name!!] = mutableListOf()
                        }
                        smsData = SmsDataClass(id!!, address, messageData, date, type, name)
                        conversationsSms[name]?.add(smsData)
                    } while (cursor.moveToNext())
                }
                " DESC" -> {
                    cursor.moveToFirst()
                    do {
                        val id: Int? =
                            cursor.getInt(cursor.getColumnIndex(Telephony.Sms._ID))

                        var address: String? =
                            cursor.getString(cursor.getColumnIndex(Telephony.Sms.ADDRESS))

                        if (address!!.matches("-?\\d+(\\.\\d+)?".toRegex()))
                            address = ToInternationalNumberPhone().transform(address, context)

                        val messageData: String? =
                            cursor.getString(cursor.getColumnIndex(Telephony.Sms.BODY))

                        val date: Date? =
                            Date(cursor.getLong(cursor.getColumnIndex(Telephony.Sms.DATE)))

                        val type: Int? = cursor.getInt(cursor.getColumnIndex(Telephony.Sms.TYPE))

                        var name = address
                        for (contact in contactsList) {
                            for (numberContact in contact.number.split(", ").toTypedArray()) {
                                if (numberContact == address)
                                    name = contact.name
                            }
                        }
                        if (!conversationsSms.containsKey(name)) {
                            conversationsSms[name!!] = mutableListOf()
                        }
                        smsData = SmsDataClass(id!!, address, messageData, date, type, name)
                        conversationsSms[name]?.add(smsData)
                        cursor.moveToNext()
                    } while (cursor.position <= 500 && cursor.position < cursor.count)
                }
                else -> {
                    println("error")
                }
            }
        }
        cursor.close()
        for (conversation in conversationsSms) {
            val messages = conversation.value
            val firstMessage = messages[0]
            var conversationMessageData = ""
            var previousType: Int? = null
            for (message in messages) {
                val formatDate = SimpleDateFormat("dd/MM/yyyy hh:mm:ss")
                val date = formatDate.format(message.date)
                when (previousType) {
                    Telephony.Sms.MESSAGE_TYPE_INBOX -> {
                        conversationMessageData += when (message.type) {
                            Telephony.Sms.MESSAGE_TYPE_INBOX -> "----${conversation.key}---- \n$date \n${message.messageData}\n"
                            Telephony.Sms.MESSAGE_TYPE_SENT -> "----Moi---- \n$date \n${message.messageData}\n"
                            else -> "----Unknown message Type---- \n $date \n ${message.messageData}\n"
                        }
                        previousType = Telephony.Sms.MESSAGE_TYPE_INBOX
                    }
                    Telephony.Sms.MESSAGE_TYPE_SENT -> {
                        conversationMessageData += when (message.type) {
                            Telephony.Sms.MESSAGE_TYPE_INBOX -> "----${conversation.key}---- \n$date \n${message.messageData}\n"
                            Telephony.Sms.MESSAGE_TYPE_SENT -> "----Moi---- \n$date \n${message.messageData}\n"
                            else -> "----Unknown message Type---- \n $date \n ${message.messageData}\n"
                        }
                        previousType = Telephony.Sms.MESSAGE_TYPE_SENT
                    }
                    else -> {
                        if (previousType == null) {
                            when (message.type) {
                                Telephony.Sms.MESSAGE_TYPE_INBOX -> {
                                    conversationMessageData =
                                        "----${conversation.key}---- \n$date \n${message.messageData}\n"
                                    previousType = Telephony.Sms.MESSAGE_TYPE_INBOX
                                }
                                Telephony.Sms.MESSAGE_TYPE_SENT -> {
                                    conversationMessageData =
                                        "----Moi---- \n$date \n${message.messageData}\n"
                                    previousType = Telephony.Sms.MESSAGE_TYPE_SENT
                                }
                                else -> {
                                    conversationMessageData =
                                        "----Unknown message Type---- \n $date \n ${message.messageData}\n"
                                }
                            }
                        } else {
                            conversationMessageData =
                                "----Unknown message Type---- \n $date \n ${message.messageData}\n"
                        }
                    }
                }

            }
            smsData = SmsDataClass(firstMessage._id,
                firstMessage.address,
                conversationMessageData,
                firstMessage.date,
                firstMessage.type,
                firstMessage.name)
            smsList.add(smsData)
        }
        return smsList
    }
}