package pe.progsistem.jesus.ubeprivado;

import android.content.Intent;
import android.os.IBinder;

import com.google.firebase.messaging.FirebaseMessagingService;

public class FCMService extends FirebaseMessagingService {
    public FCMService() {
    }

    @Override
    public void onNewToken(String token) {

        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // Instance ID token to your app server.
        sendRegistrationToServer(token);
    }

    private void sendRegistrationToServer(String token) {
        System.out.println("...............--------------........------------------------------------------------");
        System.out.println(token);
        System.out.println("...............--------------........---------------------------------------------------------------");
    }
}
