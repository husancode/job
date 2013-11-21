package yofoto.issue.controller;

import java.io.File;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;

import yofoto.issue.pojo.Upload;
import yofoto.issue.service.impl.UploadService;
import yofoto.issue.util.FileUtil;

/**
 * @author husan
 * @Date 2013-10-24
 * @description
 */
public class FileUpAction extends BaseAction{
	
	private File upload;
	private String uploadFileName;
	private String uploadContentType;
	
	@Autowired
	private UploadService uploadService;
	
	@Override
	public String execute() throws Exception {
		// TODO Auto-generated method stub
		
		Upload up = FileUtil.fileUpload(upload,uploadFileName,uploadContentType);
		up.setTid(tid);
		if(up!=null)
		uploadService.save(up);
		
		ServletActionContext.getResponse().sendRedirect("index");
		
		return null;
	}
	
	public String downLoad() throws Exception{
		String id = ServletActionContext.getRequest().getParameter("id");
		
		Upload upload = uploadService.get(FileUtil.StringToInt(id));
		File file = FileUtil.getFile(upload.getUploadName());
		HttpServletResponse response = ServletActionContext.getResponse();
		
		response.addHeader("Content-Disposition" , "attachment;filename="+new String(upload.getFileName().getBytes("UTF-8"),"ISO8859_1"));  
        response.addHeader("Content-Length",""+file.length());
        response.setContentType("application/octet-stream");
		FileUtil.fileDown(file, response.getOutputStream());
		return null;
	}
	
	

	public File getUpload() {
		return upload;
	}

	public void setUpload(File upload) {
		this.upload = upload;
	}

	public String getUploadFileName() {
		return uploadFileName;
	}

	public void setUploadFileName(String uploadFileName) {
		this.uploadFileName = uploadFileName;
	}

	public String getUploadContentType() {
		return uploadContentType;
	}

	public void setUploadContentType(String uploadContentType) {
		this.uploadContentType = uploadContentType;
	}
	
	
	

}
