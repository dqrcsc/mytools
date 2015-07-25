package cn.csc.mytools;

import cn.csc.mytools.ui.SettingItem;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.Toast;

public class SetupWizardActivity2 extends Activity {
	private SettingItem item_bound;
	private SharedPreferences sp;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		sp = getSharedPreferences("config", MODE_PRIVATE);
		setContentView(R.layout.activity_setup_wizard2);
		
		item_bound = (SettingItem) findViewById(R.id.item_bound);
		item_bound.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Editor editor = sp.edit();
				if(item_bound.isChecked()){
					item_bound.check(false);
					editor.putBoolean("bound", false);
					Toast.makeText(SetupWizardActivity2.this, item_bound.getStatus(), 0).show();
				}else{
					item_bound.check(true);
					editor.putBoolean("bound", true);
					Toast.makeText(SetupWizardActivity2.this, item_bound.getStatus(), 0).show();
				}
				editor.commit();
			}
		});
	}
	public void next(View view){
		Intent intent = new Intent(this, SetupWizardActivity3.class);
		startActivity(intent);
		finish();
		overridePendingTransition(R.anim.next_in, R.anim.pre_out);
	}
	public void back(View view){
		Intent intent = new Intent(this, SetupWizardActivity1.class);
		startActivity(intent);
		finish();
		overridePendingTransition(R.anim.pre_in, R.anim.next_out);
	}
	
}
