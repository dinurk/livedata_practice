package com.example.mvvmlivedata

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.animation.Transformation
import android.widget.Button
import android.widget.EditText
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import android.widget.Switch
import android.widget.TextView
import androidx.appcompat.widget.SwitchCompat
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.Transformations
import com.google.android.material.snackbar.Snackbar

class TransformationMapActivity : AppCompatActivity() {

    private var liveDataInt = MutableLiveData<Int>()
    private lateinit var observerLiveDataInt: Observer<Int>
    private lateinit var observerTransformedLiveData: Observer<String>
    private var transformedLiveData: MutableLiveData<String>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transformation_map)

        liveDataInt.value = 0

        val seekBar = findViewById<SeekBar>(R.id.my_seek_bar)
        seekBar.setOnSeekBarChangeListener( object: OnSeekBarChangeListener {
            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                liveDataInt.value = p1
            }
            override fun onStartTrackingTouch(p0: SeekBar?) {println("1")}
            override fun onStopTrackingTouch(p0: SeekBar?) {println("2")}
        })

        val textViewForIntData = findViewById<TextView>(R.id.text_view_for_int_data)
        observerLiveDataInt = Observer { newValue -> textViewForIntData.text = newValue.toString() }

        val textViewForTransformedData = findViewById<TextView>(R.id.text_view_for_string_data)
        observerTransformedLiveData = Observer { newValue -> textViewForTransformedData.text = newValue }

        val enableTransformationsSwitch = findViewById<SwitchCompat>(R.id.enable_transformations_switch)
        enableTransformationsSwitch.setOnCheckedChangeListener(){ _, isEnabled -> this.onChangeTransformationsSwitchState(isEnabled)}

        findViewById<Button>(R.id.switchActivityButton).setOnClickListener { goToMediatorLiveDataActivity() }
    }

    private fun goToMediatorLiveDataActivity() {
        val intent = Intent(this, MediatorLiveDataActivity::class.java)
        startActivity(intent)
    }

    private fun onChangeTransformationsSwitchState(isEnabled: Boolean) {
        transformedLiveData?.removeObserver(observerTransformedLiveData)

        if(!isEnabled) {
            transformedLiveData = null
            val textViewForTransformedLiveData = findViewById<TextView>(R.id.text_view_for_string_data)
            textViewForTransformedLiveData.text = "Transformations Disabled"
            return
        }

        transformedLiveData = Transformations.map(liveDataInt) {
            "Transformations enabled, current value = $it"
        } as MutableLiveData<String>

        transformedLiveData?.observe(this, observerTransformedLiveData)
    }

    override fun onStart() {
        super.onStart()
        liveDataInt.observe(this, observerLiveDataInt)
    }

    override fun onStop() {
        super.onStop()
        liveDataInt.removeObserver(observerLiveDataInt)
    }
}