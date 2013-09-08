package com.mrtowel.justtrack;

import android.app.Service;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.widget.Toast;

public class JustTrackLocationListenerService extends Service implements
		LocationListener {
	private final static String URL = "http://www.wp.pl/";
	private final static int INTERVAL = 1000 * 30;
	private LocationManager manager;

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		runLocationManager();
		return Service.START_STICKY;
	}

	private void runLocationManager() {
		manager = (LocationManager) getSystemService(LOCATION_SERVICE);
		Criteria criteria = new Criteria();
		String provider = manager.getBestProvider(criteria, false);

		boolean enabled = manager.isProviderEnabled(provider);
		if (!enabled) {
			Toast.makeText(this, "w????cz " + provider, Toast.LENGTH_LONG).show();
		}
		manager.requestLocationUpdates(provider, INTERVAL, 0, this);
	}

	private String buildLocationString(Location location) {
		String lat = String.valueOf(location.getLatitude());
		String lng = String.valueOf(location.getLongitude());
		return "Location: " + lat + ", " + lng;
	}

	@Override
	public void onLocationChanged(Location location) {
		Toast.makeText(this, buildLocationString(location), Toast.LENGTH_LONG)
				.show();
		JustTrackAsyncHttpGet getter = new JustTrackAsyncHttpGet();
		getter.execute(URL);
	}

	@Override
	public void onProviderDisabled(String provider) {
		Toast.makeText(this, provider + " disabled", Toast.LENGTH_LONG).show();
	}

	@Override
	public void onProviderEnabled(String provider) {
		Toast.makeText(this, provider + " enabled", Toast.LENGTH_LONG).show();
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		Toast.makeText(this, "status " + status + " for " + provider,
				Toast.LENGTH_LONG).show();
	}

	@Override
	public IBinder onBind(Intent arg0) {
		Toast.makeText(this, "binded service ", Toast.LENGTH_LONG).show();
		return null;
	}

}
