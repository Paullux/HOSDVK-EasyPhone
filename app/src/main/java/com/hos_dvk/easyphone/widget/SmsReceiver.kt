package com.hos_dvk.easyphone.widget


/**open class SmsReceiver: BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        var name = "Inconnu"
        val contactsList: MutableList<ContactDataClass> =
            ContactQuery().getAll(context!!.contentResolver, context!!)
        if (Telephony.Sms.Intents.getMessagesFromIntent(intent) != null) {
            for (sms in Telephony.Sms.Intents.getMessagesFromIntent(intent)) {
                SmsDatabase().putSmsToDatabase(context!!.contentResolver, sms)
                val nameOfSmsHere = nameOfSms?.text.toString()
                val address =
                    ToInternationalNumberPhone().transform(sms.originatingAddress!!, context)
                for (contact in contactsList) {
                    for (numberContact in contact.number.split(", ").toTypedArray()) {
                        val numberContactHere =
                            ToInternationalNumberPhone().transform(numberContact, context)
                        if (numberContactHere == address)
                            name = contact.name
                    }
                }
                if (nameOfSmsHere == address) {
                    messageToSms?.text =
                        "${messageToSms?.text.toString()}\n---${contactName?.text.toString()}---\n${sms.displayMessageBody.toString()}"
                    val scrollAmount =
                        messageToSms?.layout?.getLineTop(messageToSms!!.lineCount)
                            ?.minus(messageToSms!!.height)
                    messageToSms?.scrollTo(0, scrollAmount!!)
                }
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    val realSms = Intent(context, SmsConversationActivity::class.java).apply {
                        putExtra(CONTACT_TO_SMS, address)
                        putExtra(HISTORY_OF_SMS, "")
                    }
                    val realSmsPendingIntent: PendingIntent? = TaskStackBuilder.create(context).run {
                        addNextIntentWithParentStack(realSms)
                        getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT)
                    }
                    var builder = NotificationCompat.Builder(context!!, "SMS").apply {
                        setContentTitle("Nouveau SMS reçu")
                        setContentText(sms.originatingAddress.toString())
                        setStyle(
                            NotificationCompat.BigTextStyle()
                                .bigText("---$name--- ${sms.displayMessageBody.toString()}")
                        )
                        setSmallIcon(R.drawable.ic_notifs)
                        setCategory(NotificationCompat.CATEGORY_MESSAGE)
                        setAutoCancel(true)
                        setSound(Settings.System.DEFAULT_NOTIFICATION_URI)
                        setContentIntent(realSmsPendingIntent)
                        priority = NotificationCompat.PRIORITY_HIGH
                    }
                    with(NotificationManagerCompat.from(context!!)) {
                        // notificationId is a unique int for each notification that you must define
                        val notificationId: Int = (1..500).random()
                        notify(notificationId, builder.build())
                    }
                }
            }
        }
    }
}**/