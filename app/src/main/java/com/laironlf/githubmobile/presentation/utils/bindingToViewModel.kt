package com.laironlf.githubmobile.presentation.utils

import android.widget.EditText
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData

fun EditText.bindTextTwoWay(liveData: MutableLiveData<String>, lifecycleOwner: LifecycleOwner) {
    this.doOnTextChanged { text, _, _, _ ->
        liveData.value = text.toString()
    }
    liveData.observe(lifecycleOwner) { text ->
        if(this.text.toString() == text) return@observe
        this.setText(text)
    }
}