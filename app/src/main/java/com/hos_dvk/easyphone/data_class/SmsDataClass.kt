package com.hos_dvk.easyphone.data_class

import java.util.*

data class SmsDataClass(
    var _id: Int = 0,
    var address: String? = "",
    var messageData: String? = "",
    var date: Date? = null,
    var type: Int? = 0,
    var name: String? = "")