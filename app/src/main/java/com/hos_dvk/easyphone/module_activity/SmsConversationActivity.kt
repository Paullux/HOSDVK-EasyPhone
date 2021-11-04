package com.hos_dvk.easyphone.module_activity


/**class SmsConversationActivity : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onResume() {
        super.onResume()
        setContentView(R.layout.activity_conversation_sms)
        val realSms = intent
        val numberSMS = realSms.getStringExtra(CONTACT_TO_SMS).toString()
        var historySMS: String? = ""
        val phoneToSms = findViewById<TextView>(R.id.phone_number_to_send_sms)
        val messageToSms = findViewById<TextView>(R.id.history)
        val smsList: MutableList<SmsDataClass> =
            SmsQuery().getAll(contentResolver, this, " ASC")
        for (sms in smsList) {
            if (sms.address == numberSMS) historySMS = sms.messageData
        }
        messageToSms.hint = getString(R.string.history_hint)
        phoneToSms.text = numberSMS
        if (!historySMS.isNullOrBlank()) {
            messageToSms.text = historySMS.toString()

            messageToSms.movementMethod = ScrollingMovementMethod()
            phoneToSms.movementMethod = ScrollingMovementMethod()
            messageToSms.post {
                val scrollAmount =
                    messageToSms.layout.getLineTop(messageToSms.lineCount) - messageToSms.height
                messageToSms.scrollTo(0, scrollAmount)
            }
        }
        if (number != "") findContactName()
        receiveSMS()
    }
    private fun receiveSMS() {
        contactName = findViewById<TextView>(R.id.recipient)
        nameOfSms = findViewById<TextView>(R.id.phone_number_to_send_sms)
        messageToSms = findViewById<TextView>(R.id.history)
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
        val smsManager = SmsManager.getDefault()
        val preIntent = Intent("SMS_SENT")
        val sentIntent =
            PendingIntent.getBroadcast(
                applicationContext,
                0,
                preIntent,
                PendingIntent.FLAG_ONE_SHOT
            )
        val preIntent2 = Intent("SMS_DELIVERY")
        val deliveryIntent =
            PendingIntent.getBroadcast(
                applicationContext,
                0,
                preIntent2,
                PendingIntent.FLAG_ONE_SHOT
            )
        smsManager.sendTextMessage(number,
            "ME",
            messageSms.text.toString(),
            sentIntent,
            deliveryIntent)

        val values = ContentValues()
        values.put(Telephony.TextBasedSmsColumns.ADDRESS, number)
        values.put(Telephony.TextBasedSmsColumns.DATE, System.currentTimeMillis())
        values.put(Telephony.TextBasedSmsColumns.BODY, messageSms.text.toString())
        values.put(
            Telephony.TextBasedSmsColumns.TYPE,
            Telephony.TextBasedSmsColumns.MESSAGE_TYPE_SENT
        )

        // Push row into the SMS table
        this.contentResolver.insert(Uri.parse("content://sms"), values)

        val historyMessage = findViewById<TextView>(R.id.history)
        historyMessage.movementMethod = ScrollingMovementMethod()
        historyMessage.text = getString(R.string.historic_message_me,historyMessage.text.toString(), messageSms.text.toString())
        val scrollAmount = historyMessage.layout.getLineTop(historyMessage.lineCount) - historyMessage.height
        historyMessage.scrollTo(0, scrollAmount)
        messageSms.text = null
    }

    fun findContactName () {
        val contactName = findViewById<TextView>(R.id.recipient)
        number = findViewById<TextView>(R.id.phone_number_to_send_sms).text.toString()
        val contactsList: MutableList<ContactDataClass> =
            ContactQuery().getAll(contentResolver, this)
        number = ToInternationalNumberPhone().transform(number, this)
        for (contact in contactsList) {
            for (numberContact in contact.number.split(", ").toTypedArray()) {
                val numberContactHere = ToInternationalNumberPhone().transform(numberContact, this)
            if (numberContactHere == number)
                    contactName.text = contact.name
            }
        }
    }
    fun goBack(@Suppress("UNUSED_PARAMETER")view: View) {
        this.finish()
    }
}**/