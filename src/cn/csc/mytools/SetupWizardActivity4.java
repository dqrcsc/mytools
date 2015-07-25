package cn.csc.mytools;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;
import android.widget.CompoundButton.OnCheckedChangeListener;

public class SetupWizardActivity4 extends Activity {
	
	private SharedPreferences sp;
	private CheckBox cb_open;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		sp = getSharedPreferences("config", MODE_PRIVATE);
		setContentView(R.layout.activity_setup_wizard4);
		cb_open = (CheckBox) findViewById(R.id.cb_open);
		cb_open.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// TODO Auto-generated method stub
				if(isChecked){
					Toast.makeText(SetupWizardActivity4.this, "已成功开启手机防盗", 0).show();
				}else{
					Toast.makeText(SetupWizardActivity4.this, "已成功关闭手机防盗", 0).show();
				}
			}
		});
	}
	public void next(View view){
		Editor edit = sp.edit();
		edit.putBoolean("configed", true);
		edit.commit();
		Intent intent = new Intent(this, AntiTheftActivity.class);
		startActivity(intent);
		finish();
		overridePendingTransition(R.anim.next_in, R.anim.pre_out);
	}
	public void back(View view){
		Intent intent = new Intent(this, SetupWizardActivity3.class);
		startActivity(intent);
		finish();
		overridePendingTransition(R.anim.pre_in, R.anim.next_out);
	}
}
