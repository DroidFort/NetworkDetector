package com.droidfort.netdetector

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build

object Net {


    fun isNetworkAvailable(mContext: Context?): Boolean {
        val connectivityManager =
            mContext?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (connectivityManager != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                val network = connectivityManager.activeNetwork
                if (network != null) {
                    val nc = connectivityManager.getNetworkCapabilities(network)

                    return (nc!!.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) || nc.hasTransport(
                        NetworkCapabilities.TRANSPORT_WIFI)


                            )
                }
            } else {
                val networkInfos = connectivityManager.allNetworkInfo
                for (tempNetworkInfo in networkInfos) {
                    if (tempNetworkInfo.isConnected) {
                        return true
                    }
                }
            }
        }
        return false
    }
}