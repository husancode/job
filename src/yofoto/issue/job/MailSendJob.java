package yofoto.issue.job;

import java.util.concurrent.Callable;

import org.springframework.beans.factory.annotation.Autowired;

import yofoto.issue.pojo.IssueAlert;
import yofoto.issue.util.mail.MailSenderInfo;
import yofoto.issue.util.mail.SimpleMailSender;



/**
 * @author husan
 * @Date 2013-3-23
 * @description
 */
public class MailSendJob implements Callable<String>{
	private MailSenderInfo mailInfo; 
	public MailSendJob(IssueAlert issueAlert){
		mailInfo = new MailSenderInfo(); 
		mailInfo.setMailServerHost("smtp.yofoto.cn"); 
		mailInfo.setMailServerPort("25"); 
		mailInfo.setValidate(true); 
		mailInfo.setUserName("hukj@yofoto.cn"); 
		mailInfo.setPassword("123456");//您的邮箱密码 
		mailInfo.setFromAddress("hukj@yofoto.cn");
		mailInfo.setToAddress(issueAlert.getCompleter().getEmail()); 
		mailInfo.setSubject("你在ees有任务急需完成"); 
		mailInfo.setContent(issueAlert.getInfo()); 
		mailInfo.setRet(String.valueOf(issueAlert.getId()));
	}
	
	public String call() throws Exception {
		// TODO Auto-generated method stub
		boolean flag = false;
		int count = 0;
		while(!flag && count<3){
			SimpleMailSender sms = new SimpleMailSender();
			flag = sms.sendHtmlMail(mailInfo);//发送文体格式
			//flag = true;
			count++;
		}
		
		if(flag)
			return mailInfo.getRet();
		return null;
	}
	

}
