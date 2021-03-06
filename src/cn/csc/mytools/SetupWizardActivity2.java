package cn.csc.mytools;

import cn.csc.mytools.ui.SettingItem;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.Toast;

public class SetupWizardActivity2 extends AbstractSetupWizardActivity {
	private SettingItem item_bound;
	private TelephonyManager tm;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_setup_wizard2);
		
		item_bound = (SettingItem) findViewById(R.id.item_bound);
		String sim = sp.getString("sim", "");
		item_bound.check(!TextUtils.isEmpty(sim));
		tm = (TelephonyManager)getSystemService(TELEPHONY_SERVICE);
		item_bound.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Editor editor = sp.edit();
				String ssn = tm.getSimSerialNumber();
				if(item_bound.isChecked()){
					item_bound.check(false);
					
					editor.putString("sim", "");
					Toast.makeText(SetupWizardActivity2.this, item_bound.getStatus(), 0).show();
				}else{
					item_bound.check(true);
					editor.putString("sim", ssn);
					Toast.makeText(SetupWizardActivity2.this, item_bound.getStatus(), 0).show();
				}
				editor.commit();
			}
		});
	}
	@Override
	public void showPre() {
		Intent intent = new Intent(this, SetupWizardActivity1.class);
		startActivity(intent);
		finish();
		overridePendingTransition(R.anim.pre_in, R.anim.next_out);
	}
	@Override
	public void showNext() {
		String sim = sp.getString("sim", "");
		if(TextUtils.isEmpty(sim)){
			Toast.makeText(this,"必须绑定sim卡，否则无法使用手机防盗的功能", 1).show();
			return;
		}
		Intent intent = new Intent(this, SetupWizardActivity3.class);
		startActivity(intent);
		finish();
		overridePendingTransition(R.anim.next_in, R.anim.pre_out);
	}
	
}
