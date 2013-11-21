package yofoto.issue.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.channels.FileChannel;

import org.apache.struts2.ServletActionContext;

import yofoto.issue.pojo.Upload;

/**
 * @author husan
 * @Date 2013-10-24
 * @description
 */
public class FileUtil {
	
	public static final String BASE = "/upload/";
	
	public static void  fileDown(File file, OutputStream out){
		try {
			BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));
			
			BufferedOutputStream bos = new BufferedOutputStream(out);
			
			byte[] buffer = new byte[4*1024];
			int i = -1;
			while((i=bis.read(buffer))>0){
				bos.write(buffer,0,i);
			}
			bos.flush();
			bos.close();
			bis.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	
	public static int StringToInt(String str){
		if(str!=null && !"".equals(str)){
			return Integer.parseInt(str);
		}
		return 0;
	}
	
	public static File getFile(String name){
		String directory =  ServletActionContext.getServletContext().getRealPath(BASE);
		
		File file = new File(directory,name);
		if(file.exists())
			return file;
		return null;
	}
	
	public static Upload fileUpload(File file,String fileName,String contentType){
		Upload upload = null;
		FileChannel readChannel = null;
		FileChannel writeChannel = null;
		try {
			FileInputStream fis = new FileInputStream(file);
			readChannel = fis.getChannel();
			String directory =  ServletActionContext.getServletContext().getRealPath(BASE);
			File directoryFile = new File(directory);
			if(!directoryFile.exists()){
				directoryFile.mkdir();
			}
			String uploadName =  fileName;
			//System.out.println(directory);
			File target = new File(directory,uploadName);
			writeChannel = new FileOutputStream(target).getChannel();
			writeChannel.transferFrom(readChannel, 0, readChannel.size());
			
			upload = new Upload();
			upload.setFileName(fileName);
			upload.setUploadName(uploadName);
			//writeChannel.close();
			//readChannel.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			if(writeChannel!=null)
				try {
					writeChannel.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			if(readChannel != null)
				try {
					readChannel.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
		return upload;
		
	}
	
	

}
