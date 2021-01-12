package com.joaquimgomes.desafioandroidjoaquimgomes.data.commom

import android.content.Context
import android.widget.Toast

class SetToastMessage {

    fun setToastMessage(context: Context?, text: Int) {
        Toast.makeText(context, text, Toast.LENGTH_LONG).show()
    }

}