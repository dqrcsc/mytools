package cn.csc.mytools;

import android.content.Intent;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SetupWizardActivity3 extends AbstractSetupWizardActivity {
	protected static final int SELECT_CONTACT = 100;
	private Button btn_select_contact;
	private EditText et_safenum;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_setup_wizard3);
		et_safenum = (EditText) findViewById(R.id.et_safenum);
		et_safenum.setText(sp.getString("safe_num", ""));
		btn_select_contact = (Button) findViewById(R.id.btn_select_contact);
		btn_select_contact.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(SetupWizardActivity3.this, SelectContactActivity.class);
				startActivityForResult(intent, SELECT_CONTACT);
			}
		});
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if(data == null)
			return;
		if(requestCode == SELECT_CONTACT){
			String phone = data.getStringExtra("phone").replace("-", "").replace(" ", "");
			et_safenum.setText(phone);
		}
	}
	@Override
	public void showPre() {
		Intent intent = new Intent(this, SetupWizardActivity2.class);
		startActivity(intent);
		finish();
		overridePendingTransition(R.anim.pre_in, R.anim.next_out);
	}
	@Override
	public void showNext() {
		String phone = et_safenum.getText().toString().trim();
		if(TextUtils.isEmpty(phone)){
			Toast.makeText(this,"请设置安全号码", 0).show();
			return;
		}
		Editor edit = sp.edit();
		edit.putString("safe_num", phone);
		edit.commit();
		Intent intent = new Intent(this, SetupWizardActivity4.class);
		startActivity(intent);
		finish();
		overridePendingTransition(R.anim.next_in, R.anim.pre_out);
	}
}
