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

        var listContacts: MutableList<ContactDataClass> = mutableListOf()
        var contactInfo: ContactDataClass? = null
        if (cursor?.moveToFirst()!!) {
            do {
                var cursorPhotoUri =
                    cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.PHOTO_URI))
                if (cursorPhotoUri == null) cursorPhotoUri =
                    context.resourceUri(R.drawable.ic_photo_name).toString()
                var cursorName =
                    cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME_PRIMARY))
                var cursorNumber =
                    cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))
                cursorNumber = cursorNumber.replace(" ", "")

                cursorNumber = cursorNumber.replace(" ", "")

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
                    if (!storedContact.number.replace("+33", "0").contains(cursorNumber.replace("+33", "0")))
                        storedContact.number += ", $cursorNumber"
                } else {
                    if (cursorPhotoUri == null) cursorPhotoUri =
                        context.resourceUri(R.drawable.ic_photo_name).toString()

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