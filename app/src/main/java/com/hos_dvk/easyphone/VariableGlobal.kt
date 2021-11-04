package com.hos_dvk.easyphone

import android.app.NotificationChannel
import android.widget.TextView

const val NAME_TO_CALL = "com.hos_dvk.easyphone.NAME_TO_CALL"
const val NUMBER_TO_CALL = "com.hos_dvk.easyphone.NUMBER_TO_CALL"
const val CONTACT_TO_SMS = "com.hos_dvk.easyphone.CONTACT_TO_SMS"
const val HISTORY_OF_SMS = "com.hos_dvk.easyphone.HISTORY_OF_SMS"
var channel: NotificationChannel? = null
var contactName: TextView? = null
var nameOfSms: TextView? = null
var messageToSms: TextView? = null
var lastActivity: String = "MainActivity"
var firstContacts: Boolean = true
var firstCall: Boolean = true
var name: String = ""
var number: String = ""
var history: String = ""
var numberToCall: TextView? = null
