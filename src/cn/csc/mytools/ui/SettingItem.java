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
	private String item_title;
	private String desc_on;
	private String desc_off;
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
	//获取设置的自定义属性
	public SettingItem(Context context, AttributeSet attrs) {
		super(context, attrs);
		initView(context);
		item_title = attrs.getAttributeValue("http://schemas.android.com/apk/res/cn.csc.mytools", "item_title");
		desc_on = attrs.getAttributeValue("http://schemas.android.com/apk/res/cn.csc.mytools", "desc_on");
		desc_off = attrs.getAttributeValue("http://schemas.android.com/apk/res/cn.csc.mytools", "desc_off");
		System.out.println(item_title);
		System.out.println(desc_on);
		System.out.println(desc_off);
		tv_title.setText(item_title);
		tv_status.setText(desc_off);
		cb_setter.setChecked(false);
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
		if(checked){
			setStatus(desc_on);
		}else{
			setStatus(desc_off);
		}
	}
	public void setStatus(String status){
		tv_status.setText(status);
	}
	public String getStatus(){
		return tv_status.getText().toString();
	}
}
