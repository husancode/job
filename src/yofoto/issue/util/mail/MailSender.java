package yofoto.issue.util.mail;

/**
 * @author husan
 * @Date 2012-11-17
 * @description 邮件发送
 */
public class MailSender {
	
	public static void send(final String toAddress, final String title, final String content){
		
		Thread thread = new Thread(new Runnable() {
			
			public void run() {
				// TODO Auto-generated method stub
				boolean flag = false;
				int count = 0;
				//发送邮件容错：连接邮件服务器可能超时，如果失败则重发，最多尝试3次
				while(!flag && count<3){
					
					MailSenderInfo mailInfo = new MailSenderInfo(); 
					mailInfo.setMailServerHost("smtp.yofoto.cn"); 
					mailInfo.setMailServerPort("25"); 
					mailInfo.setValidate(true); 
					
					mailInfo.setUserName("hukj@yofoto.cn"); 
					mailInfo.setPassword("123456");//您的邮箱密码 
					mailInfo.setFromAddress("hukj@yofoto.cn"); 
					
					mailInfo.setToAddress(toAddress); 
					mailInfo.setSubject(title); 
					mailInfo.setContent(content); 
				        //这个类主要来发送邮件
					SimpleMailSender sms = new SimpleMailSender();
				   flag = sms.sendHtmlMail(mailInfo);//发送文体格式
				   count++;
				}
				
			}
		});
		
		thread.start();
	}

}
