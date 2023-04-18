package com.example.mvvmlivedata

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.animation.Transformation
import android.widget.Button
import android.widget.EditText
import android.widget.Switch
import android.widget.TextView
import androidx.appcompat.widget.SwitchCompat
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.Transformations
import com.google.android.material.snackbar.Snackbar

class TransformationMapActivity : AppCompatActivity() {

    private var liveDataInt = MutableLiveData<Int>()
    private val transformedLiveDataDisabledValue = "Transformations disabled"
    private var transformedLiveData = MutableLiveData(transformedLiveDataDisabledValue)

    private lateinit var observerLiveDataInt: Observer<Int>
    private lateinit var observerTransformedLiveData: Observer<String>

    private fun addSaveToIntLiveDataButtonListener() {
        val myEditText = findViewById<EditText>(R.id.my_edit_text)

        myEditText.addTextChangedListener( object: TextWatcher {
            override fun onTextChanged(newSequence: CharSequence?, p1: Int, p2: Int, p3: Int) {
                newSequence?.let {
                    if(newSequence.isEmpty()) {
                        liveDataInt.value = 0
                        return
                    }

                    val parsedString: Int? = Converter.convert(newSequence.toString())
                    liveDataInt.value = parsedString
                }
            }

            override fun afterTextChanged(p0: Editable?) {}
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
        })

        myEditText.setText("0")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transformation_map)

        val textViewForIntData = findViewById<TextView>(R.id.text_view_for_int_data)
        val textViewForTransformedData = findViewById<TextView>(R.id.text_view_for_string_data)

        observerLiveDataInt = Observer { newValue ->
            textViewForIntData.text = newValue.toString()
        }
        observerTransformedLiveData = Observer { newValue ->
            textViewForTransformedData.text = newValue
        }

        liveDataInt.value = 100

        val enableTransformationsSwitch = findViewById<SwitchCompat>(R.id.enable_transformations_switch)
        enableTransformationsSwitch.setOnCheckedChangeListener { _, isEnabled ->

            transformedLiveData.removeObserver(observerTransformedLiveData)
            transformedLiveData = if(isEnabled) {
                                        Transformations.map(liveDataInt) {
                                            "Transformations enabled, current value = $it"
                                        } as MutableLiveData<String>
                                  } else { MutableLiveData(transformedLiveDataDisabledValue) }
            transformedLiveData.observe(this, observerTransformedLiveData)
        }

        addSaveToIntLiveDataButtonListener()
    }

    override fun onStart() {
        super.onStart()
        liveDataInt.observe(this, observerLiveDataInt)
        transformedLiveData.observe(this, observerTransformedLiveData)
    }

    override fun onStop() {
        super.onStop()
        liveDataInt.removeObserver(observerLiveDataInt)
        transformedLiveData.removeObserver(observerTransformedLiveData)
    }
}