package com.hos_dvk.easyphone.query

import android.content.ContentResolver
import android.content.Context
import android.database.Cursor
import android.provider.Telephony
import com.hos_dvk.easyphone.data_class.ContactDataClass
import com.hos_dvk.easyphone.data_class.SmsDataClass
import java.text.SimpleDateFormat
import java.util.*


class SmsQuery {
    fun getAll(contentResolver: ContentResolver, context: Context): MutableList<SmsDataClass> {
        val smsList: MutableList<SmsDataClass> = mutableListOf()
        val cursor: Cursor =
            contentResolver.query(Telephony.Sms.CONTENT_URI, null, null, null, Telephony.Sms.DATE + " ASC")!!
        var smsData: SmsDataClass
        val conversationsSms: MutableMap<String, MutableList<SmsDataClass>> = mutableMapOf()
        if (cursor.moveToFirst()) {
            do {
                val address: String =
                    cursor.getString(cursor.getColumnIndexOrThrow(Telephony.Sms.ADDRESS))

                val messageData: String =
                    cursor.getString(cursor.getColumnIndexOrThrow(Telephony.Sms.BODY))

                val date = Date(cursor.getLong(cursor.getColumnIndexOrThrow(Telephony.Sms.DATE)))

                val type = cursor.getInt(cursor.getColumnIndexOrThrow(Telephony.Sms.TYPE))

                val contactsList: MutableList<ContactDataClass> =
                    ContactQuery().getAll(contentResolver, context)

                var name = address

                for (contact in contactsList) {
                    //TODO : Détecter le pays de la personne pour détecter le code pays
                    if (contact.number.replaceFirst("0", "+33") == address.replaceFirst("0", "+33")) name = contact.name
                }
                if (!conversationsSms.containsKey(name)) {
                        conversationsSms[name] = mutableListOf()
                    }
                    val smsId = (0..255).random()
                    smsData = SmsDataClass(smsId, address, messageData, date, type, name)
                    conversationsSms[name]?.add(smsData)

            } while (cursor.moveToNext())
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
                                Telephony.Sms.MESSAGE_TYPE_INBOX -> "--${conversation.key}-- \n$date \n${message.messageData}\n"
                                Telephony.Sms.MESSAGE_TYPE_SENT -> "--Moi-- \n$date \n${message.messageData}\n"
                                else -> "--Unknown message Type-- \n $date \n ${message.messageData}\n"
                            }
                            previousType = Telephony.Sms.MESSAGE_TYPE_INBOX
                        }
                        Telephony.Sms.MESSAGE_TYPE_SENT -> {
                            conversationMessageData += when (message.type) {
                                Telephony.Sms.MESSAGE_TYPE_INBOX -> "--${conversation.key}-- \n$date \n${message.messageData}\n"
                                Telephony.Sms.MESSAGE_TYPE_SENT -> "--Moi-- \n$date \n${message.messageData}\n"
                                else -> "--Unknown message Type-- \n $date \n ${message.messageData}\n"
                            }
                            previousType = Telephony.Sms.MESSAGE_TYPE_SENT
                        }
                        else -> {
                            when (message.type) {
                                Telephony.Sms.MESSAGE_TYPE_INBOX -> {
                                    conversationMessageData =
                                        "--${conversation.key}-- \n$date \n${message.messageData}\n"
                                    previousType = Telephony.Sms.MESSAGE_TYPE_INBOX
                                }
                                Telephony.Sms.MESSAGE_TYPE_SENT -> {
                                    conversationMessageData =
                                        "--Moi-- \n$date \n${message.messageData}\n"
                                    previousType = Telephony.Sms.MESSAGE_TYPE_SENT
                                }
                                else -> conversationMessageData =
                                    "--Unknown message Type-- \n $date \n ${message.messageData}\n"
                            }
                        }
                    }

                }
                smsData = SmsDataClass(firstMessage._id, firstMessage.address, conversationMessageData, firstMessage.date, firstMessage.type, firstMessage.name)
                smsList.add(smsData)
            }
        }
        cursor.close()
        return smsList
    }
}