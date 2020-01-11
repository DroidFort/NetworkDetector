package com.droidfort.nwdsample

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.droidfort.netdetector.NetworkChangeListener
import com.droidfort.netdetector.NetworkDetector

class MainActivity : AppCompatActivity(), NetworkChangeListener {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        NetworkDetector.init(this)
        NetworkDetector.getInstance().addNetworkChangeListener(this)
    }
    override fun onNetworkChangeListener(isNetworkAvailable: Boolean) {
        var constraintlayout:ConstraintLayout = findViewById(R.id.constraintlayout)
        NetworkDetector.getInstance().showSnackBar(constraintlayout,isNetworkAvailable)
    }
}
