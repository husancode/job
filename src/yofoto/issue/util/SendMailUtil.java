package yofoto.issue.util;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
 
import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.*;

public class SendMailUtil {
//	   public static String send(String host, String mail, String password,
//	            String from, List<String> to, String title, String content,
//	            String[] fileNames) {

    public static void send(final List<String> to, final String title, final String content,final List<String> fileNames) {
    	
		Thread thread = new Thread(new Runnable() {
			
			public void run() {
				
				// TODO Auto-generated method stub
				boolean flag = false;
				int count = 0;
				//发送邮件容错：连接邮件服务器可能超时，如果失败则重发，最多尝试3次
				while(!flag && count<3){
			        String result = "邮件发送成功";
			        // Get system properties
			        Properties props = System.getProperties();
			        //SendMailUtil.send("smtp.yofoto.cn","hukj@yofoto.cn","123456","hukj@yofoto.cn",to,"中国人好","徐益军来了",fileNames);
			        // Setup mail server
			        props.put("mail.smtp.host", "smtp.yofoto.cn");
			 
			        // Get session
			        props.put("mail.smtp.auth", "true"); // 这样才能通过验证
			 
			        MyAuthenticator myauth = new MyAuthenticator("hukj@yofoto.cn", "123456");
			        Session session = Session.getDefaultInstance(props, myauth);
			        //session.setDebug(true);
			 
			        for (int i = 0; i < to.size(); i++) {
			            // Set the to address
			 
			            // Define message
			            MimeMessage message = new MimeMessage(session);
			            // Set the from address
			            try {
			                message.setFrom(new InternetAddress("hukj@yofoto.cn"));
			            } catch (AddressException e) {
			                // TODO Auto-generated catch block
			                // e.printStackTrace();
			       
			            } catch (MessagingException e) {
			                // TODO Auto-generated catch block
			                // e.printStackTrace();
			       
			            }
			 
			            // Set the subject
			            try {
			                message.setSubject(title);
			            } catch (MessagingException e) {
			                // TODO Auto-generated catch block
			                // e.printStackTrace();
			 
			                result = "设置邮件主题错误";
			            }
			 
			            // Set the content
			            try {
			            	  BodyPart mdp = new MimeBodyPart();// 新建一个存放信件内容的BodyPart对象
			                  mdp.setContent(content, "text/html;charset=utf-8");// 给BodyPart对象设置内容和格式/编码方式
			                  Multipart mm = new MimeMultipart();// 新建一个MimeMultipart对象用来存放BodyPart对
			                  // 象(事实上可以存放多个)
			                  mm.addBodyPart(mdp);// 将BodyPart加入到MimeMultipart对象中(可以加入多个BodyPart)
			                if (fileNames != null && fileNames.size() > 0) {
			                  
			                    // 把mm作为消息对象的内容
			                    MimeBodyPart filePart;
			                    FileDataSource filedatasource;
			                    // 逐个加入附件
			                    for (int j = 0; j < fileNames.size(); j++) {
			                        filePart = new MimeBodyPart();
			                        filedatasource = new FileDataSource(fileNames.get(j));
			                        filePart
			                                .setDataHandler(new DataHandler(filedatasource));
			                        try {
			                            filePart.setFileName(MimeUtility.encodeText(skipFirstDot(filedatasource.getName())));
			                        } catch (UnsupportedEncodingException e) {
			                            e.printStackTrace();
			                        }   
			 
			                        mm.addBodyPart(filePart);
			                    }
			                }
			                
			                message.setContent(mm);
			 
			 
			            } catch (MessagingException e) {
			                // TODO Auto-generated catch block
			                // e.printStackTrace();
			            	//e.printStackTrace();
			                result = "设置邮件内容错误";
			            }
			            try {
			                message.addRecipient(Message.RecipientType.TO,
			                        new InternetAddress(to.get(i)));
			            } catch (AddressException e) {
			                // TODO Auto-generated catch block
			            	//e.printStackTrace();
			                result = "邮件地址解析失败";
			            } catch (MessagingException e) {
			                // TODO Auto-generated catch block
			            	//e.printStackTrace();
			                result = "邮件信息错误";
			            }
			            try {
			                message.saveChanges();
			            } catch (MessagingException e) {
			                // TODO Auto-generated catch block
			                // e.printStackTrace();
			            	//e.printStackTrace();
			                result = "邮件发送失败";
			            }
			            try {
			                Transport.send(message);
			            } catch (MessagingException e) {
			                // TODO Auto-generated catch block
			                // e.printStackTrace();
			            	//e.printStackTrace();
			                result = "邮件发送失败";
			            }
			        }
			 

			        
			        if("邮件发送成功".equalsIgnoreCase(result)){
				        //return result;
				        System.out.println("邮件发送结果:"+result);
			        	flag=true;
			        }
			        else{
			        	flag = false;
			        }
					count++;
					
					try {
						Thread.sleep(600);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			}
		}
		}
			);
		
		thread.start();
    }
    
    public static String skipFirstDot(String filename){
    	int firstI = filename.indexOf(".");
    	String skiped = filename.substring(firstI+1, filename.length());
    	
    	return skiped;
    }
    
	public enum SendTypeEnum {
		TO(0, "发件方式 - 普通发送"), CS(1, "发件方式 - 抄送"), MCS(2, "发件方式 - 密件抄送");
		private int type;
		private String description;

		SendTypeEnum(int type, String desc) {
			this.type = type;
			this.description = desc;
		}

		public int intValue() {
			return this.type;
		}

		public String getDescription() {
			return this.description;
		}
	}

    
	public static void main(String[] args) {

			List<String> to = new ArrayList<String>();
			to.add("727424409@qq.com");
			
            String[] fileNames = new String[]{"c:/中国.txt","c:/2.txt"};
			//SendMailUtil.send(to,"中国人好","徐益军来了",fileNames);
//			try {
//				mail.setFileAttachment("c:/中国.txt");// 本地附件
//				mail.setURLAttachment("http://static.tieba.baidu.com/tb/img/2010120801.jpg");// 网络文件
//				// mail.setURLAttachment("http://static.tieba.baidu.com/tb/editor/images/jd/j_0017.gif");
//				mail.setURLAttachment("http://tieba.baidu.com");// 引用网页
//			} catch (Exception e) {
//				System.out.println("附件发送异常");
//			}
//			mail.sendBatch();
//		} catch (AddressException e) {
//			e.printStackTrace();
//		} catch (MessagingException e) {
//			e.printStackTrace();
//		}
	}

}