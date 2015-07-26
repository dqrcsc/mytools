package cn.csc.mytools;

import cn.csc.mytools.receiver.MyAdminReceiver;
import android.app.Activity;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class AntiTheftActivity extends AbstractToolsActivity {
	private static final int REQ_CODE_ACTIVE_ADMIN = 0;
	private SharedPreferences sp;
	private DevicePolicyManager dpm;
	private TextView tv_safe_num;
	private ImageView iv_lock;
	private TextView tv_active;
	private Button btn_active;
	private ComponentName cn;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		sp = getSharedPreferences("config", MODE_PRIVATE);
		dpm = (DevicePolicyManager) getSystemService(DEVICE_POLICY_SERVICE);
		boolean isConfiged = sp.getBoolean("configed", false);
		if(isConfiged){
			setContentView(R.layout.activity_anti_theft);
			tv_safe_num = (TextView) findViewById(R.id.tv_safe_num);
			iv_lock = (ImageView) findViewById(R.id.iv_lock);
			tv_active = (TextView) findViewById(R.id.tv_active);
			btn_active = (Button) findViewById(R.id.btn_active);
			cn = new ComponentName(this,MyAdminReceiver.class);
			updateUi();
			tv_safe_num.setText(sp.getString("safe_num", ""));
			if(sp.getBoolean("open_anti_theft", false)){
				iv_lock.setImageResource(R.drawable.lock);
			}else{
				iv_lock.setImageResource(R.drawable.unlock);
			}
		}else{
			Intent intent = new Intent(this, SetupWizardActivity1.class);
			startActivity(intent);
			finish();
		}
		
	}
	public void active(View view){
		if(dpm.isAdminActive(cn)){
			Intent intent = new Intent(this, DeactiveAdminConfirmActivity.class);
			startActivityForResult(intent, REQ_CODE_ACTIVE_ADMIN);
		}else{
			// 创建一个Intent
			Intent intent = new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
			// 我要激活谁
			intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN,
					cn);
			// 劝说用户开启管理员权限
			intent.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION,
					"激活管理员权限之后，可以使用远程数据销毁，及远程锁屏功能");
			startActivityForResult(intent, REQ_CODE_ACTIVE_ADMIN);
		}
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		updateUi();
	}
	private void updateUi() {
		if(dpm.isAdminActive(cn)){
			tv_active.setText("已激活管理员权限");
			btn_active.setText("取消激活");
		}else{
			tv_active.setText("还未激活管理员权限");
			btn_active.setText("激活");
		}
	}
	public void enterSetupWizard(View view){
		Intent intent = new Intent(this, SetupWizardActivity1.class);
		startActivity(intent);
		finish();
		overridePendingTransition(R.anim.pre_in, R.anim.next_out);
	}

}
