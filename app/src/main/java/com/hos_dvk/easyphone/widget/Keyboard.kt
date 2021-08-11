package com.hos_dvk.easyphone.widget

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import com.hos_dvk.easyphone.R


class Keyboard : LinearLayout {
    var inputView: View? = let {
        val parent = this.parent
        when (parent) {
            is View -> parent
            else -> null
        }
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context,
        attrs,
        defStyleAttr) {
        this.inputView = findViewById(attrs?.getAttributeNameResource(R.attr.inputView) ?: 0)
        this.setOnClickListener { if (it is KeyButton) it.onKeyboardClick(this) }
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(context,
        attrs) {
        this.inputView = findViewById(attrs?.getAttributeNameResource(R.attr.inputView) ?: 0)
        this.setOnClickListener { if (it is KeyButton) it.onKeyboardClick(this) }
    }
}