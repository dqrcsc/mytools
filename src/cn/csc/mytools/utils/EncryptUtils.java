package cn.csc.mytools.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class EncryptUtils {
	public static String md5Encrypt(String password){
		try {
			MessageDigest digest = MessageDigest.getInstance("md5");
			byte[] bs = digest.digest(password.getBytes());
			StringBuffer sb = new StringBuffer();
			for(byte b:bs){
				int num = b&0xff;
				String str = Integer.toHexString(num);
				if(str.length() == 1){
					sb.append("0");
				}
				sb.append(str);
			}
			return sb.toString();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
}
