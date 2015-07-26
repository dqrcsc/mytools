package cn.csc.mytools.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

public class ContactUtils {
	public static List<Map<String, String>> getContacts(Context context){
		List<Map<String, String>> list = new ArrayList<Map<String,String>>();
		
		ContentResolver resolver = context.getContentResolver();
		Uri contactUri = Uri.parse("content://com.android.contacts/contacts");
		Uri dataUri = Uri.parse("content://com.android.contacts/data");
		
		Cursor cursor = resolver.query(contactUri, new String[]{"_id"}, null, null, null);
		while(cursor.moveToNext()){
			String id = cursor.getString(0);
			Cursor cursor2 = resolver.query(dataUri, new String[]{"mimetype","data1"}, "contact_id=?", new String[]{id}, null);
			Map<String,String> map = new HashMap<String, String>();
			while(cursor2.moveToNext()){
				String type = cursor2.getString(0);
				String data = cursor2.getString(1);
				if(type.equals("vnd.android.cursor.item/phone_v2")){
					map.put("phone", data);
				}else{
					map.put("name", data);
				}
				
			}
			list.add(map);
			cursor2.close();
		}
		return list;
	}
}
