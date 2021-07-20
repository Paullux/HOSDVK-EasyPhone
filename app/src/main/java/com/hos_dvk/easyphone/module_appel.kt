/**package com.hos_dvk.easyphone

import android.app.Activity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import android.view.LayoutInflater
import android.widget.Button
import androidx.constraintlayout.widget.ConstraintSet
import android.content.Intent;
import android.util.Log
import android.widget.TextView
import android.widget.Toast

class module_appel : Activity() {
    public fun Retour(view: View) {
        setContentView(R.layout.activity_main)
    }
    public fun button_0(view: View) {
        //val editText = findViewById<EditText>(R.id.numero)
        //val editTextValue = editText.text
    //textView.setText("0").toString()
    // val textViewValue = textView.text
        Toast.makeText(this, "0", Toast.LENGTH_LONG).show()
    }
    public fun button_1(view: View) {
        Toast.makeText(this, "1", Toast.LENGTH_LONG).show()
    }
    public fun button_2(view: View) {
        Toast.makeText(this, "2", Toast.LENGTH_LONG).show()
    }
    public fun button_3(view: View) {
        Toast.makeText(this, "3", Toast.LENGTH_LONG).show()
    }
    public fun button_4(view: View) {
        Toast.makeText(this, "4", Toast.LENGTH_LONG).show()
    }
    public fun button_5(view: View) {
        Toast.makeText(this, "5", Toast.LENGTH_LONG).show()
    }
    public fun button_6(view: View) {
        Toast.makeText(this, "6", Toast.LENGTH_LONG).show()
    }
    public fun button_7(view: View) {
        Toast.makeText(this, "7", Toast.LENGTH_LONG).show()
    }
    public fun button_8(view: View) {
        Toast.makeText(this, "8", Toast.LENGTH_LONG).show()
    }
    public fun button_9(view: View) {
        Toast.makeText(this, "9", Toast.LENGTH_LONG).show()
    }
    public fun button_sign(view: View) {
        Toast.makeText(this, "*#", Toast.LENGTH_LONG).show()
    }
    public fun button_call(view: View) {
        Toast.makeText(this, "Appel", Toast.LENGTH_LONG).show()
    }
}**/