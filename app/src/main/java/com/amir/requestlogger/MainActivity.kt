package com.amir.requestlogger

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.amir.chuck.RequestLogger

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        RequestLogger(this)
    }
}