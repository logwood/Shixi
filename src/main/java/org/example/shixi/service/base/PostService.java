package org.example.shixi.service.base;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.example.shixi.controller.RestResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;

@Service
public class PostService {
    @Autowired
    private JavaMailSender javaMailSender;
    @Value("${spring.mail.username}")
    private String username;
    public RestResponse<String> sendSimpleMail(String to, String subject, String text) {
        try {
            SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
            // 发件人
            simpleMailMessage.setFrom(username);
            // 收件人
            simpleMailMessage.setTo(to);
            // 邮件主题
            simpleMailMessage.setSubject(subject);
            // 邮件内容
            simpleMailMessage.setText(text);
            javaMailSender.send(simpleMailMessage);
        }catch (Exception e){
            return RestResponse.badRequest(e.getMessage());
        }
        return RestResponse.queryResponse(text);
    }
    public ResponseEntity<RestResponse<String>> sendMimeMail(MultipartFile[] multipartFiles, String to, String subject, String text) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
            helper.setFrom(username);
            helper.setTo(to);
            helper.setSubject(subject);
            // 设置邮件内容，第二个参数设置是否支持 text/html 类型
            helper.setText(text, true);
            helper.addInline("logo", new ClassPathResource("img/logo.jpg"));
            Arrays.stream(multipartFiles).forEach(multipartFile -> {
                try {
                    helper.addAttachment(multipartFile.getName(),multipartFile);
                } catch (MessagingException e) {
                    e.printStackTrace();
                }
            });
            javaMailSender.send(mimeMessage);
            return ResponseEntity.status(HttpStatus.CREATED).body(RestResponse.ok(subject,"成功"));
        } catch (MessagingException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(RestResponse.fail(e.getMessage()));
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.CREATED).body(RestResponse.fail(e.getMessage()));
        }
    }
}
