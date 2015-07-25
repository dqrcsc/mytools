package cn.csc.mytools;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Toast;
import cn.csc.mytools.ui.SettingItem;

public class SettingsActivity extends Activity {
	private SettingItem item_update;
	private SharedPreferences sp;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
//		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_settings);
		sp = getSharedPreferences("config", MODE_PRIVATE);
		
		item_update = (SettingItem) findViewById(R.id.item_update);
		boolean b = sp.getBoolean("auto_update", false);
		item_update.check(b);
		item_update.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Editor editor = sp.edit();
				if(item_update.isChecked()){
					item_update.check(false);
					editor.putBoolean("auto_update", false);
					Toast.makeText(SettingsActivity.this, item_update.getStatus(), 0).show();
				}else{
					item_update.check(true);
					editor.putBoolean("auto_update", true);
					Toast.makeText(SettingsActivity.this, item_update.getStatus(), 0).show();
				}
				editor.commit();
			}
		});
	}
	
}
