package com.hos_dvk.easyphone

import android.Manifest
import android.app.AlertDialog
import android.content.pm.PackageManager
import android.database.Cursor
import android.database.MatrixCursor
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.ContactsContract.CommonDataKinds.Phone
import android.view.ContextThemeWrapper
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.iterator
import java.util.*
import kotlin.concurrent.schedule
import kotlin.concurrent.timerTask


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
//--------Initialisation variable globale---------------
    var previousview = R.layout.activity_main
    val PERMISSIONS_REQUEST_READ_CONTACTS = 1
    var first = true
    private var isFirst = true
//--------Fonction de Base------------------------------
    fun APPEL(view: View) {
        //Toast.makeText(this, "APPEL", Toast.LENGTH_LONG).show()
        setContentView(R.layout.module_appels)
    }
    @RequiresApi(Build.VERSION_CODES.M)
    fun CONTACTS(view: View) {
        //Toast.makeText(this, "CONTACTS", Toast.LENGTH_LONG).show()
        requestContactPermission()
        val requiredPermission = Manifest.permission.READ_CONTACTS
        val checkVal = checkSelfPermission(requiredPermission)
        if (checkVal==PackageManager.PERMISSION_GRANTED) {
            setContentView(R.layout.module_contacts)
            remplir_contact()
        } else if(!first) {
            Toast.makeText(
                this,
                "Vous avez refusé à Easy-Phone l'accès aux contacts du téléphone",
                Toast.LENGTH_LONG
            ).show()
        }
        if(!first) first = true
    }
    fun SMS(view: View) {
        Toast.makeText(this, "SMS", Toast.LENGTH_LONG).show()
    }
    fun EMAILS(view: View) {
        Toast.makeText(this, "EMAILS", Toast.LENGTH_LONG).show()
    }
    fun PHOTOS(view: View) {
        Toast.makeText(this, "PHOTOS", Toast.LENGTH_LONG).show()
    }
//------------------Fonctions globales------------------------------
    fun Retour(view: View) {
        setContentView(previousview)
        if (previousview == R.layout.module_appels) { previousview = R.layout.activity_main }
        if (previousview == R.layout.module_contacts) {
            remplir_contact()
            previousview = R.layout.activity_main
        }
    }
    override fun onBackPressed() {
        setContentView(previousview)
        if (previousview == R.layout.module_appels) { previousview = R.layout.activity_main }
        if (previousview == R.layout.module_contacts) {
            remplir_contact()
            previousview = R.layout.activity_main
        }
    }
//------------------fonctions liées aux modules appels--------------
    fun bouton_appel(view: View) {
        previousview = R.layout.module_appels
        var mon_nom_a_appeler = findViewById<TextView>(R.id.numero)
        val str: String = mon_nom_a_appeler.text.toString()
        setContentView(R.layout.module_choix_sms_appel)
        var nom_a_appeler = findViewById<TextView>(R.id.choix_appeler)
        nom_a_appeler.setText("Appeler " + str)
        var sms_envoyer = findViewById<TextView>(R.id.choix_envoyer_sms)
        sms_envoyer.setText("Envoyer un SMS à " + str)
    }
    fun efface_texte(view: View) {
        val numero = findViewById<TextView>(R.id.numero)
        if (numero.text.toString() != null && numero.text.toString().trim() != "" && isFirst) {
            numero.text = numero.text.toString().substring(0, numero.text.toString().length - 1)
        }
        if (numero.text.toString() != null && numero.text.toString().trim() != "" && !isFirst) {
            Timer().schedule(timerTask {
                numero.text = ""
            }, 500)
        }
        isFirst = !isFirst
    }
    fun button_0(view: View) {
        val numero = findViewById<TextView>(R.id.numero)
        if (numero.text.toString() == null || numero.text.toString().trim()==""){
            numero.setText("0").toString()
        }else{
            numero.setText(numero.text.toString() + "0").toString()
        }
    }
    fun button_1(view: View) {
        val numero = findViewById<TextView>(R.id.numero)
        if (numero.text.toString() == null || numero.text.toString().trim()==""){
            numero.setText("1").toString()
        }else{
            numero.setText(numero.text.toString() + "1").toString()
        }
    }
    fun button_2(view: View) {
        val numero = findViewById<TextView>(R.id.numero)
        if (numero.text.toString() == null || numero.text.toString().trim()==""){
            numero.setText("2").toString()
        }else{
            numero.setText(numero.text.toString() + "2").toString()
        }
    }
    fun button_3(view: View) {
        val numero = findViewById<TextView>(R.id.numero)
        if (numero.text.toString() == null || numero.text.toString().trim()==""){
            numero.setText("3").toString()
        }else{
            numero.setText(numero.text.toString() + "3").toString()
        }
    }
    fun button_4(view: View) {
        val numero = findViewById<TextView>(R.id.numero)
        if (numero.text.toString() == null || numero.text.toString().trim()==""){
            numero.setText("4").toString()
        }else{
            numero.setText(numero.text.toString() + "4").toString()
        }
    }
    fun button_5(view: View) {
        val numero = findViewById<TextView>(R.id.numero)
        if (numero.text.toString() == null || numero.text.toString().trim()==""){
            numero.setText("5").toString()
        }else{
            numero.setText(numero.text.toString() + "5").toString()
        }
    }
    fun button_6(view: View) {
        val numero = findViewById<TextView>(R.id.numero)
        if (numero.text.toString() == null || numero.text.toString().trim()==""){
            numero.setText("6").toString()
        }else{
            numero.setText(numero.text.toString() + "6").toString()
        }
    }
    fun button_7(view: View) {
        val numero = findViewById<TextView>(R.id.numero)
        if (numero.text.toString() == null || numero.text.toString().trim()==""){
            numero.setText("7").toString()
        }else{
            numero.setText(numero.text.toString() + "7").toString()
        }
    }
    fun button_8(view: View) {
        val numero = findViewById<TextView>(R.id.numero)
        if (numero.text.toString() == null || numero.text.toString().trim()==""){
            numero.setText("8").toString()
        }else{
            numero.setText(numero.text.toString() + "8").toString()
        }
    }
    fun button_9(view: View) {
        val numero = findViewById<TextView>(R.id.numero)
        if (numero.text.toString() == null || numero.text.toString().trim()==""){
            numero.setText("9").toString()
        }else{
            numero.setText(numero.text.toString() + "9").toString()
        }
    }
    fun button_sign(view: View) {
        val numero = findViewById<TextView>(R.id.numero)
        if (numero.text.toString() == null || numero.text.toString().trim()==""){
            numero.setText("+").toString()
        }else{
            numero.setText(numero.text.toString() + "+").toString()
        }
    }
//----------Fonctions liées au module contacts--------------------------------------------------
    fun defiler(view: View) {
        val nomPersonne = view.findViewById<TextView>(R.id.nom_personne)
        val numPersonne = view.findViewById<TextView>(R.id.numero_personne)
        val allPersonne = findViewById<ListView>(R.id.liste_contacts)

        if (nomPersonne.isSelected) {
            choix_numero(view)
            nomPersonne.isSelected = false
            numPersonne.isSelected = false
        }
        for (personne in allPersonne) {
            var nomPersonneHere = personne.findViewById<TextView>(R.id.nom_personne)
            var numPersonneHere = personne.findViewById<TextView>(R.id.numero_personne)
            nomPersonneHere.isSelected = false
            numPersonneHere.isSelected = false
        }
        nomPersonne.isSelected = true
        numPersonne.isSelected = true
    }
    fun remplir_contact() {
        val cursor : Cursor? = contentResolver.query(
            Phone.CONTENT_URI,
            null,
            null,
            null,
            Phone.DISPLAY_NAME + " ASC"
        )

        var mc: MatrixCursor? = MatrixCursor(
            arrayOf(
                Phone._ID,
                Phone.PHOTO_URI,
                Phone.DISPLAY_NAME_PRIMARY,
                Phone.NUMBER,
            ),16
        )
        var contacts : MutableList<Array<String>> = mutableListOf()
        if (cursor != null) {
            while(cursor.moveToNext()){
                var id = cursor.getInt(cursor.getColumnIndex(Phone._ID)).toString()
                var photo = cursor.getString(cursor.getColumnIndex(Phone.PHOTO_URI))
                var name = cursor.getString(cursor.getColumnIndex(Phone.DISPLAY_NAME))
                var number = cursor.getString(cursor.getColumnIndex(Phone.NUMBER))

                number = number.replace(" ", "")

                var alreadyExistContactId : Int? = null
                val iterator = contacts.iterator()
                for((index,contact) in iterator.withIndex()) {
                    if (contact[2] == name) {
                        alreadyExistContactId = index
                        break
                    }
                }
                var storedContact : Array<String>
                if (alreadyExistContactId != null) {
                    storedContact = contacts[alreadyExistContactId]
                    if (!storedContact[3].contains(number))
                        storedContact[3] += ", $number"
                } else {
                    if (photo == null) {
                        photo = Uri.parse(
                            "android.resource://com.hos_dvk.easyphone/drawable/ic_photo_name")
                            .toString()
                    }
                    storedContact = arrayOf(id, photo, name, number)
                    contacts.add(storedContact)
                }
            }
            for (contact in contacts) {
                mc?.addRow(contact)
            }
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
    fun choix_numero(view: View) {
        var mon_nom_a_appeler = view.findViewById<TextView>(R.id.nom_personne)
        var nom = mon_nom_a_appeler.text.toString()
        var listNum : Array<String> =
            view.findViewById<TextView>(R.id.numero_personne).text.split(", ").toTypedArray()
        if (listNum.size > 1) {
            val wrapper = ContextThemeWrapper(this, R.style.MyPopupMenu)
            val popupMenu = PopupMenu(wrapper, view)
            popupMenu.menu.add(nom)
            for (num in listNum) {
                popupMenu.menu.add(num)
            }
            popupMenu.setOnMenuItemClickListener(PopupMenu.OnMenuItemClickListener { item: MenuItem? ->
                when (item!!) {
                    item -> {
                        if (nom != item.title) aller_appeler(mon_nom_a_appeler, item.toString())
                    }
                }
                true
            })
            popupMenu.show()
        } else {
            var mon_num_a_appeler = view.findViewById<TextView>(R.id.numero_personne)
            aller_appeler(mon_nom_a_appeler, mon_num_a_appeler.text.toString())
        }
    }
    fun aller_appeler(mon_nom_a_appeler: TextView, mon_numero_sms: String) {
        previousview = R.layout.module_contacts
        val str1: String = mon_nom_a_appeler.text.toString()
        val str2: String = mon_numero_sms
        setContentView(R.layout.module_choix_sms_appel)
        var nom_a_appeler = findViewById<TextView>(R.id.choix_appeler)
        nom_a_appeler.setText("Appeler " + str1)
        var sms_envoyer = findViewById<TextView>(R.id.choix_envoyer_sms)
        sms_envoyer.setText("Envoyer un SMS à " + str2)

    }
    fun choix_appeler(view: View) {
        var numero_a_appeler = findViewById<Button>(R.id.choix_envoyer_sms).text.toString()
        numero_a_appeler  = numero_a_appeler .replace("Envoyer un SMS à ", "")
        Toast.makeText(this, "Appel de " + numero_a_appeler, Toast.LENGTH_LONG).show()
    }
    fun choix_envoyer_sms(view: View) {
        var sms_envoyer = findViewById<Button>(R.id.choix_envoyer_sms).text.toString()
        sms_envoyer  = sms_envoyer.replace("Envoyer un SMS à ", "")
        Toast.makeText(this, "sms à " + sms_envoyer, Toast.LENGTH_LONG).show()
    }
//-----------fonctions liées aux permissions------------------------
    private fun requestContactPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.READ_CONTACTS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(
                        this,
                        Manifest.permission.READ_CONTACTS
                    )
                ) {
                    val builder: AlertDialog.Builder = AlertDialog.Builder(this)
                    builder.setTitle("Permission d'accès aux contacts")
                    builder.setPositiveButton(android.R.string.ok, null)
                    builder.setMessage("Veuillez Autoriser l'accès aux contact du téléphone.")
                    builder.setOnDismissListener {
                        requestPermissions(
                            arrayOf(
                                Manifest.permission.READ_CONTACTS
                            ), PERMISSIONS_REQUEST_READ_CONTACTS
                        )
                    }
                    builder.show()
                } else {
                    ActivityCompat.requestPermissions(
                        this, arrayOf(Manifest.permission.READ_CONTACTS),
                        PERMISSIONS_REQUEST_READ_CONTACTS
                    )
                }
            }
        }
    }
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            PERMISSIONS_REQUEST_READ_CONTACTS -> {
                if (grantResults.isNotEmpty()
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED
                ) {
                    setContentView(R.layout.module_contacts)
                    remplir_contact()
                } else {
                    Toast.makeText(
                        this,
                        "Vous avez refusé à Easy-Phone l'accès aux contacts du téléphone",
                        Toast.LENGTH_LONG
                    ).show()
                }
                return
            }
        }
    }
}

