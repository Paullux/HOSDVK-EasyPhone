package com.hos_dvk.easyphone.widget

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.provider.Telephony
import com.hos_dvk.easyphone.contactName
import com.hos_dvk.easyphone.messageToSms
import com.hos_dvk.easyphone.module_activity.SmsConversationActivity
import com.hos_dvk.easyphone.nameOfSms
import com.hos_dvk.easyphone.query.ToInternationalNumberPhone

class MmsReceiver: BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        if (Telephony.Sms.Intents.getMessagesFromIntent(intent) != null) {
            for (sms in Telephony.Sms.Intents.getMessagesFromIntent(intent)) {
                SmsConversationActivity().findContactName()
                val nameOfSmsHere = nameOfSms?.text.toString()
                val address =
                    ToInternationalNumberPhone().transform(sms.originatingAddress!!, context)
                if (nameOfSmsHere == address) {
                    messageToSms?.text =
                        "${messageToSms?.text.toString()}\n---${contactName?.text.toString()}---\n${sms.displayMessageBody.toString()}"
                    val scrollAmount =
                        messageToSms?.layout?.getLineTop(messageToSms!!.lineCount)
                            ?.minus(messageToSms!!.height)
                    messageToSms?.scrollTo(0, scrollAmount!!)
                }
            }
        }
    }
}