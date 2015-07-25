package cn.csc.mytools;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

public class AntiTheftActivity extends Activity {
	private SharedPreferences sp;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
//		requestWindowFeature(Window.FEATURE_NO_TITLE);
		sp = getSharedPreferences("config", MODE_PRIVATE);
		boolean isConfiged = sp.getBoolean("configed", false);
		if(isConfiged){
			setContentView(R.layout.activity_anti_theft);
		}else{
			Intent intent = new Intent(this, SetupWizardActivity1.class);
			startActivity(intent);
			finish();
		}
	}
	public void enterSetupWizard(View view){
		Intent intent = new Intent(this, SetupWizardActivity1.class);
		startActivity(intent);
		finish();
		overridePendingTransition(R.anim.pre_in, R.anim.next_out);
	}

}
