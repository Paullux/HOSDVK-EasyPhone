package com.hos_dvk.easyphone.widget

import android.content.Context
import android.util.AttributeSet
import android.view.KeyEvent
import android.view.inputmethod.BaseInputConnection
import androidx.appcompat.widget.AppCompatButton
import com.hos_dvk.easyphone.R

class RawKeyButton : AppCompatButton, KeyButton {
    var key: Int

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context,
        attrs,
        defStyleAttr) {
        this.key = attrs?.getAttributeIntValue(R.attr.inputKey, KeyEvent.KEYCODE_UNKNOWN)
            ?: KeyEvent.KEYCODE_UNKNOWN
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context,
        attrs) {
        this.key = attrs?.getAttributeIntValue(R.attr.inputKey, KeyEvent.KEYCODE_UNKNOWN)
            ?: KeyEvent.KEYCODE_UNKNOWN
    }

    override fun onKeyboardClick(keyboard: Keyboard) {
        val mInputConnection = BaseInputConnection(keyboard.inputView, true)
        mInputConnection.sendKeyEvent(KeyEvent(KeyEvent.ACTION_DOWN, key))
    }
}
