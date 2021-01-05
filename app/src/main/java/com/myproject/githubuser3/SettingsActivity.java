package com.myproject.githubuser3;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Switch;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.myproject.githubuser3.receiver.Receiver;
import java.util.Calendar;

public class SettingsActivity extends AppCompatActivity {

    private SharedPreferences sharedPreferences;

    @SuppressLint("UseSwitchCompatOrMaterialCode")
    private Switch aSwitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        int notificationDaily = sharedPreferences.getInt("user_notification", 0);
        aSwitch = findViewById(R.id.switchNotification);

        if (notificationDaily == 1){

            aSwitch.setChecked(true);

        }else {

            aSwitch.setChecked(false);

        }

        onClickSwitch();

    }

    public void onClickSwitch(){

        aSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {

            if (isChecked){
                setReminder(getApplicationContext());
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putInt("user_notification",1);
                editor.apply();
                Toast.makeText(SettingsActivity.this, "Notification Active", Toast.LENGTH_SHORT).show();

            }else {

                setReminderOff(getApplicationContext());
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putInt("user_notification",0);
                editor.apply();
                Toast.makeText(SettingsActivity.this, "Notification Not Active", Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void setReminder(Context applicationContext) {

        Intent intent = new Intent(applicationContext, Receiver.class);
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY,9);
        calendar.set(Calendar.MINUTE,0);
        calendar.set(Calendar.SECOND,0);

        AlarmManager alarmManager = (AlarmManager) applicationContext.getSystemService(Context.ALARM_SERVICE);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(applicationContext, 102, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        Log.d("aSwitch","aSwitch");
        if (alarmManager != null){

            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),AlarmManager.INTERVAL_DAY,pendingIntent);

        }
    }

    private void setReminderOff(Context applicationContext) {

        Log.d("aSwitch","aSwitch");
        Intent intent = new Intent(SettingsActivity.this, Receiver.class);

        AlarmManager alarmManager = (AlarmManager) applicationContext.getSystemService(Context.ALARM_SERVICE);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(applicationContext, 102, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        if (alarmManager != null){
            alarmManager.cancel(pendingIntent);

        }
    }
}
