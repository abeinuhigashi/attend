package slim3.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import slim3.model.MailQueue;
import slim3.model.MailSendHistory;
import slim3.util.AttendDateUtil;
import slim3.util.SettingPropertyUtil;

import com.google.appengine.api.mail.MailService.Message;
import com.google.appengine.api.mail.MailServiceFactory;

public class MailSendService {

    private MailSendHistoryService meilSendHistoryService = new MailSendHistoryService();
    
    static final String userAdminAddr = SettingPropertyUtil.getProperty("mailAddr.user.admin");
    static final String systemAdminAddr = SettingPropertyUtil.getProperty("mailAddr.system.admin");
    static final String mailingListAddr = SettingPropertyUtil.getProperty("mailAddr.user.all");    
    
    private Logger logger = Logger.getLogger(this.getClass().getName());

    /**
     * メール送信Queueからメールを送信します。
     * 
     * @param queueList
     */
    public void sendFromQueue(List<MailQueue> queueList) {

        for(MailQueue queue : queueList){
            String subject = queue.getHeader();
            List<String> recipients= queue.getTo();
            String msgBody = queue.getContent();
            sendMail(subject, msgBody, recipients);
        }
    }

    /**
     * 管理者にメールを送信します
     * 
     * @param subject
     * @param body
     */
    public void sendMailToSystemAdmin(String subject, String body) {
        List<String> toAddressList = new ArrayList<String>();
        toAddressList.add(systemAdminAddr);
        sendMail(subject, body, toAddressList);
    }
    
    /**
     * マネージャーにメールを送信します
     * 
     * @param subject
     * @param body
     */
    public void sendMailToUserAdmin(String subject, String body) {
        List<String> toAddressList = new ArrayList<String>();
        toAddressList.add(userAdminAddr);
        sendMail(subject, body, toAddressList);
    }    

    /**
     * MLにメールを送信します。
     * 
     * @param subject
     * @param body
     */
    public void sendMailToML(String subject, String body) {
        List<String> toAddressList = new ArrayList<String>();
        toAddressList.add(mailingListAddr);
        sendMail(subject, body, toAddressList);
    }

    /**
     * メールを送信します
     * 
     * @param subject 件名
     * @param body 内容
     * @param toAddress 受信者リスト
     */
    public void sendMail(String subject, String body, List<String> toAddress) {
        
        Message msg = new Message();
        msg.setSender(userAdminAddr);
        msg.setTo(toAddress);
        msg.setSubject(subject);
        msg.setTextBody(body);
        
        try {
            MailServiceFactory.getMailService().send(msg);
            registMailHistory(subject, body, toAddress);
        } catch (IOException e) {
            logger.warning("Exception has occered.");
            logger.warning(e.getMessage());
        }
    }

    /**
     * メール送信履歴を保存します
     * 
     * @param subject
     * @param body
     * @param recipients
     */
    private void registMailHistory(String subject, String body, List<String> recipients){

        MailSendHistory history = new MailSendHistory();
        history.setHeader(subject);
        history.setContent(body);
        history.setTo(recipients);
        history.setSentDate(AttendDateUtil.getCurrentDate());

        meilSendHistoryService.regist(history);

    }
}
