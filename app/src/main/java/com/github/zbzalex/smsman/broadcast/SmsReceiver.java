package com.github.zbzalex.smsman.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;

public class SmsReceiver extends BroadcastReceiver
{
    @Override
    public void onReceive(Context context, Intent intent)
    {
        Bundle bundle = intent.getExtras();

        if (bundle != null && bundle.containsKey("pdus")) {
            Object[] pdus = (Object[]) bundle.get("pdus");
            if (pdus.length != 0) {

                SmsMessage message = SmsMessage.createFromPdu((byte[]) pdus[0]);

                String from = message.getOriginatingAddress();
                String body = message.getMessageBody();

                Log.d("TEST", "from ===> " + from);
                Log.d("TEST", "body ===> " + body);

            }
        }
    }
}
