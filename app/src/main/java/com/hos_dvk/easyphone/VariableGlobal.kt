package com.hos_dvk.easyphone

import android.widget.TextView

const val NAME_TO_CALL = "com.hos_dvk.easyphone.NAME_TO_CALL"
const val NUMBER_TO_CALL = "com.hos_dvk.easyphone.NUMBER_TO_CALL"
const val CONTACT_TO_SMS = "com.hos_dvk.easyphone.CONTACT_TO_SMS"
const val HISTORY_OF_SMS = "com.hos_dvk.easyphone.HISTORY_OF_SMS"
const val PERMISSIONS_REQUEST_READ_CONTACTS = 1
const val PERMISSIONS_REQUEST_WRITE_CONTACTS = 2
const val PERMISSIONS_REQUEST_RECEIVE_SMS = 37
const val PERMISSIONS_REQUEST_SEND_SMS = 38
const val PERMISSIONS_REQUEST_READ_SMS = 39
const val PERMISSIONS_REQUEST_CALL_PHONE = 42
const val PERMISSIONS_REQUEST_IMAGE_CAPTURE = 5
const val PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 410
var lastActivity: String = "MainActivity"
var firstContacts: Boolean = true
var firstCall: Boolean = true
var name: String = ""
var number: String = ""
var history: String = ""
var numberToCall: TextView? = null
