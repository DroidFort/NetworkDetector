package com.droidfort.netdetector

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import java.lang.ref.WeakReference


class NetworkChangeReceiver : BroadcastReceiver() {

    lateinit var mInternetChangeListenerWeekReference : WeakReference<InternetChangeListener?>
    override fun onReceive(context: Context?, intent: Intent?) {
        mInternetChangeListenerWeekReference.get()?.onNetworkChange(isNetworkAvailable(context))
    }


    fun isNetworkAvailable(mContext: Context?): Boolean {
        return (Net.isNetworkAvailable(mContext))
    }

    fun setInternetChangeListener(internetChangeListener: InternetChangeListener?){
        mInternetChangeListenerWeekReference = WeakReference(internetChangeListener)
    }

    fun removeInternetChangeListener(){
        if (mInternetChangeListenerWeekReference !=null) {
            mInternetChangeListenerWeekReference.clear()
        }
    }

    interface InternetChangeListener{
        fun onNetworkChange(isNetworkAvailable: Boolean)
    }
}