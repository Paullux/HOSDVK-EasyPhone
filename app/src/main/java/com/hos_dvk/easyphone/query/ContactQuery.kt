package com.hos_dvk.easyphone.query

import android.content.ContentResolver
import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.provider.ContactsContract
import com.hos_dvk.easyphone.R
import com.hos_dvk.easyphone.data_class.ContactDataClass

class ContactQuery {
    fun getAll(contentResolver: ContentResolver, context: Context): MutableList<ContactDataClass> {
        val cursor: Cursor? = contentResolver.query(
            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
            null,
            null,
            null,
            ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " ASC"
        )

        val listContacts: MutableList<ContactDataClass> = mutableListOf()
        var contactInfo: ContactDataClass?

        if (cursor?.moveToFirst()!!) {
            do {
                var cursorPhotoUri =
                    cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.PHOTO_URI))
                if (cursorPhotoUri == null) cursorPhotoUri =
                    context.resourceUri(R.drawable.ic_photo_name).toString()
                val cursorName =
                    cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME_PRIMARY))
                var cursorNumber =
                    cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))
                cursorNumber = ToInternationalNumberPhone().transform(cursorNumber, context)

                var alreadyExistContactId: Int? = null
                val iterator = listContacts.iterator()
                for ((index, contactInfo) in iterator.withIndex()) {
                    if (contactInfo.name.lowercase() == cursorName.lowercase()) {
                        alreadyExistContactId = index
                        break
                    }
                }
                var storedContact: ContactDataClass
                if (alreadyExistContactId != null) {
                    storedContact = listContacts[alreadyExistContactId]
                    val number = storedContact.number
                    if (!number.contains(cursorNumber))
                        storedContact.number += ", $cursorNumber"
                } else {

                    contactInfo = ContactDataClass(
                        cursorPhotoUri,
                        cursorName,
                        cursorNumber)
                    listContacts.add(contactInfo)
                }
            } while (cursor.moveToNext())
            cursor.close()
        }
        return listContacts
    }
    private fun Context.resourceUri(resourceId: Int): Uri = with(resources) {
        Uri.Builder()
            .scheme(ContentResolver.SCHEME_ANDROID_RESOURCE)
            .authority(getResourcePackageName(resourceId))
            .appendPath(getResourceTypeName(resourceId))
            .appendPath(getResourceEntryName(resourceId))
            .build()
    }
}