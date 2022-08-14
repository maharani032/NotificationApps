package com.mahar.notificationapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationManagerCompat;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Icon;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class MainActivity extends AppCompatActivity {
    private Button button;
    public final String CHANNEL_ID="1";
    int counter=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button=findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                counter++;
                button.setText(""+counter);
                if(counter==5){
                    startNotification();
                }
            }
        });

    }
    public void startNotification(){
        Intent i=new Intent(this,MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,0,i,0);

        //        action toast message
        Intent actionIntent=new Intent(this,receivers.class);
        actionIntent.putExtra("toast","this is notification Message");
        PendingIntent actionPending=PendingIntent.getBroadcast(this,0,actionIntent,0);

        Notification.Action action=new Notification.Action.Builder(Icon.createWithResource(this,R.drawable.ic_notif)
                ,"Toast Message",actionPending).build();
//          action dismiss

        Intent dismissIntent=new Intent(this,receiversDismiss.class);
        PendingIntent dismissPending=PendingIntent.getBroadcast(this,0,dismissIntent,0);

        Notification.Action dismiss=new Notification.Action.Builder(Icon.createWithResource(this,R.drawable.ic_notif)
                ,"Dismiss Message",dismissPending).build();


        NotificationChannel channel= new NotificationChannel(CHANNEL_ID,"1"
                , NotificationManager.IMPORTANCE_DEFAULT);

        NotificationManager manager=(NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        manager.createNotificationChannel(channel);

        Bitmap icon= BitmapFactory.decodeResource(getResources()
                ,R.drawable.girl);
        String text=getResources().getString(R.string.big_text);

        Notification.Builder builder= new Notification.Builder(MainActivity.this,CHANNEL_ID);
        builder.setSmallIcon(R.drawable.ic_notif)
                .setContentTitle("Judul Notification")
                .setContentText("Text Notification")
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .addAction(action)
                .addAction(dismiss)
                .setColor(Color.BLUE)
                .setLargeIcon(icon);
//                .setStyle(new Notification.BigTextStyle().bigText(text));
//                .setStyle(new Notification.BigPictureStyle().bigPicture(icon));
//                .setActions(action);
//                .setPriority(Notification.PRIORITY_DEFAULT);

        NotificationManagerCompat compat=NotificationManagerCompat.from(MainActivity.this);
        compat.notify(1,builder.build());
    }
}