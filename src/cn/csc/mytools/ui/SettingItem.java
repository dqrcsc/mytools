package cn.csc.mytools.ui;

import cn.csc.mytools.R;
import cn.csc.mytools.SettingsActivity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class SettingItem extends RelativeLayout {

	private TextView tv_title;
	private TextView tv_status;
	private CheckBox cb_setter;
	private void initView(Context context){
		View.inflate(context, R.layout.settings_item, this);
		tv_title = (TextView) this.findViewById(R.id.tv_title);
		tv_status = (TextView) this.findViewById(R.id.tv_status);
		cb_setter = (CheckBox) this.findViewById(R.id.cb_setter);
	}
	public SettingItem(Context context) {
		super(context);
		initView(context);
		
	}
	
	public SettingItem(Context context, AttributeSet attrs) {
		super(context, attrs);
		initView(context);
		}

	public SettingItem(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		initView(context);
		}
	public boolean isChecked(){
		return cb_setter.isChecked();
	}
	public void check(boolean checked){
		cb_setter.setChecked(checked);
	}
	public void setStatus(String status){
		tv_status.setText(status);
	}
}
