package com.lucifer.utils;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class Md5Utils {
	
	private static  Log log = LogFactory.getLog(Md5Utils.class);

	 public static String md5(String input) {
	        String md5 = null;
	        if(null == input) return null;
	        try {
	            //Create MessageDigest object for MD5
	            MessageDigest digest = MessageDigest.getInstance("MD5");

	            //Update input string in message digest
	            digest.update(input.getBytes(), 0, input.length());

	            byte[] resultByteArray = digest.digest();
	            
	            //Converts message digest value in base 16 (hex)
	            md5 = byteArrayToHex(resultByteArray);
	            md5 = md5.toLowerCase();

	        } catch (NoSuchAlgorithmException e) {
	        	log.error(e);
	        }
	        return md5;
	    }
	 
	 public static String byteArrayToHex(byte[] byteArray) {

		  // 首先初始化一个字符数组，用来存放每个16进制字符

		  char[] hexDigits = {'0','1','2','3','4','5','6','7','8','9', 'A','B','C','D','E','F' };



		  // new一个字符数组，这个就是用来组成结果字符串的（解释一下：一个byte是八位二进制，也就是2位十六进制字符（2的8次方等于16的2次方））

		  char[] resultCharArray =new char[byteArray.length * 2];



		  // 遍历字节数组，通过位运算（位运算效率高），转换成字符放到字符数组中去

		  int index = 0;

		  for (byte b : byteArray) {

		     resultCharArray[index++] = hexDigits[b>>> 4 & 0xf];

		     resultCharArray[index++] = hexDigits[b& 0xf];

		  }



		  // 字符数组组合成字符串返回

		  return new String(resultCharArray);
		}
	 
	 public static String md5Old(String input) {
	        String md5 = null;
	        if(null == input) return null;
	        try {
	            //Create MessageDigest object for MD5
	            MessageDigest digest = MessageDigest.getInstance("MD5");

	            //Update input string in message digest
	            digest.update(input.getBytes(), 0, input.length());

	            //Converts message digest value in base 16 (hex)
	            md5 = new BigInteger(1, digest.digest()).toString(16);

	        } catch (NoSuchAlgorithmException e) {
	        	log.error(e);
	        }
	        return md5;
	    }
}
