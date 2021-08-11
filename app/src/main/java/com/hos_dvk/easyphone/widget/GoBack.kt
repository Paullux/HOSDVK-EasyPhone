package com.hos_dvk.easyphone.widget

import android.content.Context
import android.content.Intent
import com.hos_dvk.easyphone.MainActivity
import com.hos_dvk.easyphone.lastActivity
import com.hos_dvk.easyphone.module_activity.ContactActivity
import com.hos_dvk.easyphone.module_activity.DialerActivity


class GoBack {
    fun goBack(packageContext: Context) {
        if (lastActivity == "MainActivity") {
            val intent = Intent(packageContext, MainActivity::class.java)
            packageContext.startActivity(intent)
        }
        if (lastActivity == "DialerActivity") {
            val intent = Intent(packageContext, DialerActivity::class.java)
            packageContext.startActivity(intent)
        }
        if (lastActivity == "ContactActivity") {
            val intent = Intent(packageContext, ContactActivity::class.java)
            packageContext.startActivity(intent)
        }
    }
}