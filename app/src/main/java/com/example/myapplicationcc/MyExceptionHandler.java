package com.example.myapplicationcc;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import androidx.work.BackoffPolicy;
import androidx.work.OneTimeWorkRequest;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;
import androidx.work.WorkRequest;

import java.util.concurrent.TimeUnit;

public class MyExceptionHandler implements Thread.UncaughtExceptionHandler {
    private Activity activity;
    public MyExceptionHandler(Activity a) {
        activity = a;
    }
    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        Log.d("UNUNUN", "uncaughtException");
        Log.d("UNUNUN", "Thread : " + thread);
        Log.d("UNUNUN", "Throwable : " + ex);
//        WorkRequest worker = new OneTimeWorkRequest.Builder(Worker.class).setBackoffCriteria(BackoffPolicy.LINEAR, 3000, TimeUnit.MILLISECONDS)
//                .build();
//        WorkManager.getInstance(activity).enqueue(worker);
        Intent intent = new Intent(activity, MainActivity.class);
        intent.putExtra("crash", true);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                | Intent.FLAG_ACTIVITY_CLEAR_TASK
                | Intent.FLAG_ACTIVITY_NEW_TASK);

        PendingIntent pendingIntent;
        // Android API 31 이상일 경우
        if (Build.VERSION_CODES.S <= Build.VERSION.SDK_INT) {
            pendingIntent = PendingIntent.getActivity(MyApplication.getInstance().getBaseContext(), 0, intent, PendingIntent.FLAG_IMMUTABLE | PendingIntent.FLAG_ONE_SHOT);
        } else {
            pendingIntent = PendingIntent.getActivity(MyApplication.getInstance().getBaseContext(), 0, intent, PendingIntent.FLAG_ONE_SHOT);
        }


        AlarmManager mgr = (AlarmManager) MyApplication.getInstance().getBaseContext().getSystemService(Context.ALARM_SERVICE);
        mgr.set(AlarmManager.RTC, System.currentTimeMillis() + 100, pendingIntent);

        activity.finish();
        System.exit(2);

    }


}
