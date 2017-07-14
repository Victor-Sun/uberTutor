package com.gnomon.common.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;

  
/** 
 * �������� 
 * ���ܳ����� 
 */  
public class EncryptUtil {  
    // ��Կ��16λ���ȵ�byte[]����Base64ת����õ����ַ���  
    public static String key = "LmMGStGtOpF4xNyvYt54EQ==";  
  
    /** 
     * <li> 
     * ��������:encrypt</li> <li> 
     * ���ܷ��� 
     * @param xmlStr 
     *            ��Ҫ���ܵ���Ϣ�ַ��� 
     * @return ���ܺ���ַ��� 
     */  
    public static String encrypt(String xmlStr) {  
        byte[] encrypt = null;  
  
        try {  
            // ȡ��Ҫ�������ݵ�utf-8���롣  
            encrypt = xmlStr.getBytes("utf-8");  
        } catch (UnsupportedEncodingException e) {  
            e.printStackTrace();  
        }  
        // ȡMD5Hash�룬����ϼ�������  
        byte[] md5Hasn = null;  
        try {  
            md5Hasn = EncryptUtil.MD5Hash(encrypt, 0, encrypt.length);  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
        // �����Ϣ��  
        byte[] totalByte = EncryptUtil.addMD5(md5Hasn, encrypt);  
  
        // ȡ��Կ��ƫת����  
        byte[] key = new byte[8];  
        byte[] iv = new byte[8];  
        getKeyIV(EncryptUtil.key, key, iv);  
        SecretKeySpec deskey = new SecretKeySpec(key, "DES");  
        IvParameterSpec ivParam = new IvParameterSpec(iv);  
  
        // ʹ��DES�㷨ʹ�ü�����Ϣ��  
        byte[] temp = null;  
        try {  
            temp = EncryptUtil.DES_CBC_Encrypt(totalByte, deskey, ivParam);  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
  
        // ʹ��Base64���ܺ󷵻�  
        return new String(Base64.encodeBase64(temp));  
    }  
  
    /** 
     * <li> 
     * ��������:encrypt</li> <li> 
     * ��������: 
     *  
     * <pre> 
     * ���ܷ��� 
     * </pre> 
     *  
     * </li> 
     *  
     * @param xmlStr 
     *            ��Ҫ���ܵ���Ϣ�ַ��� 
     * @return ���ܺ���ַ��� 
     * @throws Exception 
     */  
    public static String decrypt(String xmlStr) throws Exception {  
        // base64����  
        byte[] encBuf = null;  
        encBuf = Base64.decodeBase64(xmlStr);  
  
        // ȡ��Կ��ƫת����  
        byte[] key = new byte[8];  
        byte[] iv = new byte[8];  
        getKeyIV(EncryptUtil.key, key, iv);  
  
        SecretKeySpec deskey = new SecretKeySpec(key, "DES");  
        IvParameterSpec ivParam = new IvParameterSpec(iv);  
  
        // ʹ��DES�㷨����  
        byte[] temp = null;  
        try {  
            temp = EncryptUtil.DES_CBC_Decrypt(encBuf, deskey, ivParam);  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
  
        // ���н��ܺ��md5HashУ��  
        byte[] md5Hash = null;  
        try {  
            md5Hash = EncryptUtil.MD5Hash(temp, 16, temp.length - 16);  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
  
        // ���н���У��  
        for (int i = 0; i < md5Hash.length; i++) {  
            if (md5Hash[i] != temp[i]) {  
                // System.out.println(md5Hash[i] + "MD5У�����" + temp[i]);  
                throw new Exception("MD5У�����");  
            }  
        }  
  
        // ���ؽ��ܺ�����飬����ǰ16λMD5Hash��Ҫ��ȥ��  
        return new String(temp, 16, temp.length - 16, "utf-8");  
    }  
  
    /** 
     * <li> 
     * ��������:TripleDES_CBC_Encrypt</li> <li> 
     * ��������: 
     *  
     * <pre> 
     * ������װ������DES/CBC�����㷨������������ģ���ע����롣 
     * </pre> 
     *  
     * </li> 
     *  
     * @param sourceBuf 
     *            ��Ҫ�������ݵ��ֽ����顣 
     * @param deskey 
     *            KEY ��24λ�ֽ�����ͨ��SecretKeySpec��ת�����ɡ� 
     * @param ivParam 
     *            IVƫת��������8λ�ֽ�����ͨ��IvParameterSpec��ת�����ɡ� 
     * @return ���ܺ���ֽ����� 
     * @throws Exception 
     */  
    public static byte[] TripleDES_CBC_Encrypt(byte[] sourceBuf,  
            SecretKeySpec deskey, IvParameterSpec ivParam) throws Exception {  
        byte[] cipherByte;  
        // ʹ��DES�ԳƼ����㷨��CBCģʽ����  
        Cipher encrypt = Cipher.getInstance("TripleDES/CBC/PKCS5Padding");  
  
        encrypt.init(Cipher.ENCRYPT_MODE, deskey, ivParam);  
  
        cipherByte = encrypt.doFinal(sourceBuf, 0, sourceBuf.length);  
        // ���ؼ��ܺ���ֽ�����  
        return cipherByte;  
    }  
  
    /** 
     * <li> 
     * ��������:TripleDES_CBC_Decrypt</li> <li> 
     * ��������: 
     *  
     * <pre> 
     * ������װ������DES / CBC�����㷨 
     * </pre> 
     *  
     * </li> 
     *  
     * @param sourceBuf 
     *            ��Ҫ�������ݵ��ֽ����� 
     * @param deskey 
     *            KEY ��24λ�ֽ�����ͨ��SecretKeySpec��ת�����ɡ� 
     * @param ivParam 
     *            IVƫת��������6λ�ֽ�����ͨ��IvParameterSpec��ת�����ɡ� 
     * @return ���ܺ���ֽ����� 
     * @throws Exception 
     */  
    public static byte[] TripleDES_CBC_Decrypt(byte[] sourceBuf,  
            SecretKeySpec deskey, IvParameterSpec ivParam) throws Exception {  
  
        byte[] cipherByte;  
        // ���Cipherʵ����ʹ��CBCģʽ��  
        Cipher decrypt = Cipher.getInstance("TripleDES/CBC/PKCS5Padding");  
        // ��ʼ������ʵ��������Ϊ���ܹ��ܣ���������Կ��ƫת����  
        decrypt.init(Cipher.DECRYPT_MODE, deskey, ivParam);  
  
        cipherByte = decrypt.doFinal(sourceBuf, 0, sourceBuf.length);  
        // ���ؽ��ܺ���ֽ�����  
        return cipherByte;  
    }  
  
    /** 
     * <li> 
     * ��������:DES_CBC_Encrypt</li> <li> 
     * ��������: 
     *  
     * <pre> 
     * ������װ��DES/CBC�����㷨������������ģ���ע����롣 
     * </pre> 
     *  
     * </li> 
     *  
     * @param sourceBuf 
     *            ��Ҫ�������ݵ��ֽ����顣 
     * @param deskey 
     *            KEY ��8λ�ֽ�����ͨ��SecretKeySpec��ת�����ɡ� 
     * @param ivParam 
     *            IVƫת��������8λ�ֽ�����ͨ��IvParameterSpec��ת�����ɡ� 
     * @return ���ܺ���ֽ����� 
     * @throws Exception 
     */  
    public static byte[] DES_CBC_Encrypt(byte[] sourceBuf,  
            SecretKeySpec deskey, IvParameterSpec ivParam) throws Exception {  
        byte[] cipherByte;  
        // ʹ��DES�ԳƼ����㷨��CBCģʽ����  
        Cipher encrypt = Cipher.getInstance("DES/CBC/PKCS5Padding");  
  
        encrypt.init(Cipher.ENCRYPT_MODE, deskey, ivParam);  
  
        cipherByte = encrypt.doFinal(sourceBuf, 0, sourceBuf.length);  
        // ���ؼ��ܺ���ֽ�����  
        return cipherByte;  
    }  
  
    /** 
     * <li> 
     * ��������:DES_CBC_Decrypt</li> <li> 
     * ��������: 
     *  
     * <pre> 
     * ������װ��DES/CBC�����㷨�� 
     * </pre> 
     *  
     * </li> 
     *  
     * @param sourceBuf 
     *            ��Ҫ�������ݵ��ֽ����� 
     * @param deskey 
     *            KEY ��8λ�ֽ�����ͨ��SecretKeySpec��ת�����ɡ� 
     * @param ivParam 
     *            IVƫת��������6λ�ֽ�����ͨ��IvParameterSpec��ת�����ɡ� 
     * @return ���ܺ���ֽ����� 
     * @throws Exception 
     */  
    public static byte[] DES_CBC_Decrypt(byte[] sourceBuf,  
            SecretKeySpec deskey, IvParameterSpec ivParam) throws Exception {  
  
        byte[] cipherByte;  
        // ���Cipherʵ����ʹ��CBCģʽ��  
        Cipher decrypt = Cipher.getInstance("DES/CBC/PKCS5Padding");  
        // ��ʼ������ʵ��������Ϊ���ܹ��ܣ���������Կ��ƫת����  
        decrypt.init(Cipher.DECRYPT_MODE, deskey, ivParam);  
  
        cipherByte = decrypt.doFinal(sourceBuf, 0, sourceBuf.length);  
        // ���ؽ��ܺ���ֽ�����  
        return cipherByte;  
    }  
  
    /** 
     * <li> 
     * ��������:MD5Hash</li> <li> 
     * ��������: 
     *  
     * <pre> 
     * MD5�������˼򵥵ķ�װ���������ڼӣ������ַ�����У�顣 
     * </pre> 
     *  
     * </li> 
     *  
     * @param buf 
     *            ��ҪMD5�����ֽ����顣 
     * @param offset 
     *            ����������ʼλ�á� 
     * @param length 
     *            ��Ҫ���ܵ����鳤�ȡ� 
     * @return 
     * @throws Exception 
     */  
    public static byte[] MD5Hash(byte[] buf, int offset, int length)  
            throws Exception {  
        MessageDigest md = MessageDigest.getInstance("MD5");  
        md.update(buf, offset, length);  
        return md.digest();  
    }  
  
    /** 
     * <li> 
     * ��������:byte2hex</li> <li> 
     * ��������: 
     *  
     * <pre> 
     * �ֽ�����ת��Ϊ�����Ʊ�ʾ 
     * </pre> 
     *  
     * </li> 
     *  
     * @param inStr 
     *            ��Ҫת���ֽ����顣 
     * @return �ֽ�����Ķ����Ʊ�ʾ�� 
     */  
    public static String byte2hex(byte[] inStr) {  
        String stmp;  
        StringBuffer out = new StringBuffer(inStr.length * 2);  
  
        for (int n = 0; n < inStr.length; n++) {  
            // �ֽ���"��"���㣬ȥ����λ���ֽ� 11111111  
            stmp = Integer.toHexString(inStr[n] & 0xFF);  
            if (stmp.length() == 1) {  
                // �����0��F�ĵ�λ�ַ����������0  
                out.append("0" + stmp);  
            } else {  
                out.append(stmp);  
            }  
        }  
        return out.toString();  
    }  
  
    /** 
     * <li> 
     * ��������:addMD5</li> <li> 
     * ��������: 
     *  
     * <pre> 
     * MDУ���� ��Ϸ�����ǰ16λ��MD5Hash�롣 ��MD5��֤��byte[]����������byte[]��ϵķ����� 
     * </pre> 
     *  
     * </li> 
     *  
     * @param md5Byte 
     *            �������ݵ�MD5Hash�ֽ����顣 
     * @param bodyByte 
     *            ���������ֽ����� 
     * @return ��Ϻ���ֽ����飬�ȼ������ݳ�16���ֽڡ� 
     */  
    public static byte[] addMD5(byte[] md5Byte, byte[] bodyByte) {  
        int length = bodyByte.length + md5Byte.length;  
        byte[] resutlByte = new byte[length];  
  
        // ǰ16λ��MD5Hash��  
        for (int i = 0; i < length; i++) {  
            if (i < md5Byte.length) {  
                resutlByte[i] = md5Byte[i];  
            } else {  
                resutlByte[i] = bodyByte[i - md5Byte.length];  
            }  
        }  
  
        return resutlByte;  
    }  
  
    /** 
     * <li> 
     * ��������:getKeyIV</li> <li> 
     * ��������: 
     *  
     * <pre> 
     *  
     * </pre> 
     * </li> 
     *  
     * @param encryptKey 
     * @param key 
     * @param iv 
     */  
    public static void getKeyIV(String encryptKey, byte[] key, byte[] iv) {  
        // ��ԿBase64����  
        byte[] buf = null;  
        buf = Base64.decodeBase64(encryptKey);  
        // ǰ8λΪkey  
        int i;  
        for (i = 0; i < key.length; i++) {  
            key[i] = buf[i];  
        }  
        // ��8λΪiv����  
        for (i = 0; i < iv.length; i++) {  
            iv[i] = buf[i + 8];  
        }  
    }  
      
    public static void main(String[] args) throws Exception {  
        System.out.println(encrypt("123456"));
        System.out.println(decrypt("oqGiG3w2C/s4l945xI++My4Wpv2cCyLi"));
    }  
}