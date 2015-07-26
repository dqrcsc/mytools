package cn.csc.mytools;

import cn.csc.mytools.receiver.MyAdminReceiver;
import android.app.Activity;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.os.Bundle;
import android.view.View;

public class DeactiveAdminConfirmActivity extends Activity {
	private DevicePolicyManager dpm;
	private ComponentName cn;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		dpm = (DevicePolicyManager) getSystemService(DEVICE_POLICY_SERVICE);
		cn = new ComponentName(this,MyAdminReceiver.class);
		setContentView(R.layout.activity_deactive_confirm);
	}
	public void deactive(View view){
		dpm.removeActiveAdmin(cn);
		finish();
	}
	public void cancel(View view){
		finish();
	}
}
