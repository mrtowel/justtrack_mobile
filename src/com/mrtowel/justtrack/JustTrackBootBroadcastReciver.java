package com.mrtowel.justtrack;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class JustTrackBootBroadcastReciver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		Intent service = new Intent(context, JustTrackLocationListenerService.class);
		context.startService(service);
	}

}
