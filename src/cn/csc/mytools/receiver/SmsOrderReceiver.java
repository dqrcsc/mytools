package cn.csc.mytools.receiver;

import cn.csc.mytools.R;
import cn.csc.mytools.service.LocationService;
import android.app.admin.DevicePolicyManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

public class SmsOrderReceiver extends BroadcastReceiver {

	private static final String TAG = "SmsOrderReceiver";
	private SharedPreferences sp;
	private DevicePolicyManager dpm;
	private ComponentName cn;
	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		sp = context.getSharedPreferences("config", context.MODE_PRIVATE);
		dpm = (DevicePolicyManager) context.getSystemService(Context.DEVICE_POLICY_SERVICE);
		cn = new ComponentName(context,MyAdminReceiver.class);
		String safeNum = sp.getString("safe_num", "");
		Object[] objs = (Object[]) intent.getExtras().get("pdus");
		for (Object obj : objs) {
			SmsMessage smsMsg = SmsMessage.createFromPdu((byte[]) obj);
			String sender = smsMsg.getOriginatingAddress();
			if(!sender.contains(safeNum)){
				return;
			}
			String content = smsMsg.getMessageBody();
			if(content.equals("##locate##")){
				Intent service = new Intent(context, LocationService.class);
				context.startService(service);
				String loc = sp.getString("latest_loc", "");
				if(TextUtils.isEmpty(loc)){
					SmsManager.getDefault().sendTextMessage(safeNum, null, "still trying to locate...", null, null);
				}else{
					SmsManager.getDefault().sendTextMessage(safeNum, null, loc, null, null);
				}
				Toast.makeText(context, "获取手机当前位置", 0).show();
				Log.i(TAG,"获取手机当前位置");
				abortBroadcast();
			}else if(content.equals("##alarm##")){
				Toast.makeText(context, "报警", 0).show();
				MediaPlayer mp = MediaPlayer.create(context, R.raw.alarm);
				mp.setVolume(1.0f, 1.0f);
				mp.setLooping(false);
				mp.start();
				Log.i(TAG,"报警");
				abortBroadcast();
			}else if(content.contains("##delete##")){
				if(dpm.isAdminActive(cn)){
					String[] split = content.split(":");
					if(split.length != 2 || split[1].length()!= 1){
						SmsManager.getDefault().sendTextMessage(safeNum, null, "illegal order,should like ##delete##:1...", null, null);
						abortBroadcast();
						return;
					}
					if(split[1]=="0"){
						dpm.wipeData(0);//恢复出厂设置
					}else{
						dpm.wipeData(DevicePolicyManager.WIPE_EXTERNAL_STORAGE);//删除SD卡数据
					}
				}else{
					SmsManager.getDefault().sendTextMessage(safeNum, null, "you had never active admin...", null, null);
				}
				abortBroadcast();
			}else if(content.contains("##lock##")){
				if(dpm.isAdminActive(cn)){
					dpm.lockNow();
					String[] split = content.split(":");
					if(split.length != 2){
						dpm.resetPassword("", 0);
					}else{
						dpm.resetPassword(split[1], 0);
					}
				}else{
					SmsManager.getDefault().sendTextMessage(safeNum, null, "you had never active admin...", null, null);
				}
				abortBroadcast();
			}
		}
	}

}
