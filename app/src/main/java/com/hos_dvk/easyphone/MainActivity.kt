package com.hos_dvk.easyphone

import android.Manifest
import android.app.AlertDialog
import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.database.MatrixCursor
import android.graphics.Typeface
import android.net.Uri
import android.os.*
import android.provider.ContactsContract.CommonDataKinds.Phone
import android.text.Editable
import android.text.TextWatcher
import android.util.TypedValue
import android.view.ContextThemeWrapper
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.widget.AppCompatTextView
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.iterator
import com.ddd.androidutils.DoubleClick
import com.ddd.androidutils.DoubleClickListener
import com.mancj.materialsearchbar.MaterialSearchBar
import java.lang.reflect.Field
import java.util.*


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    //--------Initialisation variable globale---------------
    var previousview = R.layout.activity_main
    val PERMISSIONS_REQUEST_READ_CONTACTS = 1
    val PERMISSIONS_REQUEST_CALL_PHONE = 42
    var firstContacts = true
    var firstCall = true

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
        if (checkVal == PackageManager.PERMISSION_GRANTED) {
            setContentView(R.layout.module_contacts)
            remplir_contact()
        } else if (!firstContacts) {
            Toast.makeText(
                this,
                "Vous avez refusé à Easy-Phone l'accès aux contacts du téléphone",
                Toast.LENGTH_LONG
            ).show()
        }
        if (firstContacts) firstContacts = false
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
        if (previousview == R.layout.module_appels) {
            previousview = R.layout.activity_main
        }
        if (previousview == R.layout.module_contacts) {
            remplir_contact()
            previousview = R.layout.activity_main
        }
    }

    override fun onBackPressed() {
        setContentView(previousview)
        if (previousview == R.layout.module_appels) {
            previousview = R.layout.activity_main
        }
        if (previousview == R.layout.module_contacts) {
            remplir_contact()
            previousview = R.layout.activity_main
        }
    }
    @RequiresApi(Build.VERSION_CODES.M)
    fun choix_appeler(view: View) {
        requestCallPermission()
        var numero_a_appeler = findViewById<Button>(R.id.choix_envoyer_sms).text.toString()
        numero_a_appeler  = numero_a_appeler .replace("Envoyer un SMS à ", "")
        Toast.makeText(this, "Appel de " + numero_a_appeler, Toast.LENGTH_LONG).show()
        val requiredPermission = Manifest.permission.CALL_PHONE
        val checkVal = checkSelfPermission(requiredPermission)
        if (checkVal == PackageManager.PERMISSION_GRANTED) {
            passcall(numero_a_appeler)
        } else if (!firstCall) {
            Toast.makeText(
                this,
                "Vous avez refusé à Easy-Phone l'accès aux appels par le téléphone",
                Toast.LENGTH_LONG
            ).show()
        }
        if (firstCall) firstCall = false
    }
    fun passcall(numero_a_appeler : String) {
        val intent = Intent(Intent.ACTION_CALL, Uri.parse("tel:" + numero_a_appeler))
        startActivity(intent)
    }
    fun choix_envoyer_sms(view: View) {
        var sms_envoyer = findViewById<Button>(R.id.choix_envoyer_sms).text.toString()
        sms_envoyer  = sms_envoyer.replace("Envoyer un SMS à ", "")
        Toast.makeText(this, "sms à " + sms_envoyer, Toast.LENGTH_LONG).show()
    }
    fun Context.resourceUri(resourceId: Int): Uri = with(resources) {
        Uri.Builder()
            .scheme(ContentResolver.SCHEME_ANDROID_RESOURCE)
            .authority(getResourcePackageName(resourceId))
            .appendPath(getResourceTypeName(resourceId))
            .appendPath(getResourceEntryName(resourceId))
            .build()
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
        val efface = findViewById<Button>(R.id.button_delete)
        val doubleClick = DoubleClick(object : DoubleClickListener {
            override fun onSingleClickEvent(view: View?) {
                if (numero.text.toString() != null && numero.text.toString().trim()!="")
                numero.text = numero.text.toString().substring(0, numero.text.toString().length - 1)
            }
            override fun onDoubleClickEvent(view: View?) {
                if (numero.text.toString() != null && numero.text.toString().trim()!="")
                    numero.text = ""
            }
        })
        efface.setOnClickListener(doubleClick)
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
        val cursor: Cursor? = contentResolver.query(
            Phone.CONTENT_URI,
            null,
            null,
            null,
            Phone.DISPLAY_NAME + " ASC"
        )

        var mc: MatrixCursor = MatrixCursor(
            arrayOf(
                Phone._ID,
                Phone.PHOTO_URI,
                Phone.DISPLAY_NAME_PRIMARY,
                Phone.NUMBER,
            ), 16
        )
        var contacts: MutableList<Array<String>> = mutableListOf()
        if (cursor != null) {
            while (cursor.moveToNext()) {
                var id = cursor.getInt(cursor.getColumnIndex(Phone._ID)).toString()
                var photo = cursor.getString(cursor.getColumnIndex(Phone.PHOTO_URI))
                var name = cursor.getString(cursor.getColumnIndex(Phone.DISPLAY_NAME))
                var number = cursor.getString(cursor.getColumnIndex(Phone.NUMBER))

                number = number.replace(" ", "")

                var alreadyExistContactId: Int? = null
                val iterator = contacts.iterator()
                for ((index, contact) in iterator.withIndex()) {
                    if (contact[2] == name) {
                        alreadyExistContactId = index
                        break
                    }
                }
                var storedContact: Array<String>
                if (alreadyExistContactId != null) {
                    storedContact = contacts[alreadyExistContactId]
                    if (!storedContact[3].replace("+33", "0").contains(number.replace("+33", "0")))
                        storedContact[3] += ", $number"
                } else {
                    if (photo == null) {
                        photo = resourceUri(R.drawable.ic_photo_name)
                            .toString()
                    }
                    storedContact = arrayOf(id, photo, name, number)
                    contacts.add(storedContact)
                }
            }
            for (contact in contacts) {
                mc.addRow(contact)
            }
        }
        var from = arrayOf(
            Phone.PHOTO_URI,
            Phone.DISPLAY_NAME_PRIMARY,
            Phone.NUMBER)

        var to = intArrayOf(R.id.image_de_profil, R.id.nom_personne, R.id.numero_personne)

        var simple = SimpleCursorAdapter(this, R.layout.mon_style_liste_texte, mc, from, to, 0)
        var liste_contacts = findViewById<ListView>(R.id.liste_contacts)
        liste_contacts.adapter = simple
        cursor?.close()
        SeachBar(liste_contacts, contacts, from, to, simple)
    }
    fun SeachBar(liste_contacts : ListView, contacts : MutableList<Array<String>>, from : Array<String>, to : IntArray, simple : SimpleCursorAdapter) {
        var mc: MatrixCursor
        val searchBar = findViewById<MaterialSearchBar>(R.id.search_bar).also {
            it.setHint("Rechercher...")
            it.setSpeechMode(false)
            it.addTextChangeListener(object : TextWatcher {
                override fun beforeTextChanged(charSequence: CharSequence?, start: Int, count: Int, after: Int) {

                }
                override fun onTextChanged(charSequence: CharSequence?, start: Int, before: Int, count: Int) {

                }
                override fun afterTextChanged(editable: Editable?) {
                    if (editable.toString() != "") {
                        mc = MatrixCursor(
                            arrayOf(
                                Phone._ID,
                                Phone.PHOTO_URI,
                                Phone.DISPLAY_NAME_PRIMARY,
                                Phone.NUMBER,
                            ), 16
                        )
                        for (contact in contacts) {
                            if (contact[2].lowercase().contains(editable.toString().lowercase())) { mc.addRow(contact) }
                        }
                    } else {
                        mc = MatrixCursor(
                            arrayOf(
                                Phone._ID,
                                Phone.PHOTO_URI,
                                Phone.DISPLAY_NAME_PRIMARY,
                                Phone.NUMBER,
                            ), 16
                        )
                        for (contact in contacts) {
                            mc.addRow(contact)
                        }
                    }

                    var simple = SimpleCursorAdapter(this@MainActivity, R.layout.mon_style_liste_texte, mc, from, to, 0)
                    liste_contacts.adapter = simple

                }
            })
        }
        val constraintLayout =
            ((searchBar.getChildAt(0) as CardView)?.getChildAt(0) as? ConstraintLayout)
        val placeholderTextView = constraintLayout?.getChildAt(1) as AppCompatTextView
        val editTextView =
            (constraintLayout?.getChildAt(2) as LinearLayout)?.getChildAt(1) as AppCompatEditText
        editTextView.textSize = 35f
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
    private fun requestCallPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.CALL_PHONE
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(
                        this,
                        Manifest.permission.CALL_PHONE
                    )
                ) {
                    val builder: AlertDialog.Builder = AlertDialog.Builder(this)
                    builder.setTitle("Permission d'accès aux appels")
                    builder.setPositiveButton(android.R.string.ok, null)
                    builder.setMessage("Veuillez Autoriser l'accès aux appels par le téléphone.")
                    builder.setOnDismissListener {
                        requestPermissions(
                            arrayOf(
                                Manifest.permission.CALL_PHONE
                            ), PERMISSIONS_REQUEST_CALL_PHONE
                        )
                    }
                    builder.show()
                } else {
                    ActivityCompat.requestPermissions(
                        this, arrayOf(Manifest.permission.CALL_PHONE),
                        PERMISSIONS_REQUEST_CALL_PHONE
                    )
                }
            }
        }
    }
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray,
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
                PERMISSIONS_REQUEST_CALL_PHONE -> {
                    if (grantResults.isNotEmpty()
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED
                    ) {
                        var numero_a_appeler = findViewById<Button>(R.id.choix_envoyer_sms).text.toString()
                        numero_a_appeler  = numero_a_appeler .replace("Envoyer un SMS à ", "")
                        passcall(numero_a_appeler)
                    } else {
                        Toast.makeText(
                            this,
                            "Vous avez refusé à Easy-Phone l'accès aux appels par le téléphone",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                return
            }
        }
    }
}



