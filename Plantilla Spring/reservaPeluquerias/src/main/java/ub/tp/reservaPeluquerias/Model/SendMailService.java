package ub.tp.reservaPeluquerias.Model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class SendMailService 
{

    @Autowired
    private JavaMailSender javaMailSender;

    public void sendEmail(String receiver, String body, String subject) 
    {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setTo(receiver);
        simpleMailMessage.setText(body);
        simpleMailMessage.setSubject(subject);
        simpleMailMessage.setFrom("MailService");

        javaMailSender.send(simpleMailMessage);
    }
    
}