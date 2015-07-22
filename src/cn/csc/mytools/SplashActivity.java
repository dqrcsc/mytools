package cn.csc.mytools;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


public class SplashActivity extends Activity {
	
	private String updateInfo = null;
	private String updateUrl = null;
	private String new_version = null;
	private SharedPreferences sp;
	private ProgressBar pb_update;
	private Handler handler = new Handler(){
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			switch(msg.what){
			case URL_ERROR:
				Toast.makeText(SplashActivity.this, "获取版本信息失败", 0).show();
				enterHome();
				break;
			case UPDATE:
				alertUpdate();
				break;
			case ENTER_HOME:
				enterHome();
				break;
			case NET_ERROR:
				Toast.makeText(SplashActivity.this, "网络异常", 0).show();
				enterHome();
				break;
			case JSON_ERROR:
				Toast.makeText(SplashActivity.this, "解析版本更新信息异常", 0).show();
				enterHome();
				break;
			}
		}
	};
    protected static final String TAG = "SplashActivity";
	protected static final int URL_ERROR = 0;
	protected static final int UPDATE = 1;
	protected static final int ENTER_HOME = 3;
	protected static final int NET_ERROR = 4;
	protected static final int JSON_ERROR = 5;
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_splash);
        TextView tv = (TextView) findViewById(R.id.tv_version);
        tv.setText("Version: "+getVersionName());
        pb_update = (ProgressBar) findViewById(R.id.pb_update);
        sp = getSharedPreferences("config", MODE_PRIVATE);
        boolean b = sp.getBoolean("auto_update", false);
        if(!b){
        	checkUpdate();
        }else{
        	//自动更新
        	handler.postDelayed(new Runnable() {
				
				@Override
				public void run() {
					enterHome();
				}
			}, 2000);
        }
        
    }
	private void alertUpdate(){
		AlertDialog.Builder builder = new Builder(this);
		builder.setTitle("有新版本");
//		builder.setCancelable(false);
		builder.setMessage(updateInfo);
		builder.setOnCancelListener(new OnCancelListener() {

			@Override
			public void onCancel(DialogInterface dialog) {
				// TODO Auto-generated method stub
				enterHome();
			}
		});
		builder.setPositiveButton("立即更新", new OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
					FinalHttp fh = new FinalHttp();
					fh.download(updateUrl, 
							Environment.getExternalStorageDirectory().getAbsolutePath()+"/mytools"+new_version+".apk", 
							new AjaxCallBack<File>() {
								@Override
								public void onFailure(Throwable t, int errorNo,
										String strMsg) {
									t.printStackTrace();
									super.onFailure(t, errorNo, strMsg);
									Toast.makeText(SplashActivity.this, "下载失败", 0).show();
									enterHome();
								}
								@Override
								public void onLoading(long count, long current) {
									// TODO Auto-generated method stub
									pb_update.setVisibility(View.VISIBLE);
									pb_update.setProgress((int) (current*100/count));
									super.onLoading(count, current);
								}

								@Override
								public void onSuccess(File t) {
									// TODO Auto-generated method stub
									super.onSuccess(t);
									Toast.makeText(SplashActivity.this, "下载成功", 0).show();
									installAPK(t);
								}
								private void installAPK(File t) {
									Intent intent = new Intent();
									intent.setAction("android.intent.action.VIEW");
									intent.addCategory("android.intent.category.DEFAULT");
									intent.setDataAndType(Uri.fromFile(t), "application/vnd.android.package-archive");
									startActivity(intent);
								}
					});
				}else{
					Toast.makeText(SplashActivity.this, "内存卡不存在", 0).show();
					return;
				}
				
			}
		});
		builder.setNegativeButton("下次再说", new OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				dialog.dismiss();
				enterHome();
			}
		});
		builder.show();
	}
	private void enterHome(){
		Intent intent = new Intent(this, HomeActivity.class);
		startActivity(intent);
		finish();
	}
    private void checkUpdate(){
    	
    	new Thread(){
    		public void run(){
    				long start = System.currentTimeMillis();
    				URL url;
    				Message msg = handler.obtainMessage();
					try {
						url = new URL(getString(R.string.server_url)+"/version.json");
						HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	    				conn.setRequestMethod("GET");
	    				int code = conn.getResponseCode();
	    				if(code == 200){
	    					InputStream is = conn.getInputStream();
	    					BufferedReader br = new BufferedReader(new InputStreamReader(is));
	    					String version = br.readLine();
	    					Log.i(TAG,version);
	    					JSONObject json = new JSONObject(version);
	    					String new_ver = json.getString("version");
	    					new_version = new_ver;
	    					updateInfo = json.getString("info");
	    					updateUrl = json.getString("url");
	    					if(!new_ver.equals(getVersionName())){
	    						//
	    						Log.i(TAG,"版本有更新:"+version);
	    						msg.what = UPDATE;
	    					}else{
	    						msg.what = ENTER_HOME;
	    					}
	    				}

					} catch (MalformedURLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						//url错误
						msg.what = URL_ERROR;
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						//网络错误
						msg.what = NET_ERROR;
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						//json解析错误
						msg.what = JSON_ERROR;
					} finally{
						long now = System.currentTimeMillis();
						if(now -start <2000){
								try {
									Thread.sleep(2000-now + start);
								} catch (InterruptedException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
						}
						//发送主线程消息
						handler.sendMessage(msg);
						
					}
    				
    		}
    	}.start();
    }
    private String getVersionName(){
    	PackageManager pm = getPackageManager();
    	try {
			PackageInfo info = pm.getPackageInfo(getPackageName(), 0);
			return info.versionName;
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "";
		}
    }
    
}
