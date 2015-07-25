package cn.csc.mytools;

import cn.csc.mytools.utils.EncryptUtils;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class HomeActivity extends Activity {

	private GridView gv_tools;
	private int[] ids = new int[]{
			R.drawable.item_0, R.drawable.item_1, R.drawable.item_2, R.drawable.item_3, R.drawable.item_4,
			R.drawable.item_5, R.drawable.item_6, R.drawable.item_7, R.drawable.item_8
	};
	private String[] items = null;
	private SharedPreferences sp = null;
	private EditText et_password;
	private EditText et_confirm;
	private AlertDialog dialog;
			
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		String tmp = "手机防盗、通讯卫士、软件管理、进程管理、流量统计、手机杀毒、缓存清理、高级工具、设置中心";
		items = tmp.split("、");
//		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_home);
		sp = getSharedPreferences("config", MODE_PRIVATE);
		gv_tools = (GridView) findViewById(R.id.gv_tools);
		gv_tools.setAdapter(new MyAdapter());
		gv_tools.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				switch(position){
				case 0:
					if(hasPassword()){
						showEnterPasswordDialog();
					}else{
						showSetPasswordDialog();
					}
					break;
				case 8:
					openTools(SettingsActivity.class);
					break;
				}
			}
		});
	}
	private boolean hasPassword(){
		
		String password = sp.getString("password", null);
		return !TextUtils.isEmpty(password);
	}
	private void showSetPasswordDialog(){
		AlertDialog.Builder builder = new Builder(this);
		View view = View.inflate(this, R.layout.set_password_dialog, null);
		Button btn_cancel = (Button) view.findViewById(R.id.btn_cancel);
		Button btn_ok = (Button) view.findViewById(R.id.btn_ok);
		et_password = (EditText) view.findViewById(R.id.et_password);
		et_confirm = (EditText) view.findViewById(R.id.et_pwd_confirm);
		btn_ok.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String pwd = et_password.getText().toString();
				String pwd_confirm = et_confirm.getText().toString();
				if(TextUtils.isEmpty(pwd)||TextUtils.isEmpty(pwd_confirm)){
					Toast.makeText(HomeActivity.this, "请输入密码", 0).show();
					return;
				}
				if(pwd.equals(pwd_confirm)){
					sp = getSharedPreferences("config", MODE_PRIVATE);
					Editor edit = sp.edit();
					edit.putString("password", EncryptUtils.md5Encrypt(pwd));
					edit.commit();
					dialog.dismiss();
					openTools(AntiTheftActivity.class);
				}else{
					Toast.makeText(HomeActivity.this, "两次输入不一致", 0).show();
					et_password.setText("");
					et_confirm.setText("");
					return;
				}
			}
		});
		btn_cancel.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				dialog.dismiss();
			}
		});
		dialog = builder.create();
		dialog.setView(view, 0, 0, 0, 0);
		dialog.show();
	}
	private void showEnterPasswordDialog(){
		AlertDialog.Builder builder = new Builder(this);
		View view = View.inflate(this, R.layout.enter_password_dialog, null);
		builder.setView(view);
		Button btn_cancel = (Button) view.findViewById(R.id.btn_cancel);
		Button btn_ok = (Button) view.findViewById(R.id.btn_ok);
		et_password = (EditText) view.findViewById(R.id.et_password);
		btn_ok.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String pwd = et_password.getText().toString();
				if(TextUtils.isEmpty(pwd)){
					Toast.makeText(HomeActivity.this, "请输入密码", 0).show();
					return;
				}
				String pwd_confirm = sp.getString("password", "");
				if(EncryptUtils.md5Encrypt(pwd).equals(pwd_confirm)){
					dialog.dismiss();
					openTools(AntiTheftActivity.class);
				}else{
					Toast.makeText(HomeActivity.this, "密码错误", 0).show();
					et_password.setText("");
					return;
				}
			}
		});
		btn_cancel.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				dialog.dismiss();
			}
		});
		dialog = builder.create();
		dialog.setView(view, 0, 0, 0, 0);
		dialog.show();
	}
	private void openTools(Class<?> cls){
		Intent intent = new Intent(this, cls);
		startActivity(intent);
	}
	private class MyAdapter extends BaseAdapter{

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return ids.length;
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			View view = getLayoutInflater().inflate(R.layout.item, null);
			ImageView iv = (ImageView) view.findViewById(R.id.iv_item);
			TextView tv = (TextView) view.findViewById(R.id.tv_item);
			iv.setImageResource(ids[position]);
			tv.setText(items[position]);
			return view;
		}
		
	}
	
}
