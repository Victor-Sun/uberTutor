package com.gnomon.common.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class FileUtils {

	private static final int BUFFER_SIZE = 16 * 1024;

	// 把源文件对象复制成目标文件对象
	public static void copy(File src, File dst) {
		InputStream in = null;
		OutputStream out = null;
		try {
			in = new BufferedInputStream(new FileInputStream(src), BUFFER_SIZE);
			out = new BufferedOutputStream(new FileOutputStream(dst),
					BUFFER_SIZE);
			byte[] buffer = new byte[BUFFER_SIZE];
			int len = 0;
			while ((len = in.read(buffer)) > 0) {
				out.write(buffer, 0, len);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null != in) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
					throw new RuntimeException(e);
				}
			}
			if (null != out) {
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
					throw new RuntimeException(e);
				}
			}
		}
	}

	/**
	 * 文件转化为字节数组
	 * 
	 * @param f
	 * @return
	 */
	public static byte[] getBytesFromFile(File f) {
		if (f == null)
			return null;
		try {
			FileInputStream stream = new FileInputStream(f);
			ByteArrayOutputStream out = new ByteArrayOutputStream(1000);
			byte[] b = new byte[1000];
			int n;
			while ((n = stream.read(b)) != -1) {
				out.write(b, 0, n);
			}
			stream.close();
			out.close();
			return out.toByteArray();
		} catch (IOException e) {
		}
		return null;
	}
	
    
   public static void deleteDirectory(String dest)
    {
        File f = new File(dest);
        if (f.exists())
        {
            if (f.isDirectory())
            {
                File[] fs = f.listFiles();
                if (fs.length > 0)
                {
                    for (File file : fs)
                    {
                        deleteDirectory(file.getAbsolutePath());
                    }
                }
            }
            f.delete();
        }
    }
   
   public static String saveTmpFile(File file) {
		String fileName = CommonUtils.getUUID();
		File f = new File(getTmpDir(),fileName);
		FileInputStream fis;
		try {
			fis = new FileInputStream(file);
			FileOutputStream fos = new FileOutputStream(f);
			while(fis.available()>0){
				fos.write(fis.read());
			}
			fos.close();
			fis.close();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return fileName;
	}
   
   public static void deleteTmpFile(String fileName){
		File f = new File(getTmpDir(),fileName);
		f.delete();
   }
   
   public static File getTmpFile(String fileName){
		File f = new File(getTmpDir(),fileName);
		return f;
	}
	
   public static File getTmpDir(){
		File tmpDir = new File(System.getProperty("java.io.tmpdir"));
		return tmpDir;
	}


}
