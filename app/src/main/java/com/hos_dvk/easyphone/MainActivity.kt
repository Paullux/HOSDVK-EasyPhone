package com.hos_dvk.easyphone

import android.database.Cursor
import android.database.MatrixCursor
import android.os.Bundle
import android.provider.ContactsContract
import android.provider.ContactsContract.CommonDataKinds.Phone
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {
    protected override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
    var previousview = R.layout.activity_main
    var first_click = true
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
        remplir_contact()
    }
    public fun defiler(view: View) {
        val nomPersonne = view.findViewById<TextView>(R.id.nom_personne)
        val numPersonne = view.findViewById<TextView>(R.id.numero_personne)
        if (nomPersonne.isSelected) {
            aller_appeler(view)
            nomPersonne.isSelected = false
            numPersonne.isSelected = false
        }
        nomPersonne.isSelected = true
        numPersonne.isSelected = true
    }
    public fun choix_appeler(view: View) {
        var numero_a_appeler = findViewById<Button>(R.id.choix_envoyer_sms).text.toString()
        numero_a_appeler  = numero_a_appeler .replace("Envoyer un SMS à ", "")
        Toast.makeText(this, "Appel de " + numero_a_appeler, Toast.LENGTH_LONG).show()
    }
    public fun choix_envoyer_sms(view: View) {
        var sms_envoyer = findViewById<Button>(R.id.choix_envoyer_sms).text.toString()
        sms_envoyer  = sms_envoyer.replace("Envoyer un SMS à ", "")
        Toast.makeText(this, "sms à " + sms_envoyer, Toast.LENGTH_LONG).show()
    }
    public fun bouton_appel(view: View) {
        previousview = R.layout.module_appels
        var mon_nom_a_appeler = findViewById<TextView>(R.id.numero)
        val str: String = mon_nom_a_appeler.text.toString()
        setContentView(R.layout.module_choix_sms_appel)
        var nom_a_appeler = findViewById<TextView>(R.id.choix_appeler)
        nom_a_appeler.setText("Appeler " + str)
        var sms_envoyer = findViewById<TextView>(R.id.choix_envoyer_sms)
        sms_envoyer.setText("Envoyer un SMS à " + str)
    }
    fun remplir_contact() {

         val cursor : Cursor? = contentResolver.query(
            Phone.CONTENT_URI,
            null,
            null,
            null,
            Phone.DISPLAY_NAME + " ASC"
        )

        //startManagingCursor(cursor)

        var mc: MatrixCursor? = MatrixCursor(
            arrayOf(
                Phone._ID,
                Phone.PHOTO_URI,
                Phone.DISPLAY_NAME_PRIMARY,
                Phone.NUMBER,
            ),16
        )
        var lastName = ""
        var entry : Array<String?> = arrayOf("", "", "", "")

        if (cursor != null) {
            while(cursor.moveToNext()){
                var _id = cursor.getInt(cursor.getColumnIndex(Phone._ID))
                var photo = cursor.getString(cursor.getColumnIndex(Phone.PHOTO_URI))
                var name = cursor.getString(cursor.getColumnIndex(Phone.DISPLAY_NAME))
                var number = cursor.getString(cursor.getColumnIndex(Phone.NUMBER))

                //Some condition to check previous data is not matched and only then add row
                if(lastName != name){
                    if (lastName != "") mc?.addRow(entry)
                    entry = arrayOf(_id.toString(), photo, name, number)
                    lastName = name
                    continue
                }
                entry.set(3,entry.get(3) + ", " + number)
            }
            if (lastName != "") mc?.addRow(entry)
        }
        var from = arrayOf(
            Phone.PHOTO_URI,
            Phone.DISPLAY_NAME_PRIMARY,
            Phone.NUMBER)

        var to = intArrayOf(R.id.image_de_profil,R.id.nom_personne,R.id.numero_personne)

        var simple = SimpleCursorAdapter(this,R.layout.mon_style_liste_texte,mc,from,to,0)
        var liste_contacts = findViewById<ListView>(R.id.liste_contacts)
        liste_contacts.adapter = simple
        cursor?.close()

    }
    public fun aller_appeler(view: View) {
        previousview = R.layout.module_contacts
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
        setContentView(previousview)
        if (previousview == R.layout.module_appels) { previousview = R.layout.activity_main }
        if (previousview == R.layout.module_contacts) {
            remplir_contact()
            previousview = R.layout.activity_main
        }
    }
    public fun efface_texte(view: View) {
        val numero = findViewById<TextView>(R.id.numero)
        if (numero.text.toString() != null && numero.text.toString().trim() != "") {
            numero.setText(numero.text.toString().substring(0, numero.text.toString().length - 1))
        }
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

