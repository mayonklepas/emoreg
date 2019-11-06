package com.bmkg.emoreg.notif;

import android.content.Context;
import android.content.Intent;

import com.bmkg.emoreg.lainlain.Config;
import com.bmkg.emoreg.MainActivity;
import com.onesignal.OSNotificationAction;
import com.onesignal.OSNotificationOpenResult;
import com.onesignal.OneSignal;

import org.json.JSONObject;

/**
 * Created by Minami on 7/24/2018.
 */

public class Bukanotif implements OneSignal.NotificationOpenedHandler {

    private Context ct;

    public Bukanotif(Context ct){
        this.ct=ct;
    }

    @Override
    public void notificationOpened(OSNotificationOpenResult result) {
        OSNotificationAction.ActionType actionType = result.action.type;
        JSONObject data = result.notification.payload.additionalData;
        System.out.println(data);
        if (data != null) {
            Config.status=1;
            Intent in = new Intent(ct.getApplicationContext(), MainActivity.class);
            in.putExtra("key","notif");
            ct.startActivity(in);
        }
    }
}
