package com.hos_dvk.easyphone.query

import android.content.Context
import io.michaelrocks.libphonenumber.android.PhoneNumberUtil

class ToInternationalNumberPhone {
    fun transform(number: String, context: Context?): String {
        val locale: String = context!!.resources.configuration.locale.country
        val phoneUtil: PhoneNumberUtil = PhoneNumberUtil.createInstance(context)
        return phoneUtil.formatOutOfCountryCallingNumber(
            phoneUtil.parse(number, locale),
            PhoneNumberUtil.PhoneNumberFormat.INTERNATIONAL.toString()
        ).replace(" ", "")
    }
}