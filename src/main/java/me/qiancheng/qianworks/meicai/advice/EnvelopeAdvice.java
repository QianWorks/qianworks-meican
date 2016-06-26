package me.qiancheng.qianworks.meicai.advice;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.simplejavamail.email.Email;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Created by iamya on 6/26/2016.
 */
@Aspect
@Component
public class EnvelopeAdvice {


    @Value("${qianworks.meican.mail.from}")
    private String from;

    @Value("${qianworks.meican.mail.password}")
    private String password;

    @Value("${qianworks.meican.mail.nickname}")
    private String nickname;

    private static final Logger LOG = LoggerFactory.getLogger(EnvelopeAdvice.class);

    @Pointcut("execution(* org.simplejavamail.mailer.Mailer.sendMail(..))  && args(email,..)")
    public void sendMailPointcut(Email email) {
    }

    @Pointcut("execution(* me.qiancheng.qianworks.meicai.service.MailService.send(..))")
    public void mailServicePointcut() {
    }

    @Before(value = "mailServicePointcut() && args(to,subject,content)")
    public void before(JoinPoint jp, String to, String subject, String content) {
        LOG.debug("sending mail..." + to);
    }


    @Before(value = "sendMailPointcut(email)")
    public void beforeSendMail(Email email) throws Throwable {
        email.setFromAddress("千橙工坊", from);
        email.setTextHTML(email.getTextHTML() + "<br>-----------</br>千橙工坊");
    }

    //    @After("execution(* me.qiancheng.qianworks.meicai.service.*Service.sendMail(..))")
    @After("execution(* me.qiancheng.qianworks.meicai.service.MailService.send(..))")
    public void after2(JoinPoint jp) {
        LOG.warn("after mail sending. ");
    }

}
