package com.krom.moneymanager.Fragments;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.fragment.app.Fragment;

import com.krom.moneymanager.R;
import com.krom.moneymanager.app.AlarmReceiver;

import java.util.Calendar;

public class SettingsFragment extends Fragment {

    private View view;
    private EditText timePickerEditText;
    final Calendar currentTime = Calendar.getInstance();

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
       view = inflater.inflate(R.layout.fragment_settings, container, false);

       getActivity().findViewById(R.id.addButton).setVisibility(View.INVISIBLE);
       timePickerEditText = view.findViewById(R.id.timePicker);
       addListener();

       sendNotification();

       return view;
    }


    private void addListener(){
        timePickerEditText.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                int hour = currentTime.get(Calendar.HOUR_OF_DAY);
                int minute = currentTime.get(Calendar.MINUTE);

                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        timePicker.setIs24HourView(true);
                        timePickerEditText.setText( "Fire notification at " + selectedHour + ":" + selectedMinute);
                        currentTime.set(Calendar.HOUR_OF_DAY, selectedHour);
                        currentTime.set(Calendar.MINUTE, selectedMinute);
                        currentTime.set(Calendar.SECOND, 0);
                        setAlarm();
                    }
                }, hour, minute, true);
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
            }
        });

    }

    public void sendNotification(){
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getContext())
                .setSmallIcon(R.drawable.ic_notifications_black_24dp)
                .setContentTitle("MoneyManager")
                .setContentText("Add Today Expenses")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);



        NotificationManager notificationManager = (NotificationManager) getContext().getSystemService(Context.NOTIFICATION_SERVICE);

        //notificationManager.notify();
        notificationManager.notify(001, builder.build());



    }

    private void setAlarm(){
        AlarmManager alarmManager = (AlarmManager) getContext().getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(getActivity(), AlarmReceiver.class);
        PendingIntent alarmIntent = PendingIntent.getBroadcast(getContext(), 192837, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        alarmManager.setInexactRepeating(AlarmManager.RTC, currentTime.getTimeInMillis(),AlarmManager.INTERVAL_DAY, alarmIntent);
        Toast.makeText(getContext(), "Sset complete", Toast.LENGTH_SHORT).show();

    }


}
