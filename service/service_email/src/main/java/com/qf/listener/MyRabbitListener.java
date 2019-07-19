package com.qf.listener;

import com.qf.entity.Email;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * FileName: MyRabbitMQListenner.java
 * Desc:
 *
 * @author gf
 * @version V1.0
 * @Date 2019/7/19 19:39
 */
@Component
public class MyRabbitListener {

    // 创建线程池
    private ExecutorService executorService = Executors.newFixedThreadPool(5);

    @Autowired
    private JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String from;

    @RabbitListener(queues = "email_queue")
    public void emailQueue(Email email){
        executorService.submit(() -> {

            // 发送邮件
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);

            try {
                messageHelper.setFrom(from);
                messageHelper.setTo(email.getEmailTo());
                messageHelper.setSubject(email.getTopic());
                messageHelper.setText(email.getContent(), true);
                messageHelper.setSentDate(new Date());
                // 发送
                javaMailSender.send(mimeMessage);
            } catch (MessagingException e) {
                e.printStackTrace();
            }

        });
    }


}
