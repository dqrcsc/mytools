package cn.csc.mytools;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class SetupWizardActivity1 extends AbstractSetupWizardActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_setup_wizard1);
	}
	@Override
	public void showPre() {
		
	}
	@Override
	public void showNext() {
		Intent intent = new Intent(this, SetupWizardActivity2.class);
		startActivity(intent);
		finish();
		overridePendingTransition(R.anim.next_in, R.anim.pre_out);
	}
	
}
