package cn.csc.mytools;

import android.app.Activity;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.GestureDetector.SimpleOnGestureListener;

public class AbstractToolsActivity extends Activity {
	private GestureDetector gd;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		gd = new GestureDetector(this, new SimpleOnGestureListener(){

			@Override
			public boolean onFling(MotionEvent e1, MotionEvent e2,
					float velocityX, float velocityY) {
				if(e2.getRawX()-e1.getRawX() > 200){
					finish();
					overridePendingTransition(R.anim.pre_in, R.anim.next_out);
				}
				return super.onFling(e1, e2, velocityX, velocityY);
			}
			
		});
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		gd.onTouchEvent(event);
		return super.onTouchEvent(event);
	}
	
}
