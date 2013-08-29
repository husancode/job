package yofoto.issue.job;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import yofoto.issue.pojo.IssueAlert;
import yofoto.issue.service.impl.IssueAlertService;



/**
 * @author husan
 * @Date 2013-3-22
 * @description
 */
@Component
@Lazy(value=false)
public class ScheduleJob {
	
	private static final int day = 1;
	private ExecutorService emailExec = Executors.newFixedThreadPool(2);
	
	@Autowired
	private IssueAlertService issueAlertService;
	
	//@Scheduled(cron="0/360 * *  * * ? ")
	//@Scheduled(cron="0/60 * * * * ? ")
	public void work(){
		System.out.println("work start");
		issueAlertService.updateIssueAlert(day);
		mediaEmailNotify();
		System.out.println("work end");
	}
	//邮件通知
	private void mediaEmailNotify(){
		List<IssueAlert> issueAlertList = issueAlertService.getEmailList();
		ArrayList<Future<String>> results = new ArrayList<Future<String>>();
		for(IssueAlert issueAlert : issueAlertList){
			results.add(emailExec.submit(new MailSendJob(issueAlert)));
		}
		StringBuilder ids = new StringBuilder(100);
		for(Future<String> fs : results){
			try {
				String id = fs.get();
				if(id!=null)
					ids.append(id).append(",");
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		//若回调更新出错，则可能导致邮件重复发送
		System.out.println(ids);
		if(ids.length()>1){
			ids.deleteCharAt(ids.length()-1);
			issueAlertService.emailFlagUpdate(ids.toString());
		}
		
	}

	

}
