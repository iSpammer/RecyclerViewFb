package ispam.markt17.recyclerviewfbxd;

import android.content.Intent;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;

public class NotificationService extends FirebaseMessagingService {
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {


        final Map<String, String> data = remoteMessage.getData();
        final String val1 = data.get("myKey1");
        final String val2 = data.get("myKey2");

        Log.d("FCM","Message received val1=" + val1 + " val2=" + val2);


        Handler handler = new Handler(getMainLooper());
        handler.post(new Runnable() {

            @Override
            public void run() {
                Toast.makeText(NotificationService.this.getApplicationContext(),
                        "Message received " + "val1=" + val1 + " val2=" + val2, Toast.LENGTH_SHORT).show();
            }
        });


    }

    @Override
    public void onNewToken(String registrationToken) {


        Log.d("FCM","Firebase #onNewToken registrationToken=" + registrationToken);

        startService(new Intent(this, FcmTokenRegistrationService.class));
    }
}
