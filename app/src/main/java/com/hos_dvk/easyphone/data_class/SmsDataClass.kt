package com.hos_dvk.easyphone.data_class

import java.util.*

data class SmsDataClass(
    var address: String? = "",
    var messageData: String? = "",
    var date: Date? = null,
    var type: String? = "")