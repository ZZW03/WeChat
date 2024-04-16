package com.zzw.Account.Mq.Listener;


import com.zzw.common.Const;
import jakarta.annotation.Resource;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RabbitListener(queues = Const.MQ.MQ_QUEUE_NAME_MAIL)
public class MailQueue {

    @Resource
    JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    String username;

    @RabbitHandler
    public void SendMailMessage(Map<String,Object> map){
        String mail = (String) map.get("email");
        Integer code = (Integer) map.get("code");
        String type = (String) map.get("type");
        if (type.equals("register")){
            String title = "注册验证码";
            String content = "您的验证码为："+code+"，请在5分钟内完成注册。";
            javaMailSender.send(sendMail(mail,title,content));
        }else if (type.equals("forget")){
            String title = "忘记密码的验证码";
            String content = "您的验证码为："+code+"，请在5分钟内完成注册。";
            javaMailSender.send(sendMail(mail,title,content));
        }else {
            String title = "欢迎你";
            String content = "您的验证码为："+code+"，请在5分钟内完成注册。";
            javaMailSender.send(sendMail(mail,title,content));
        }
    }

    private  SimpleMailMessage sendMail(String mail, String title, String content) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(mail);
        message.setText(content);
        message.setSubject(title);
        message.setFrom(username);
        return message;
    }
}
