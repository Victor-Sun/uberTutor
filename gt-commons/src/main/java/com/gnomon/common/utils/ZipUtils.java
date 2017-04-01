package com.gnomon.common.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.Key;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.UUID;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipFile;
import org.apache.tools.zip.ZipOutputStream;

public class ZipUtils {

	/*public static byte[] createZipFile(String source) throws Exception {
		File sourceFile = new File(source);
		List files = getSubFiles(sourceFile);

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ZipOutputStream zos = new ZipOutputStream(baos);
		
		ZipEntry ze = null;
		byte[] buf = new byte[1024];
		int readLen = 0;
		for (int i = 0; i < files.size(); i++) {
			File subFile = (File) files.get(i);

			ze = new ZipEntry(getAbsFileName(sourceFile, subFile));
			ze.setSize(subFile.length());
			ze.setTime(subFile.lastModified());

			zos.putNextEntry(ze);
			InputStream is = new BufferedInputStream(new FileInputStream(subFile));
			while ((readLen = is.read(buf, 0, 1024)) != -1) {
				zos.write(buf, 0, readLen);
			}
			is.close();
		}
		zos.close();
		return baos.toByteArray();
	}*/
	
	public static void createFile(InputStream source, String fileName) throws Exception {
		FileOutputStream fos = new FileOutputStream(fileName);
		byte[] buf = new byte[2048];
		int readLen = 0;
		while ((readLen = source.read(buf, 0, 2048)) != -1) {
			fos.write(buf, 0, readLen);
		}
		source.close();
		fos.close();
	}

	public static void zip(String source, String zipFileName) throws Exception {
		File sourceFile = new File(source);
		List files = getSubFiles(sourceFile);

		ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(zipFileName));
		
		ZipEntry ze = null;
		byte[] buf = new byte[2048];
		int readLen = 0;
		for (int i = 0; i < files.size(); i++) {
			File subFile = (File) files.get(i);

			ze = new ZipEntry(getAbsFileName(sourceFile, subFile));
			ze.setSize(subFile.length());
			ze.setTime(subFile.lastModified());

			zos.putNextEntry(ze);
			InputStream is = new BufferedInputStream(new FileInputStream(subFile));
			while ((readLen = is.read(buf, 0, 2048)) != -1) {
				zos.write(buf, 0, readLen);
			}
			is.close();
		}
		zos.close();
	}
	
	public static void zip(String source, OutputStream os) throws Exception {
		File sourceFile = new File(source);
		List files = getSubFiles(sourceFile);

		ZipOutputStream zos = new ZipOutputStream(os);
		
		ZipEntry ze = null;
		byte[] buf = new byte[2048];
		int readLen = 0;
		for (int i = 0; i < files.size(); i++) {
			File subFile = (File) files.get(i);

			ze = new ZipEntry(getAbsFileName(sourceFile, subFile));
			ze.setSize(subFile.length());
			ze.setTime(subFile.lastModified());

			zos.putNextEntry(ze);
			InputStream is = new BufferedInputStream(new FileInputStream(subFile));
			while ((readLen = is.read(buf, 0, 2048)) != -1) {
				zos.write(buf, 0, readLen);
			}
			is.close();
		}
		zos.close();
	}
	
	public static void zip(List<String> files, String zipFileName) throws Exception {
		ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(zipFileName));
		ZipEntry ze = null;
		byte[] buf = new byte[2048];
		int readLen = 0;
		for (int i = 0; i < files.size(); i++) {
			File subFile = new File(files.get(i));

			ze = new ZipEntry(subFile.getName());
			ze.setSize(subFile.length());
			ze.setTime(subFile.lastModified());

			zos.putNextEntry(ze);
			InputStream is = new BufferedInputStream(new FileInputStream(subFile));
			while ((readLen = is.read(buf, 0, 2048)) != -1) {
				zos.write(buf, 0, readLen);
			}
			is.close();
		}
		zos.close();
	}
	
	public static void unzip(String zipFileName, String target,String encoding) throws Exception {
		ZipFile zipfile = new ZipFile(zipFileName,encoding);
		Enumeration subFiles = zipfile.getEntries();
		ZipEntry zipEntry = null;
		byte[] buf = new byte[1024];
		while (subFiles.hasMoreElements()) {
			zipEntry = (ZipEntry) subFiles.nextElement();
			if (zipEntry.isDirectory()) {
				continue;
			}
			OutputStream os = new BufferedOutputStream(new FileOutputStream(getRealFileName(target, zipEntry.getName())));
			InputStream is = new BufferedInputStream(zipfile.getInputStream(zipEntry));
			int readLen = 0;
			while ((readLen = is.read(buf, 0, 1024)) != -1) {
				os.write(buf, 0, readLen);
			}
			is.close();
			os.close();
		}
		zipfile.close();
	}
	public static void unzip(String zipFileName, String target) throws Exception {
		ZipFile zipfile = new ZipFile(zipFileName);
		Enumeration subFiles = zipfile.getEntries();
		ZipEntry zipEntry = null;
		byte[] buf = new byte[1024];
		while (subFiles.hasMoreElements()) {
			zipEntry = (ZipEntry) subFiles.nextElement();
			if (zipEntry.isDirectory()) {
				continue;
			}
			OutputStream os = new BufferedOutputStream(new FileOutputStream(getRealFileName(target, zipEntry.getName())));
			InputStream is = new BufferedInputStream(zipfile.getInputStream(zipEntry));
			int readLen = 0;
			while ((readLen = is.read(buf, 0, 1024)) != -1) {
				os.write(buf, 0, readLen);
			}
			is.close();
			os.close();
		}
		zipfile.close();
	}
	
	public static void unzipFirstEntry(String zipFileName, String entryFileName) throws Exception {
		ZipFile zipfile = new ZipFile(zipFileName);
		Enumeration subFiles = zipfile.getEntries();
		ZipEntry zipEntry = null;
		byte[] buf = new byte[1024];
		while (subFiles.hasMoreElements()) {
			zipEntry = (ZipEntry) subFiles.nextElement();
			if (zipEntry.isDirectory()) {
				continue;
			}
			OutputStream os = new BufferedOutputStream(new FileOutputStream(entryFileName));
			InputStream is = new BufferedInputStream(zipfile.getInputStream(zipEntry));
			int readLen = 0;
			while ((readLen = is.read(buf, 0, 1024)) != -1) {
				os.write(buf, 0, readLen);
			}
			is.close();
			os.close();
			break;
		}
		zipfile.close();
	}
	

	private static File getRealFileName(String source, String absFileName) {
		String[] dirs = absFileName.split("/");
		File file = new File(source);
		if (dirs.length > 1) {
			for (int i = 0; i < dirs.length - 1; i++) {
				file = new File(file, dirs[i]);
			}
		}
		if (!file.exists()) {
			file.mkdirs();
		}
		file = new File(file, dirs[dirs.length - 1]);
		return file;
	}

	private static String getAbsFileName(File sourceFile, File subFile) {
		String result = subFile.getName();
		if (sourceFile.isDirectory()) {
			File sub = subFile;
			while (true) {
				sub = sub.getParentFile();
				if (sub == null) {
					break;
				}
				if (sub.equals(sourceFile)) {
					break;
				}
				else {
					result = sub.getName() + "/" + result;
				}
			}
		}
		return result;
	}

	private static List<File> getSubFiles(File source) {
		List<File> files = new ArrayList<File>();
		if (source.isDirectory()) {
			File[] tmp = source.listFiles();
			for (int i = 0; i < tmp.length; i++) {
				if (tmp[i].isFile()) {
					files.add(tmp[i]);
				}
				if (tmp[i].isDirectory()) {
					files.addAll(getSubFiles(tmp[i]));
				}
			}
		}
		else {
			files.add(source);
		}
		return files;
	}
	
	
	
	
	
	public static Key getKey(String keyPath) throws Exception {
		FileInputStream fis = new FileInputStream(keyPath);
		byte[] b = new byte[16];
		fis.read(b);
		SecretKeySpec dks = new SecretKeySpec(b, "AES");
		fis.close();
		return dks;
	}
	
	public static Key getKey(byte[] key) throws Exception {
		SecretKeySpec dks = new SecretKeySpec(key, 0, 16, "AES");
		return dks;
	}

	public static void encrypt(String srcFile, String destFile, Key privateKey) throws Exception {
		SecureRandom sr = new SecureRandom();
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		IvParameterSpec spec = new IvParameterSpec(privateKey.getEncoded());
		cipher.init(Cipher.ENCRYPT_MODE, privateKey, spec, sr);
		FileInputStream fis = new FileInputStream(srcFile);
		FileOutputStream fos = new FileOutputStream(destFile);
		byte[] b = new byte[2048];
		int byteread = 0;
		while ((byteread = fis.read(b)) != -1) {
			fos.write(cipher.doFinal(b, 0, byteread));
		}
		fos.close();
		fis.close();
	}

	public static String encryptPass(String str, String key) {
		String result = null;
		try {
			Key privateKey = getKey(key.getBytes());
			SecureRandom sr = new SecureRandom();
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			IvParameterSpec spec = new IvParameterSpec(privateKey.getEncoded());
			cipher.init(Cipher.ENCRYPT_MODE, privateKey, spec, sr);
			result = BaseEncoderUtils.shaEncode(cipher.doFinal(str.getBytes()));
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public static void encryptZip(String source, String destFile, String keyFile) throws Exception {
		Key key = getKey(keyFile);
		File temp = new File(UUID.randomUUID().toString() + ".zip");
		temp.deleteOnExit();
		zip(source, temp.getAbsolutePath());
		encrypt(temp.getAbsolutePath(), destFile, key);
		temp.delete();
	}

	public static void decrypt(String srcFile, String destFile, Key privateKey) throws Exception {
		SecureRandom sr = new SecureRandom();
		Cipher ciphers = Cipher.getInstance("AES/CBC/PKCS5Padding");
		IvParameterSpec spec = new IvParameterSpec(privateKey.getEncoded());
		ciphers.init(Cipher.DECRYPT_MODE, privateKey, spec, sr);
		FileInputStream fis = new FileInputStream(srcFile);
		FileOutputStream fos = new FileOutputStream(destFile);
		byte[] b = new byte[2064];
		int byteread = 0;
		while ((byteread = fis.read(b)) != -1) {
			fos.write(ciphers.doFinal(b, 0, byteread));
		}
		fos.close();
		fis.close();
	}

	public static void decryptUnzip(String srcFile, String target, String keyFile) throws Exception {
		File temp = new File(UUID.randomUUID().toString() + ".zip");
		temp.deleteOnExit();
		decrypt(srcFile, temp.getAbsolutePath(), getKey(keyFile));
		unzip(temp.getAbsolutePath(), target);
		temp.delete();
	}
	
	public static void createKey(String keyFile) throws Exception {
		SecureRandom sr = new SecureRandom();
        KeyGenerator kg = KeyGenerator.getInstance("AES");
        kg.init(128, sr);
        SecretKey key = kg.generateKey();
        File f = new File(keyFile);
        if (!f.getParentFile().exists()) {
        	f.getParentFile().mkdirs();
        }
        f.createNewFile();
        FileOutputStream fos = new FileOutputStream(f);
        fos.write(key.getEncoded());
	}

	
	public static void main(String[] args) throws Exception {
//		ZipUtils.createZipFile("D:\\Dumex\\setup\\dms", "D:\\Dumex\\setup\\dms\\out.zip");
//		ZipUtils.zip("D:\\Dumex\\setup\\dms\\dms.zip", "D:\\Dumex\\setup\\dms");
		
//		ZipUtils.zip("G:/ziptest/base", "G:/ziptest/base.zip");
		ZipUtils.unzip("D:\\Tmp\\1.zip", "D:\\Tmp\\exp","GBK");
		
//		ZipUtils.encryptZip("./data/2009-03-19.xml", "./data/encry.zip", "./data/2009-02-26.xml");
//		ZipUtils.decryptUnzip("./data/encry.zip", "./data/target", "./data/2009-02-26.xml");
		
//		createKey("./data/dms.dll");
//		String a = ZipUtils.encryptPass("1", "winchannel!@#$%^");
//		System.out.println(a);
//		System.out.println(a.length());
	}
	
}
