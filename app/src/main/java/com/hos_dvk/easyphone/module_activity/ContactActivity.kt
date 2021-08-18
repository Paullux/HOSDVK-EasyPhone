package com.hos_dvk.easyphone.module_activity

import android.content.Intent
import android.database.MatrixCursor
import android.os.Build
import android.provider.ContactsContract.CommonDataKinds.Phone
import android.view.ContextThemeWrapper
import android.view.MenuItem
import android.view.View
import android.widget.ListView
import android.widget.PopupMenu
import android.widget.SimpleCursorAdapter
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.iterator
import com.hos_dvk.easyphone.NAME_TO_CALL
import com.hos_dvk.easyphone.NUMBER_TO_CALL
import com.hos_dvk.easyphone.R
import com.hos_dvk.easyphone.data_class.ContactDataClass
import com.hos_dvk.easyphone.lastActivity
import com.hos_dvk.easyphone.query.ContactQuery
import com.hos_dvk.easyphone.widget.GoBack

class ContactActivity : AppCompatActivity() {
    override fun onResume() {
        super.onResume()
        setContentView(R.layout.activity_contact)
        lastActivity = "MainActivity"
        loadContacts()
    }

    private fun loadContacts() {
        val contactsListView = findViewById<ListView>(R.id.contacts_list)
        val contactsList: MutableList<ContactDataClass> =
            ContactQuery().getAll(contentResolver, this)
        val mc = MatrixCursor(
            arrayOf(
                Phone._ID,
                Phone.PHOTO_URI,
                Phone.DISPLAY_NAME_PRIMARY,
                Phone.NUMBER,
            ), 16
        )

        for(contact in contactsList) {
            val contactId: Int = (0..255).random()
            mc.addRow(arrayOf(contactId, contact.photoUri, contact.name, contact.number))
        }
        val from = arrayOf(
            Phone.PHOTO_URI,
            Phone.DISPLAY_NAME_PRIMARY,
            Phone.NUMBER)

        val to = intArrayOf(R.id.profile_picture, R.id.person_name, R.id.person_number)

        val simple = SimpleCursorAdapter(this, R.layout.style_of_contact_list, mc, from, to, 0)
        contactsListView.adapter = simple
    }

    @RequiresApi(Build.VERSION_CODES.M)
    fun defile(view: View) {
        val personName = view.findViewById<TextView>(R.id.person_name)
        val personNumber = view.findViewById<TextView>(R.id.person_number)
        val allPerson = findViewById<ListView>(R.id.contacts_list)

        if (personName.isSelected) {
            numberChoice(view)
            personName.isSelected = false
            personNumber.isSelected = false
        }
        for (person in allPerson) {
            val personNameHere = person.findViewById<TextView>(R.id.person_name)
            val personNumberHere = person.findViewById<TextView>(R.id.person_number)
            personNameHere.isSelected = false
            personNumberHere.isSelected = false
        }
        personName.isSelected = true
        personNumber.isSelected = true
    }
    @RequiresApi(Build.VERSION_CODES.M)
    private fun numberChoice(view: View) {
        val nameToCall = view.findViewById<TextView>(R.id.person_name)
        val numberToCall = view.findViewById<TextView>(R.id.person_number)
        val name = nameToCall.text.toString()
        val listNumber: Array<String> =
            numberToCall.text.split(", ").toTypedArray()
        if (listNumber.size > 1) {
            val wrapper = ContextThemeWrapper(this, R.style.MyPopupMenu)
            val popupMenu = PopupMenu(wrapper, view)
            popupMenu.menu.add(name)
            for (num in listNumber) {
                popupMenu.menu.add(num)
            }
            popupMenu.setOnMenuItemClickListener { item: MenuItem? ->
                when (item!!) {
                    item -> {
                        if (name != item.title) {
                            lastActivity = "ContactActivity"
                            val intent = Intent(this, RealCallActivity::class.java).apply {
                                putExtra(NAME_TO_CALL, nameToCall.text.toString())
                                putExtra(NUMBER_TO_CALL, item.toString())
                            }
                            startActivity(intent)
                        }
                    }
                }
                true
            }
            popupMenu.show()
        } else {
            lastActivity = "ContactActivity"
            val realCall = Intent(this, RealCallActivity::class.java).apply {
                putExtra(NAME_TO_CALL, nameToCall.text.toString())
                putExtra(NUMBER_TO_CALL, numberToCall.text.toString())
            }
            startActivity(realCall)
        }
    }

    fun goBack(@Suppress("UNUSED_PARAMETER")view: View) {
        GoBack().goBack(this)
    }
}