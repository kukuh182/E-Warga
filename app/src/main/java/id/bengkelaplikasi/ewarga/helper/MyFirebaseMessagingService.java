package id.bengkelaplikasi.ewarga.helper;

/**
 * Created by wira on 8/28/16.
 */

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import id.bengkelaplikasi.ewarga.R;
import id.bengkelaplikasi.ewarga.views.menus.home.Communicator;
import id.bengkelaplikasi.ewarga.views.menus.home.HomeActivity;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "panic_button";
    private Communicator communicator;

    /**
     * Called when message is received.
     *
     * @param remoteMessage Object representing the message received from Firebase Cloud Messaging.
     */
    // [START receive_message]
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        // TODO(developer): Handle FCM messages here.
        // If the application is in the foreground handle both data and notification messages here.
        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated. See sendNotification method below.
        //Log.d(TAG, "From: " + remoteMessage.getFrom());
        //Log.d(TAG, "Notification Message Body: " + remoteMessage.getNotification().getBody());
        //Log.d(TAG, "Notification Message Title: " + remoteMessage.getTtl());
        //Log.d(TAG, "Notification Message Latitude: " + remoteMessage.getData().get("lat"));
        //Log.d(TAG, "Notification Message Laongitude: " + remoteMessage.getData().get("lng"));
        sendNotification(remoteMessage.getNotification().getBody(),remoteMessage.getData().get("lat"), remoteMessage.getData().get("lng"));
    }
    // [END receive_message]

    /**
     * Create and show a simple notification containing the received FCM message.
     *
     * @param messageBody FCM message body received.
     */
    private void sendNotification(String messageBody, String lat, String lng) {
        Intent intent = new Intent(this, HomeActivity.class);
        intent.putExtra("menuFragment", "panicMenuItem");
        intent.putExtra("content", messageBody);
        intent.putExtra("latitude", lat);
        intent.putExtra("longitude", lng);

        /*
        Fragment navFragment = null;
        navFragment = new LokasiFragment();

        navFragment.setArguments(bundle);
        */

        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent,
                PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.logo)
                .setContentTitle("E-Warga")
                .setContentText(messageBody)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setColor(Color.RED)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0, notificationBuilder.build());

        /*
        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        String id = "my_channel_01";
        CharSequence name = getString(R.string.info_panic);

        int importance = NotificationManager.IMPORTANCE_HIGH;
        NotificationChannel mChannel = new NotificationChannel(id, name, importance);
        // Configure the notification channel.
        mChannel.setDescription(messageBody);
        mChannel.enableLights(true);
        // Sets the notification light color for notifications posted to this
        // channel, if the device supports this feature.
        mChannel.setLightColor(Color.RED);
        mChannel.enableVibration(true);
        mChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
        notificationManager.createNotificationChannel(mChannel);
        */
    }
}
