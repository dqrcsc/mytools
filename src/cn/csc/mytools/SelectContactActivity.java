package cn.csc.mytools;

import java.util.List;
import java.util.Map;

import cn.csc.mytools.utils.ContactUtils;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class SelectContactActivity extends Activity {
	protected static final int RET_OK = 0;
	private ListView lv_contacts;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_select_contact);
		lv_contacts = (ListView) findViewById(R.id.lv_contacts);
		final List<Map<String, String>> contacts = ContactUtils.getContacts(this);
		lv_contacts.setAdapter(new SimpleAdapter(this, contacts, 
				R.layout.contact_item, new String[]{"name","phone"}, 
				new int[]{R.id.tv_contact_name,R.id.tv_contact_phone}));
		lv_contacts.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				String phone = contacts.get(position).get("phone");
				Intent data = new Intent();
				data.putExtra("phone", phone);
				setResult(RET_OK, data );
				finish();
			}
			
		});
	}
}
