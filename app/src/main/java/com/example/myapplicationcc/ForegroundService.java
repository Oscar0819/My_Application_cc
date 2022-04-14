package com.example.myapplicationcc;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import androidx.core.app.NotificationCompat;

public class ForegroundService extends Service {

    private static final String TAG = "ForegroundService";

//    CheckPackageNameThread checkPackageNameThread;
    NotificationCompat.Builder notification;
    NotificationManager mNotificationManager;

    public ForegroundService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        // PendingIntent를 이용하면 포그라운드 서비스 상태에서 알림을 누르면 앱의 MainActivity를 다시 열게 된다.
        Intent testIntent = new Intent(getApplicationContext(), MainActivity.class);
        PendingIntent pendingIntent
                = PendingIntent.getActivity(this, 0, testIntent, PendingIntent.FLAG_IMMUTABLE);

        // 오래오 윗버젼일 때는 아래와 같이 채널을 만들어 Notification과 연결해야 한다.
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("channel", "play!!",
                    NotificationManager.IMPORTANCE_DEFAULT);

            // Notification과 채널 연걸
            mNotificationManager = ((NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE));
            mNotificationManager.createNotificationChannel(channel);

            // Notification 세팅
            notification
                    = new NotificationCompat.Builder(getApplicationContext(), "channel")
                    .setSmallIcon(R.drawable.ic_launcher_foreground)
                    .setContentTitle("현재 실행 중인 앱 이름")
                    .setContentIntent(pendingIntent)
                    .setContentText("");

            // id 값은 0보다 큰 양수가 들어가야 한다.
            mNotificationManager.notify(1, notification.build());
            // foreground에서 시작
            startForeground(1, notification.build());
        }

        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy");
    }
}