package cn.csc.mytools.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;
import android.widget.Toast;

public class BootCompleteReceiver extends BroadcastReceiver {
	private SharedPreferences sp;
	private TelephonyManager tm;
	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		sp = context.getSharedPreferences("config", context.MODE_PRIVATE);
		if(sp.getBoolean("open_anti_theft", false)){
			String savedSim = sp.getString("sim", "")/*  +"salt"测试时用于产生不同的sim号  */;
			tm = (TelephonyManager) context.getSystemService(context.TELEPHONY_SERVICE);
			String readSim = tm.getSimSerialNumber();
			if(!savedSim.equals(readSim)){
				Toast.makeText(context, "Sim卡已变更，手机可能被盗", 0).show();
				SmsManager sm = SmsManager.getDefault();
				sm.sendTextMessage(sp.getString("safe_num", ""), null, "Sim卡已变更，手机可能被盗", null, null);
			}
		}
		
	}

}
