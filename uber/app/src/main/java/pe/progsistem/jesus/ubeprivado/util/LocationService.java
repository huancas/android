package pe.progsistem.jesus.ubeprivado.util;

import android.app.IntentService;
import android.app.NotificationManager;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;

import pe.progsistem.jesus.ubeprivado.R;

public class LocationService extends IntentService {

    private static final String INTENT_SERVICE_NAME = LocationService.class.getName();

    public LocationService() {
        super(INTENT_SERVICE_NAME);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (null == intent) {
            return;
        }

        Bundle bundle = intent.getExtras();

        if (null == bundle) {
            return;
        }

        Location location = bundle.getParcelable("com.google.android.location.LOCATION");

        if (null == location) {
            return;
        }

        if (null != location) {
            // TODO: Handle the incoming location

            //Log.i(INTENT_SERVICE_NAME, "onHandleIntent " + location.getLatitude() + ", " + location.getLongitude());

            // Just show a notification with the location's coordinates
            NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            NotificationCompat.Builder notification = new NotificationCompat.Builder(this);
            notification.setContentTitle("Location");
            notification.setContentText(location.getLatitude() + ", " + location.getLongitude());
            //
            notification.setSmallIcon(R.drawable.icono_auto);
            notificationManager.notify(1234, notification.build());
        }
    }
}