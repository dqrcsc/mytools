package cn.csc.mytools;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.View;

public abstract class AbstractSetupWizardActivity extends Activity {
	private GestureDetector gd;
	protected SharedPreferences sp;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		sp = getSharedPreferences("config", MODE_PRIVATE);
		gd = new GestureDetector(this,  new SimpleOnGestureListener(){

			@Override
			public boolean onFling(MotionEvent e1, MotionEvent e2,
					float velocityX, float velocityY) {
				//从右向左滑动
				if(e1.getRawX() - e2.getRawX() > 200){
					System.out.println("从右向左滑动");
					showNext();
					return true;
				}
				//从左向右滑动
				if(e2.getRawX() - e1.getRawX() > 200){
					System.out.println("从左向右滑动");
					showPre();
					return true;
				}
				return super.onFling(e1, e2, velocityX, velocityY);
			}	
			
		});
	}
	public abstract void showPre();
	public abstract void showNext();
	public void next(View view){
		showNext();
	}
	public void back(View view){
		showPre();
	}
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		gd.onTouchEvent(event);
		return super.onTouchEvent(event);
	}
	

}
