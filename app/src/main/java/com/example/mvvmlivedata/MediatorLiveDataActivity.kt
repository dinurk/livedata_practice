package com.example.mvvmlivedata

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData

class MediatorLiveDataActivity : AppCompatActivity() {

    private val liveData1 = MutableLiveData<String>()
    private val liveData2 = MutableLiveData<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mediator_live_data)

        val editText = findViewById<EditText>(R.id.myEditText)

        val saveButton1 = findViewById<Button>(R.id.save_livedata_1)
        saveButton1.setOnClickListener(){ view ->
            liveData1.value = editText.text.toString()
        }

        val saveButton2 = findViewById<Button>(R.id.save_livedata_2)
        saveButton2.setOnClickListener(){ view ->
            liveData2.value = editText.text.toString()
        }

        val textViewLiveData1 = findViewById<TextView>(R.id.text_view_livedata_1)
        val textViewLiveData2 = findViewById<TextView>(R.id.text_view_livedata_2)

        liveData1.observe(this) {
            textViewLiveData1.text = it
        }

        liveData2.observe(this) {
            textViewLiveData2.text = it
        }

        val textViewLastSavedValue = findViewById<TextView>(R.id.text_view_last_saved_value)

        val liveDataMerger = MediatorLiveData<String>()

        liveDataMerger.addSource(liveData1) {
            liveDataMerger.value = it
        }
        liveDataMerger.addSource(liveData2) {
            liveDataMerger.value = it
        }

        liveDataMerger.observe(this) {
            textViewLastSavedValue.text = it
        }
    }
}