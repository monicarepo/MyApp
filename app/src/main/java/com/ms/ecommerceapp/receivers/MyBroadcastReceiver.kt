package com.ms.ecommerceapp.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import com.google.android.gms.auth.api.phone.SmsRetriever
import com.google.android.gms.common.api.CommonStatusCodes
import com.google.android.gms.common.api.Status

class MyBroadcastReceiver : BroadcastReceiver() {
    var smsListener: SmsListener? = null

    override fun onReceive(context: Context?, intent: Intent?) {
        Toast.makeText(context,"Broadcast Receiver Triggered",Toast.LENGTH_SHORT).show();
        if (intent?.action.equals(SmsRetriever.SMS_RETRIEVED_ACTION)) {
            Log.d("CHECK_SMS", "Broadcast Received")
            val extras = intent?.extras
            val status = extras?.get(SmsRetriever.EXTRA_STATUS) as Status
            when (status.statusCode) {
                CommonStatusCodes.SUCCESS -> {
                    val messageIntent = extras.getParcelable<Intent>(SmsRetriever.EXTRA_CONSENT_INTENT)
                    if (messageIntent != null) {
                        smsListener?.onSuccess(messageIntent)
                    }
                }
                CommonStatusCodes.TIMEOUT -> {
                    smsListener?.onFailure()
                }
            }
        }
        if (intent?.action == Intent.ACTION_AIRPLANE_MODE_CHANGED) {
            val isAirplaneModeEnabled = Settings.Global.getInt(
                context?.contentResolver,
                Settings.Global.AIRPLANE_MODE_ON,
                0
            ) != 0
            Log.d("CHECK_AIRPLANE", "Airplane Mode Enabled ? $isAirplaneModeEnabled")
            Toast.makeText(context, "Airplane Mode Enabled", Toast.LENGTH_SHORT).show()
        }
    }

    interface SmsListener {
        fun onSuccess(intent: Intent)
        fun onFailure()
    }

}