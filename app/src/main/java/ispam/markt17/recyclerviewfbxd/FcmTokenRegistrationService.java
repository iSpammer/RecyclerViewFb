package ispam.markt17.recyclerviewfbxd;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

/**
 * Service for sending this device's registrationToken to your server to remember it.
 *
 * @author Tom Misawa (riversun.org@gmail.com)
 */
public class FcmTokenRegistrationService extends IntentService {

    public FcmTokenRegistrationService() {
        super("FcmTokenRegistrationService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(Task<InstanceIdResult> task) {
                        if (!task.isSuccessful()) {

                            Log.d("FCM", "Firebase getInstanceId failed " + task.getException());
                            return;
                        }

                        // Get new Instance ID token
                        String token = task.getResult().getToken();

                        Log.d("FCM","Firebase registrationToken=" + token);

                        //TODO register token to your server.

                    }
                });
    }


}