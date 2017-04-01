package com.gnomon.common.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class BaseEncoderUtils {

	/**
	 * 功能：十进制数到十六进制
	 * 
	 * @param value
	 * @return
	 */
	public static String integer2HexString(int value) {
		return Integer.toHexString(value);
	}

	/**
	 * 功能：十进制数到八进制
	 * 
	 * @param value
	 * @return
	 */
	public static String integer2OctalString(int value) {
		return Integer.toOctalString(value);
	}

	/**
	 * 功能：十进制数到二进制
	 * 
	 * @param value
	 * @return
	 */
	public static String integer2BinaryString(int value) {
		return Integer.toBinaryString(value);
	}

	/**
	 * 功能：十六进制数到十进制
	 * 
	 * @param value
	 * @return
	 */
	public static int hexString2Integer(String value) {
		return Integer.parseInt(value, 16);
	}

	/**
	 * 功能：八进制数到十进制
	 * 
	 * @param value
	 * @return
	 */
	public static int octalString2Integer(String value) {
		return Integer.parseInt(value, 8);
	}

	/**
	 * 功能：八进制数到十进制
	 * 
	 * @param value
	 * @return
	 */
	public static int binaryString2Integer(String value) {
		return Integer.parseInt(value, 2);
	}

	/**
	 * 功能：字符串进行MD5编码，返回16进制字符串
	 * 
	 * @param message
	 * @return
	 */
	public static String md5Encode(String message) {
		return encodeString2BASE64("MD5", message);
	}

	/**
	 * 功能：字符串进行SHA编码，返回16进制字符串
	 * 
	 * @param message
	 * @return
	 */
	public static String shaEncode(String message) {
		return encodeString2BASE64("SHA", message);
	}
	public static String shaEncode(byte[] b) {
		return encodeByteArray2BASE64("SHA", b);
	}

	/**
	 * 功能：字符串进行SHA-256编码，返回16进制字符串
	 * 
	 * @param message
	 * @return
	 */
	public static String sha256Encode(String message) {
		return encodeString2BASE64("SHA-256", message);
	}

	/**
	 * 功能：字符串进行SHA-512编码，返回16进制字符串
	 * 
	 * @param message
	 * @return
	 */
	public static String sha512Encode(String message) {
		return encodeString2BASE64("SHA-512", message);
	}

	/**
	 * 功能：将字符串进行BASE64编码，返回字符串
	 * 
	 * @param src
	 * @return
	 */
	public static String getBASE64(String src) {
		if (src == null) {
			return null;
		}
		return getBASE64(src.getBytes());
	}

	public static String getBASE64(byte[] src) {
		org.apache.commons.codec.binary.Base64 encoder = new org.apache.commons.codec.binary.Base64();
		return new String(encoder.encode(src));
	}

	/**
	 * 功能：将BASE64编码的字符串src进行解码
	 * 
	 * @param src
	 * @return
	 */
	public static String getFromBASE64(String src) {
		return new String(getBytesFromBASE64(src));
	}

	public static byte[] getBytesFromBASE64(String src) {
		if (src == null) {
			return null;
		}
		org.apache.commons.codec.binary.Base64 decoder = new org.apache.commons.codec.binary.Base64();
		try {
			return decoder.decode(src);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 功能：将字符串转为16进制字符串表示
	 * 
	 * @param src
	 * @return
	 */
	public static String string2Hex(String src) {
		if (src == null)
			return null;
		byte[] b = src.getBytes();
		StringBuffer sb = new StringBuffer(b.length);
		String sTemp;
		for (int i = 0; i < b.length; i++) {
			sTemp = Integer.toHexString(0xFF & b[i]);
			if (sTemp.length() < 2)
				sb.append(0);
			sb.append(sTemp.toUpperCase());
		}
		return sb.toString();

		// if(src==null)
		// return null;
		// byte[] b=src.getBytes();
		// String ret = "";
		// for (int i = 0; i < b.length; i++) {
		// String hex = Integer.toHexString(b[i] & 0xFF);
		// if (hex.length() == 1) {
		// hex = '0' + hex;
		// }
		// ret += hex.toUpperCase();
		// }
		// return ret;
	}

	/**
	 * 功能：将字符串进行编码，并返回字符串
	 * 
	 * @param src
	 * @return
	 */
	public static String encoderString(String code, String src) {
		try {
			byte[] strTemp = src.getBytes();
			MessageDigest messageDigest = MessageDigest.getInstance(code);
			messageDigest.update(strTemp);
			byte[] md = messageDigest.digest();
			return byteArray2String(md);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 功能：将字节数组转为字符串
	 * 
	 * @param byteArray
	 * @return encode
	 */
	private static String byteArray2String(byte[] byteArray) {
		String encode = null;
		int j = byteArray.length;
		char str[] = new char[j * 2];
		int k = 0;
		for (int i = 0; i < j; i++) {
			byte byte0 = byteArray[i];
			str[k++] = hexDigits[byte0 >>> 4 & 0xf];
			str[k++] = hexDigits[byte0 & 0xf];
		}
		encode = new String(str);
		return encode;
	}

	/**
	 * 功能：将字符串进行编码，并返回16进制字符串
	 * 
	 * @param code
	 * @param message
	 * @return encode
	 */
	private static String encodeString2Hex(String code, String message) {
		MessageDigest md;
		String encode = null;
		try {
			md = MessageDigest.getInstance(code);
			encode = byteArrayToHexString(md.digest(message.getBytes()));
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return encode;
	}

	private static String encodeString2BASE64(String code, String message) {
		MessageDigest md;
		String encode = null;
		try {
			md = MessageDigest.getInstance(code);
			encode = getBASE64(md.digest(message.getBytes()));
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return encode;
	}

	private static String encodeByteArray2BASE64(String code, byte[] b) {
		MessageDigest md;
		String encode = null;
		try {
			md = MessageDigest.getInstance(code);
			encode = getBASE64(md.digest(b));
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return encode;
	}

	/**
	 * 功能：对字节数组编码
	 * 
	 * @param byteArray
	 * @return
	 */
	private static String byteArrayToHexString(byte[] byteArray) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < byteArray.length; i++) {
			sb.append(byteToHexString(byteArray[i]));
		}
		return sb.toString();
	}

	/**
	 * 功能：将字节进行16进制编码
	 * 
	 * @param byt
	 * @return
	 */
	private static String byteToHexString(byte byt) {
		int n = byt;
		if (n < 0) {
			n = 256 + n;
		}
		return String.valueOf(hexDigits[n / 16] + hexDigits[n % 16]);
	}

	private static char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
	
	
	
	
	
	
	
	
	
	
	private static char[] chars = 
		{
		'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 
		'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
		'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'
		};
	
	private static int charsLen = chars.length;
	
	public static String longToString(long n) {
		StringBuffer sb = new StringBuffer();
		while (n != 0) {
			sb.insert(0, chars[(int)(n % charsLen)]);
			n /= charsLen;
		}
		return sb.toString();
	}
	
	public static void main(String[] args) throws Exception {
		for (int i = 0; i < 100; i++) {
			Thread.sleep(100);
			System.out.println(longToString(Long.parseLong("99999" + System.currentTimeMillis())));
		}
		
		
		
		
		/*System.out.println(string2Hex("测试数据,123.456"));
		System.out.println(string2Hex("测试数据,123.456"));
		System.out.println(octalString2Integer("123"));
		System.out.println(integer2OctalString(8));
		System.out.println(Integer2OctalString(BinaryString2Integer("123")));

		System.out.println(encoderString("MD5", "测试数据,123.456"));
		System.out.println(encoderString("SHA-1", "测试数据,123.456"));
		System.out.println(getFromBASE64("测试数据,123.456"));
		System.out.println(md5Encode("测试数据,123.456"));
		System.out.println(shaEncode("qweqweqweqweqwe"));
		System.out.println(shaEncode("qweqweqweqweqwe123assdwe23234werdsf").length());
		System.out.println(sha256Encode("测试数据,123.456"));
		System.out.println(sha512Encode("测试数据,123.456"));
		
		System.out.println("BASE64:" + getBASE64("测试数据,123.456"));
		System.out.println("BASE64:" + getFromBASE64(getBASE64("测试数据,123.456")));*/
	}



}
