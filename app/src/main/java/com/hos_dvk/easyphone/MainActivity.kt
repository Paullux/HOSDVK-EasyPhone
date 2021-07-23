package com.hos_dvk.easyphone

import android.content.Context
import android.database.Cursor
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.provider.ContactsContract
import android.view.View
import android.widget.*
import androidx.annotation.ColorRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.get
import androidx.core.view.iterator
import java.util.*
import com.google.android.material.imageview.ShapeableImageView

class MainActivity : AppCompatActivity() {
    protected override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    public fun APPEL(view: View) {
        //Toast.makeText(this, "APPEL", Toast.LENGTH_LONG).show()
        setContentView(R.layout.module_appels)
    }
    public fun SMS(view: View) {
        Toast.makeText(this, "SMS", Toast.LENGTH_LONG).show()
    }
    public fun CONTACTS(view: View) {
        //Toast.makeText(this, "CONTACTS", Toast.LENGTH_LONG).show()
        setContentView(R.layout.module_contacts)
        read()
    }
    public fun choix_appeler(view: View) {
        var nom_a_appeler = findViewById<Button>(R.id.choix_appeler).text.toString()
        Toast.makeText(this, nom_a_appeler, Toast.LENGTH_LONG).show()
    }
    public fun choix_envoyer_sms(view: View) {
        var sms_envoyer = findViewById<Button>(R.id.choix_envoyer_sms).text.toString()
        Toast.makeText(this, sms_envoyer, Toast.LENGTH_LONG).show()
    }
    public fun bouton_appel(view: View) {
        var mon_nom_a_appeler = findViewById<TextView>(R.id.numero)
        val str: String = mon_nom_a_appeler.text.toString()
        setContentView(R.layout.module_choix_sms_appel)
        var nom_a_appeler = findViewById<TextView>(R.id.choix_appeler)
        nom_a_appeler.setText("Appeler " + str)
        var sms_envoyer = findViewById<TextView>(R.id.choix_envoyer_sms)
        sms_envoyer.setText("Envoyer un SMS à " + str)
    }
    fun read() {
        var cursor : Cursor? = contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,null, null, null, null)
        startManagingCursor(cursor)

        var from = arrayOf(ContactsContract.CommonDataKinds.Phone.PHOTO_URI,
            ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
            ContactsContract.CommonDataKinds.Phone.NUMBER,
            ContactsContract.CommonDataKinds.Phone._ID)

        var to = intArrayOf(R.id.image_de_profil,R.id.nom_personne,R.id.numero_personne)

        var simple : SimpleCursorAdapter = SimpleCursorAdapter(this,R.layout.mon_style_liste_texte,cursor,from,to)

        val liste_contacts = findViewById<ListView>(R.id.liste_contacts)

        liste_contacts.adapter = simple
    }
    public fun aller_appeler(view: View) {
        var mon_nom_a_appeler = view.findViewById<TextView>(R.id.nom_personne)
        var mon_numero_sms = view.findViewById<TextView>(R.id.numero_personne)
        val str1: String = mon_nom_a_appeler.text.toString()
        val str2: String = mon_numero_sms.text.toString()
        setContentView(R.layout.module_choix_sms_appel)
        var nom_a_appeler = findViewById<TextView>(R.id.choix_appeler)
        nom_a_appeler.setText("Appeler " + str1)
        var sms_envoyer = findViewById<TextView>(R.id.choix_envoyer_sms)
        sms_envoyer.setText("Envoyer un SMS à " + str2)

    }
    public fun EMAILS(view: View) {
        Toast.makeText(this, "EMAILS", Toast.LENGTH_LONG).show()
    }
    public fun PHOTOS(view: View) {
        Toast.makeText(this, "PHOTOS", Toast.LENGTH_LONG).show()
    }
    public fun Retour(view: View) {
        setContentView(R.layout.activity_main)
    }
    public fun button_0(view: View) {
        val numero = findViewById<TextView>(R.id.numero)
        if (numero.text.toString() == null || numero.text.toString().trim()==""){
            numero.setText("0").toString()
        }else{
            numero.setText(numero.text.toString() + "0").toString()
        }
    }
    public fun button_1(view: View) {
        val numero = findViewById<TextView>(R.id.numero)
        if (numero.text.toString() == null || numero.text.toString().trim()==""){
            numero.setText("1").toString()
        }else{
            numero.setText(numero.text.toString() + "1").toString()
        }
    }
    public fun button_2(view: View) {
        val numero = findViewById<TextView>(R.id.numero)
        if (numero.text.toString() == null || numero.text.toString().trim()==""){
            numero.setText("2").toString()
        }else{
            numero.setText(numero.text.toString() + "2").toString()
        }
    }
    public fun button_3(view: View) {
        val numero = findViewById<TextView>(R.id.numero)
        if (numero.text.toString() == null || numero.text.toString().trim()==""){
            numero.setText("3").toString()
        }else{
            numero.setText(numero.text.toString() + "3").toString()
        }
    }
    public fun button_4(view: View) {
        val numero = findViewById<TextView>(R.id.numero)
        if (numero.text.toString() == null || numero.text.toString().trim()==""){
            numero.setText("4").toString()
        }else{
            numero.setText(numero.text.toString() + "4").toString()
        }
    }
    public fun button_5(view: View) {
        val numero = findViewById<TextView>(R.id.numero)
        if (numero.text.toString() == null || numero.text.toString().trim()==""){
            numero.setText("5").toString()
        }else{
            numero.setText(numero.text.toString() + "5").toString()
        }
    }
    public fun button_6(view: View) {
        val numero = findViewById<TextView>(R.id.numero)
        if (numero.text.toString() == null || numero.text.toString().trim()==""){
            numero.setText("6").toString()
        }else{
            numero.setText(numero.text.toString() + "6").toString()
        }
    }
    public fun button_7(view: View) {
        val numero = findViewById<TextView>(R.id.numero)
        if (numero.text.toString() == null || numero.text.toString().trim()==""){
            numero.setText("7").toString()
        }else{
            numero.setText(numero.text.toString() + "7").toString()
        }
    }
    public fun button_8(view: View) {
        val numero = findViewById<TextView>(R.id.numero)
        if (numero.text.toString() == null || numero.text.toString().trim()==""){
            numero.setText("8").toString()
        }else{
            numero.setText(numero.text.toString() + "8").toString()
        }
    }
    public fun button_9(view: View) {
        val numero = findViewById<TextView>(R.id.numero)
        if (numero.text.toString() == null || numero.text.toString().trim()==""){
            numero.setText("9").toString()
        }else{
            numero.setText(numero.text.toString() + "9").toString()
        }
    }
    public fun button_sign(view: View) {
        val numero = findViewById<TextView>(R.id.numero)
        if (numero.text.toString() == null || numero.text.toString().trim()==""){
            numero.setText("+").toString()
        }else{
            numero.setText(numero.text.toString() + "+").toString()
        }
    }
    public fun button_call(view: View) {
        Toast.makeText(this, "Appel", Toast.LENGTH_LONG).show()
    }
}
