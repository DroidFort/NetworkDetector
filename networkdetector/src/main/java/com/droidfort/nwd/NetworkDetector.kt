package com.droidfort.netdetector

import android.content.Context
import android.content.IntentFilter
import java.lang.ref.WeakReference

class NetworkDetector private constructor(context: Context?) : NetworkChangeReceiver.InternetChangeListener {

    private val CONNECTIVITY_CHANGE_INTENT_ACTION = "android.net.conn.CONNECTIVITY_CHANGE"

    private var isNetworkAvailable:Boolean = false
    private var networkChangeReceiver:NetworkChangeReceiver? = null
    var isNetworkChangeListenerRegisterd:Boolean = false
    var context: WeakReference<Context?> = WeakReference(context?.applicationContext)
    var mNetworkChangeListenerList:MutableList<WeakReference<NetworkChangeListener?>> = ArrayList()

    override fun onNetworkChange(isNetworkAvailable: Boolean) {
        this.isNetworkAvailable = isNetworkAvailable
        updateCurrentNetworkStatus()
    }

    companion object{
        @Volatile
       private var networkDetector:NetworkDetector? = null


        fun init(context: Context?): NetworkDetector? {

           synchronized(NetworkDetector::class.java){

               if (networkDetector ==null){
                   networkDetector = NetworkDetector(context)
               }

           }
            return networkDetector
        }

        fun getInstance(): NetworkDetector {

            if (networkDetector==null){
                throw  Exception("context can not be null")
            }

            return  networkDetector!!
        }


    }

    fun isNetworkAvailable():Boolean {
        isNetworkAvailable = Net.isNetworkAvailable(context.get())
        return isNetworkAvailable
    }


    fun addNetworkChangeListener(networkChangeListener: NetworkChangeListener?){


        val weakReference: WeakReference<NetworkChangeListener?> = WeakReference(networkChangeListener)
        mNetworkChangeListenerList.add(weakReference)

        if (mNetworkChangeListenerList.size == 1){
            registerNetworkChangeReceiver()
        }
        //updateCurrentNetworkStatus()
    }

    private fun updateCurrentNetworkStatus() {

        val iterator: MutableIterator<WeakReference<NetworkChangeListener?>> = mNetworkChangeListenerList.iterator()

        while (iterator.hasNext()){
            val ref : WeakReference<NetworkChangeListener?> =iterator.next()
            val mInternetChangeListener: NetworkChangeListener? = ref.get()
            mInternetChangeListener?.onNetworkChangeListener(isNetworkAvailable)
        }

        if (mNetworkChangeListenerList.size == 0){
            unregisterNetworkChangeReceiver()
        }

    }

    private fun registerNetworkChangeReceiver() {

        val context:Context?=this.context.get()
        if (context!=null || !isNetworkChangeListenerRegisterd){

            networkChangeReceiver = NetworkChangeReceiver()
            networkChangeReceiver?.setInternetChangeListener(this)
            context?.registerReceiver(networkChangeReceiver, IntentFilter(CONNECTIVITY_CHANGE_INTENT_ACTION))
            isNetworkChangeListenerRegisterd =true

        }

    }

    private fun unregisterNetworkChangeReceiver() {


        val context:Context?=this.context.get()
        if (context!=null && isNetworkChangeListenerRegisterd && networkChangeReceiver!=null){

            context.unregisterReceiver(networkChangeReceiver)
            isNetworkChangeListenerRegisterd =true
            networkChangeReceiver?.removeInternetChangeListener()
        }

        networkChangeReceiver=null
        isNetworkChangeListenerRegisterd = false


    }


}