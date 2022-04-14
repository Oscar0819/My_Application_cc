package com.example.myapplicationcc;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private String permission = Manifest.permission.SYSTEM_ALERT_WINDOW;

    static final int REQUEST_CODE = 101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        checkPermission();

//        Intent notificationIntent = new Intent(this, ForegroundService.class);
//
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//
//            startForegroundService(notificationIntent);
//
//        } else {
//
//            startService(notificationIntent);
//
//        }

        Thread.setDefaultUncaughtExceptionHandler(new MyExceptionHandler(this));
        if (getIntent().getBooleanExtra("crash", false)) {
            Toast.makeText(this, "App restarted after crash", Toast.LENGTH_SHORT).show();
        }

        Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick");
                crashMe();
            }
        });
    }
    public void crashMe() {
        throw new NullPointerException();
    }

    public void checkPermission() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            if (!Settings.canDrawOverlays(getApplicationContext())) {

                checkOverlayPermission();

            }

        }

    }

    private void checkOverlayPermission() {

        try {

            Uri uri = Uri.parse("package:" + getPackageName());

            Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, uri);

            startActivityForResult(intent, 5469);

        } catch (Exception e) {

            //toast(e.toString());

        }

    }



    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        Log.d("mmmmm", "grantResults.length : " + grantResults.length);
//        Log.d("mmmmm", "grantResults[0] : " + grantResults[0]);
//        Log.d("mmmmm", "grantResults[0] : " + grantResults[0]);
        switch (requestCode) {
            case REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "앱 실행을 위한 권한이 설정 되었습니다.", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(this, "앱 실행을 위한 권한이 취소 되었습니다", Toast.LENGTH_LONG).show();
                }

                break;
        }
    }
}