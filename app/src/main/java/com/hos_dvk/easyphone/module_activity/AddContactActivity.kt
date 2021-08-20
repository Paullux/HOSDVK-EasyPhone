package com.hos_dvk.easyphone.module_activity

import android.content.ContentProviderOperation
import android.content.Intent
import android.os.Build
import android.provider.ContactsContract
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.hos_dvk.easyphone.NUMBER_TO_CALL
import com.hos_dvk.easyphone.number

class AddContactActivity : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onResume() {
        super.onResume()
        setContentView(com.hos_dvk.easyphone.R.layout.activity_add_contact)
        val addContact = intent
        number = addContact?.getStringExtra(NUMBER_TO_CALL).toString()
        findViewById<TextView>(com.hos_dvk.easyphone.R.id.number_in_add).text = number
        val buttonAddContact = findViewById<Button>(com.hos_dvk.easyphone.R.id.add_contact)
        buttonAddContact.setOnClickListener {
            addContactInPhone(number)
        }
        val buttonCancelContact = findViewById<Button>(com.hos_dvk.easyphone.R.id.cancel_add_contact)
        buttonCancelContact.setOnClickListener {
            finish()
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun addContactInPhone(phone: String) {
        val contactFirstName =
            findViewById<EditText>(com.hos_dvk.easyphone.R.id.first_name).text.toString().trim()
        val contactLastName =
            findViewById<TextView>(com.hos_dvk.easyphone.R.id.last_name).text.toString().trim()

        val cpo = ArrayList<ContentProviderOperation>()
        val rawContactId = cpo.size
        cpo.add(
            ContentProviderOperation.newInsert(
                ContactsContract.RawContacts.CONTENT_URI
            )
                .withValue(ContactsContract.RawContacts.ACCOUNT_TYPE, null)
                .withValue(ContactsContract.RawContacts.ACCOUNT_NAME, null)
                .build()
        )
        cpo.add(
            ContentProviderOperation.newInsert(
                ContactsContract.Data.CONTENT_URI
            )
                .withValueBackReference(
                    ContactsContract.RawContacts.Data.RAW_CONTACT_ID,
                    rawContactId
                )
                .withValue(
                    ContactsContract.RawContacts.Data.MIMETYPE,
                    ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE
                )
                .withValue(
                    ContactsContract.CommonDataKinds.StructuredName.GIVEN_NAME,
                    contactFirstName
                )
                .withValue(
                    ContactsContract.CommonDataKinds.StructuredName.FAMILY_NAME,
                    contactLastName
                )
                .build()
        )
        cpo.add(
            ContentProviderOperation.newInsert(
                ContactsContract.Data.CONTENT_URI
            )
                .withValueBackReference(
                    ContactsContract.RawContacts.Data.RAW_CONTACT_ID,
                    rawContactId
                )
                .withValue(
                    ContactsContract.RawContacts.Data.MIMETYPE,
                    ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE
                )
                .withValue(ContactsContract.CommonDataKinds.Phone.NUMBER, phone)
                .withValue(
                    ContactsContract.CommonDataKinds.Phone.TYPE,
                    ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE
                )
                .build()
        )

        try {
            contentResolver.applyBatch(ContactsContract.AUTHORITY, cpo)
            Toast.makeText(
                this,
                getString(com.hos_dvk.easyphone.R.string.contact_added),
                Toast.LENGTH_LONG
            ).show()
            val intent = Intent(this, ContactActivity::class.java)
            startActivity(intent)
        } catch (e: Exception) {
            Toast.makeText(this, "failed tu save due to ${e.message}", Toast.LENGTH_LONG).show()
        }
    }

    fun goBack(@Suppress("UNUSED_PARAMETER")view: View) {
        this.finish()
    }
}