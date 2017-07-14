package com.gnomon.common.utils;

import java.security.SecureRandom;
import java.util.UUID;

/**
 * ��װ��������Ψһ��ID�㷨�Ĺ�����.
 * 
 * @author calvin
 */
public class Identities {

	private static SecureRandom random = new SecureRandom();

	private Identities() {
	}

	/**
	 * ��װJDK�Դ���UUID, ͨ��Random��������,�м���-�ָ�
	 */
	public static String uuid() {
		return UUID.randomUUID().toString();
	}

	/**
	 * ��װJDK�Դ���UUID, ͨ��Random��������,�м���-�ָ�
	 */
	public static String uuid2() {
		return UUID.randomUUID().toString().replaceAll("-", "").toUpperCase();
	}

	/**
	 * ʹ��SecureRandom�������Long. 
	 */
	public static long randomLong() {
		return random.nextLong();
	}

	/**
	 * ����Base62������������Long.
	 */
	public static String randomBase62() {
		return Encodes.encodeBase62(random.nextLong());
	}
}