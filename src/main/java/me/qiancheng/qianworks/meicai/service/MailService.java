package me.qiancheng.qianworks.meicai.service;

import org.simplejavamail.email.Email;
import org.simplejavamail.mailer.Mailer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import javax.mail.Message;
import java.util.List;

/**
 * Created by iamya on 6/25/2016.
 */
@Service
@PropertySource({"classpath:mail.properties"})
public class MailService {

    @Value("${qianworks.meican.mail.from}")
    private String from;

    @Value("${qianworks.meican.mail.password}")
    private String password;

    @Value("${qianworks.meican.mail.nickname}")
    private String nickname;

    @Autowired
    private Mailer mailSender;

    @Deprecated
    protected void setup(Email email){
        if(null!=email){
            synchronized (email){
                if(null!=email){
                    email.setFromAddress(nickname, from);
                }
            }
        }
    }

    public synchronized void send(String to,String subject,String content){
        Email email = new Email();
        email.addRecipient(getNickname(to), to, Message.RecipientType.TO);
        email.setSubject(subject);
        email.setTextHTML(content);
        sendMail(email);
    }

    public synchronized void send(List<String> toList, String subject, String content){
        Email email = new Email();
        for (String to : toList) {
            email.addRecipient(getNickname(to), to, Message.RecipientType.TO);
        }
        email.setSubject(subject);
        email.setTextHTML(content);
        sendMail(email);
    }

    protected void sendMail(Email email){
        mailSender.sendMail(email);
    }

    protected String getNickname(String email){
        return null;
    }
}
