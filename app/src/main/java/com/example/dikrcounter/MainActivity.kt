package com.example.dikrcounter

import android.os.Build
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val sharedPreference = getSharedPreferences("dikrCounter", MODE_PRIVATE)
        val editor = sharedPreference.edit()
        val imageView = findViewById<ImageView>(R.id.counter_img)
        val reset = findViewById<ImageView>(R.id.reset_background)
        val counterView = findViewById<TextView>(R.id.counter)
        val savedCounter = sharedPreference.getInt("counter",0)
        var counter = savedCounter
        counterView.text = counter.toString()

        imageView.setOnClickListener(){
            vibrate()
            counter++
            counterView.text = counter.toString()
            editor.putInt("counter",counter)
            editor.apply()
        }

        reset.setOnClickListener(){
            counter = 0
            editor.putInt("counter",0)
            editor.apply()
            counterView.text = 0.toString()
        }




    }

    private fun vibrate(){
        val vibrator = if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.S){
            val vibratorManager = getSystemService(VIBRATOR_MANAGER_SERVICE) as VibratorManager
            vibratorManager.defaultVibrator
        }else{
            @Suppress("DEPRECATION")
            getSystemService(VIBRATOR_SERVICE) as Vibrator

        }
        vibrator.vibrate(100)

    }


}