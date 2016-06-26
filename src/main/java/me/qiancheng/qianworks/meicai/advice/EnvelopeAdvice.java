package me.qiancheng.qianworks.meicai.advice;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
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

    @Pointcut("execution(* me.qiancheng.qianworks.meicai.service.MailService.*(..))  && args(email,..)")
    public void sendMailPointcut(Email email){ }

    @Pointcut("execution(* me.qiancheng.qianworks.meicai.service.MailService.send(..))")
    public void mailServicePointcut(){ }

//    @Before(value="mailServicePointcut() && args(to,subject,content)")
    public void before(JoinPoint jp,String to,String subject,String content){
        LOG.debug("sending mail..."+to);
    }


//    @Around(value="sendMailPointcut(email)")
    public void beforeSendMail(ProceedingJoinPoint point, Email email) throws Throwable {
        email.setFromAddress(nickname, from);
        email.setTextHTML("-----------\n千橙工坊");
        LOG.debug("----------------");
        point.proceed();
        LOG.warn(":::::::beforeSendMail ");
    }

//    @After("execution(* me.qiancheng.qianworks.meicai.service.MailService.send(..))")
    @After("within(me.qiancheng.qianworks.meicai.service..*)")
    public void after(JoinPoint jp){
        LOG.warn("after1111111111 mail sending. ");
    }

//    @After("execution(* me.qiancheng.qianworks.meicai.service.MailService.send(..))")
    @After("execution(* me.qiancheng.qianworks.meicai.service.*Service.sendMail(..))")
    public void after2(JoinPoint jp){
        LOG.warn("after mail sending. ");
    }

}
