package com.example.mvvmlivedata

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer

class MainActivity : AppCompatActivity() {

    private val myLiveData = MyLiveData()

    private lateinit var observer: Observer<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val myTextView = findViewById<TextView>(R.id.myText)

        observer = Observer { newValue ->
            myTextView.text = newValue
        }

        val myEditText = findViewById<EditText>(R.id.myEditText)

        myEditText.addTextChangedListener( object: TextWatcher {
           override fun onTextChanged(newSequence: CharSequence?, p1: Int, p2: Int, p3: Int) {
               myLiveData.updateValue(newSequence.toString())
           }

           override fun afterTextChanged(p0: Editable?) {}
           override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
        })

        myEditText.setText("Hello world!")

        findViewById<Button>(R.id.switchActivityButton).setOnClickListener {
            val intent = Intent(this, TransformationMapActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onStart() {
        super.onStart()
        myLiveData.observe(this, observer)
    }

    override fun onStop() {
        super.onStop()
        myLiveData.removeObserver(observer)
    }
}