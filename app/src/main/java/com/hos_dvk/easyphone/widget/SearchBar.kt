package com.hos_dvk.easyphone.widget

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import com.hos_dvk.easyphone.R
import java.util.*

class SearchBar : LinearLayout {
    var inputText: EditText? = null
        set(value) = value?.addTextChangedListener(SearchUpdater()) ?: Unit
    var resultView: ViewGroup? = null
    var viewFactory: ((Context, String) -> View)? = null

    var entries: Set<String> = Collections.emptySet()

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context,
        attrs,
        defStyleAttr) {
        init(attrs)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context,
        attrs) {
        init(attrs)
    }

    private fun init(attrs: AttributeSet?) {
        inputText = findViewById(attrs?.getAttributeNameResource(R.attr.inputView) ?: 0)
        resultView = findViewById(attrs?.getAttributeNameResource(R.attr.resultView) ?: 0)
        val elementView = attrs?.getAttributeNameResource(R.attr.elementView)
        viewFactory = if (elementView != null) { context, name ->
            val view = LayoutInflater.from(context).inflate(elementView, resultView)
            if (view is TextView) view.text = name
            view
        } else null
    }

    inner class SearchUpdater : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            entries.filter {
                it.split(" ")
                    .any { it.startsWith(s ?: "") }
            }.forEach {
                resultView?.addView(viewFactory?.invoke(context, it))
            }
        }

        override fun afterTextChanged(s: Editable?) {}

    }
}