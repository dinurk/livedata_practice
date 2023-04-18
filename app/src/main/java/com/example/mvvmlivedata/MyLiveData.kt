package com.example.mvvmlivedata

import android.util.Log
import androidx.lifecycle.LiveData

class MyLiveData : LiveData<String>() {

    fun updateValue(string: String) {
        value = string
    }

    override fun onActive() {
        super.onActive()
        Log.i("myLiveData", "onActive")
    }

    override fun onInactive() {
        super.onInactive()
        Log.i("myLiveData","onInactive")
    }
}