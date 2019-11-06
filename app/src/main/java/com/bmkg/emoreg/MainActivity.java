package com.bmkg.emoreg;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

import com.bmkg.emoreg.home.FragmentHome;
import com.bmkg.emoreg.lainlain.Config;
import com.bmkg.emoreg.profile.FragmentProfile;
import com.bmkg.emoreg.notif.Bukanotif;
import com.bmkg.emoreg.notif.FragmentNotif;
import com.bmkg.emoreg.usulan.FragmentDaftarUsulan;
import com.onesignal.OSPermissionSubscriptionState;
import com.onesignal.OneSignal;

public class MainActivity extends AppCompatActivity {

    private TextView mTextMessage;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.nav_home:
                    getFragmentManager().beginTransaction().replace(R.id.container,new FragmentHome()).commit();
                    return true;
                case R.id.nav_usulan:
                    getFragmentManager().beginTransaction().replace(R.id.container,new FragmentDaftarUsulan()).commit();
                    return true;
                case R.id.navig_notifikasi:
                    getFragmentManager().beginTransaction().replace(R.id.container,new FragmentNotif()).commit();
                    return true;
                case R.id.nav_profile:
                    getFragmentManager().beginTransaction().replace(R.id.container,new FragmentProfile()).commit();
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        OneSignal.startInit(this)
                .setNotificationOpenedHandler(new Bukanotif(this))
                .inFocusDisplaying(OneSignal.OSInFocusDisplayOption.Notification)
                .unsubscribeWhenNotificationsAreDisabled(true)
                .init();
        Bundle extra=getIntent().getExtras();
        String key=extra.getString("key");
        OSPermissionSubscriptionState ostatus= OneSignal.getPermissionSubscriptionState();
        String osid=ostatus.getSubscriptionStatus().getUserId();
        String ostoken=ostatus.getSubscriptionStatus().getPushToken();
        Config.playerid=osid;
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        if(key.equals("notif")){
            getFragmentManager().beginTransaction().replace(R.id.container,new FragmentNotif()).commit();
            navigation.setSelectedItemId(R.id.navig_notifikasi);
        }else{
            getFragmentManager().beginTransaction().replace(R.id.container,new FragmentHome()).commit();
        }

    }

}
