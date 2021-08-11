package com.hos_dvk.easyphone.module_activity

import android.content.Intent
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.ddd.androidutils.DoubleClick
import com.ddd.androidutils.DoubleClickListener
import com.hos_dvk.easyphone.*
import com.hos_dvk.easyphone.widget.GoBack


class DialerActivity : AppCompatActivity() {
    override fun onStart() {
        super.onStart()
        setContentView(R.layout.activity_dialer)
        lastActivity = "MainActivity"
        numberToCall = findViewById<TextView>(R.id.write_number)
    }
    private var firstDeleteButtonPress = true

    fun chooseCall(@Suppress("UNUSED_PARAMETER")view: View) {
        lastActivity = "DialerActivity"
        val realCall = Intent(this, RealCallActivity::class.java).apply {
            putExtra(NAME_TO_CALL, numberToCall?.text.toString())
            putExtra(NUMBER_TO_CALL, numberToCall?.text.toString())
        }
        startActivity(realCall)
    }

    fun addContact(@Suppress("UNUSED_PARAMETER")view: View) {
        val addContact = Intent(this, AddContactActivity::class.java).apply {
            putExtra(NUMBER_TO_CALL, numberToCall?.text.toString())
        }
        startActivity(addContact)
    }

    fun textToDelete(@Suppress("UNUSED_PARAMETER")view: View) {
        val delete = findViewById<Button>(R.id.call_button_delete)
        if (firstDeleteButtonPress) {
            if (numberToCall?.text.toString().trim() != "") {
                numberToCall?.text.toString().substring(0, numberToCall?.text.toString().length - 1)
            }
            firstDeleteButtonPress = false
        }
        val doubleClick = DoubleClick(object : DoubleClickListener {
            override fun onSingleClickEvent(view: View?) {
                if (numberToCall?.text.toString()
                        .trim() != ""
                )
                    numberToCall?.text =
                        numberToCall?.text
                            ?.substring(0, numberToCall?.text.toString().length - 1)
            }

            override fun onDoubleClickEvent(view: View?) {
                if (numberToCall?.text.toString()
                        .trim() != ""
                )
                    numberToCall?.text = ""
            }
        })
        delete.setOnClickListener(doubleClick)
    }
    private fun pressButton(button: String) {
        if (numberToCall?.text.toString().trim() == "") {
            numberToCall?.text = button
        } else { numberToCall?.text = numberToCall?.text.toString() + button }
    }
    fun button0(@Suppress("UNUSED_PARAMETER")view: View) {
        pressButton("0")
    }

    fun button1(@Suppress("UNUSED_PARAMETER")view: View) {
        pressButton("1")
    }

    fun button2(@Suppress("UNUSED_PARAMETER")view: View) {
        pressButton("2")
    }

    fun button3(@Suppress("UNUSED_PARAMETER")view: View) {
        pressButton("3")
    }

    fun button4(@Suppress("UNUSED_PARAMETER")view: View) {
        pressButton("4")
    }

    fun button5(@Suppress("UNUSED_PARAMETER")view: View) {
        pressButton("5")
    }

    fun button6(@Suppress("UNUSED_PARAMETER")view: View) {
        pressButton("6")
    }

    fun button7(@Suppress("UNUSED_PARAMETER")view: View) {
        pressButton("7")
    }

    fun button8(@Suppress("UNUSED_PARAMETER")view: View) {
        pressButton("8")
    }

    fun button9(@Suppress("UNUSED_PARAMETER")view: View) {
        pressButton("9")
    }

    fun buttonSign(@Suppress("UNUSED_PARAMETER")view: View) {
        pressButton("+")
    }

    fun goBack(@Suppress("UNUSED_PARAMETER")view: View) {
        GoBack().goBack(this)
    }
}