package cn.csc.mytools.service;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;

public class LocationService extends Service {
	private LocationManager lm;
	private MyLocationListener mll;
	private SharedPreferences sp;
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void onCreate() {
		super.onCreate();
		sp = getSharedPreferences("config", MODE_PRIVATE);
		lm = (LocationManager) getSystemService(LOCATION_SERVICE);
		mll = new MyLocationListener();
		Criteria criteria = new Criteria();
		criteria.setAccuracy(criteria.ACCURACY_FINE);
		String provider = lm.getBestProvider(criteria , true);
		
		lm.requestLocationUpdates(provider, 0, 0, mll);
		
	}
	class MyLocationListener implements LocationListener{

		@Override
		public void onLocationChanged(Location location) {
			double longitude = location.getLongitude();
			double latitude = location.getLatitude();
			float accuracy = location.getAccuracy();
			String loc = longitude + ","+latitude+","+accuracy;
			Editor edit = sp.edit();
			edit.putString("latest_loc", loc);
			edit.commit();
		}

		@Override
		public void onStatusChanged(String provider, int status, Bundle extras) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onProviderEnabled(String provider) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onProviderDisabled(String provider) {
			// TODO Auto-generated method stub
			
		}
		
	}
	@Override
	public void onDestroy() {
		super.onDestroy();
		lm.removeUpdates(mll);
		mll = null;
	}
	
}
