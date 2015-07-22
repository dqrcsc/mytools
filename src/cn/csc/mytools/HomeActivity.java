package cn.csc.mytools;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

public class HomeActivity extends Activity {

	private GridView gv_tools;
	private int[] ids = new int[]{
			R.drawable.item_0, R.drawable.item_1, R.drawable.item_2, R.drawable.item_3, R.drawable.item_4,
			R.drawable.item_5, R.drawable.item_6, R.drawable.item_7, R.drawable.item_8
	};
	private String[] items = null;
			
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		String tmp = "手机防盗、通讯卫士、软件管理、进程管理、流量统计、手机杀毒、缓存清理、高级工具、设置中心";
		items = tmp.split("、");
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_home);
		gv_tools = (GridView) findViewById(R.id.gv_tools);
		gv_tools.setAdapter(new MyAdapter());
		gv_tools.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				switch(position){
				case 8:
					openTools(SettingsActivity.class);
					break;
				}
			}
		});
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
