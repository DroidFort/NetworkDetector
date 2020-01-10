package com.droidfort.nwdsample

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.droidfort.netdetector.NetworkChangeListener

class MainActivity : AppCompatActivity(), NetworkChangeListener {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
    override fun onNetworkChangeListener(isNetworkAvailable: Boolean) {

    }
}
