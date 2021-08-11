package com.hos_dvk.easyphone.query

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.hos_dvk.easyphone.data_class.ContactDataClass

class ContactAdapter(context: Context, users: MutableList<ContactDataClass>) :
    ArrayAdapter<ContactDataClass?>(context!!, 0, users as MutableList<ContactDataClass?>) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        // Get the data item for this position
        var convertView: View? = convertView
        val user: ContactDataClass? = getItem(position)
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(com.hos_dvk.easyphone.R.layout.style_of_text_list, parent, false)
        }
        // Lookup view for data population
        val personPhotoUri = convertView?.findViewById(com.hos_dvk.easyphone.R.id.profile_picture) as TextView
        val personName = convertView?.findViewById(com.hos_dvk.easyphone.R.id.person_name) as TextView
        val personNumber = convertView?.findViewById(com.hos_dvk.easyphone.R.id.person_number) as TextView
        // Populate the data into the template view using the data object
        personPhotoUri.text = user?.photoUri
        personName.text = user?.name
        personNumber.text = user?.number
        // Return the completed view to render on screen
        return convertView!!
    }
}