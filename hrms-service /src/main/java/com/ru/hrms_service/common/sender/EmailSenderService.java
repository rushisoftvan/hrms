package com.ru.hrms_service.common.sender;

import com.ru.hrms_service.common.models.MailDto;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;


@Component
@RequiredArgsConstructor
@Slf4j
public class EmailSenderService implements NotificationSender {


    private final JavaMailSender javaMailSender;
    private final SpringTemplateEngine templateEngine;


    @Override
    public void sendNotification(Object mailDto) {


        try {

            if (mailDto instanceof MailDto mail) {

                if (emailSendIsDisbaled()) {
                    log.warn("Email will not be send because email send feature is disabled");
                    return;
                }
                MimeMessage mimeMessage = javaMailSender.createMimeMessage();
                MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);

                mimeMessageHelper.setTo(mail.toMail());
                mimeMessageHelper.setSubject(mail.subject());

                Context context = new Context();
                context.setVariables(mail.props());

                System.out.println("mail.templateFileName() = " + mail.templateFileName());
                String emailBody = templateEngine.process(mail.templateFileName(), context);

                mimeMessageHelper.setText(emailBody, true);

                javaMailSender.send(mimeMessage);
                log.info("Email sent successfully");

            }

        } catch (MessagingException e) {
            throw new RuntimeException(String.format("Exception occurred while sending %s",
                    mailDto instanceof MailDto ? ((MailDto) mailDto).templateFileName() : "Unknown Template"), e);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
    private boolean emailSendIsDisbaled() {
        // return !appProps.emailSendEnabled();
        return false;
    }
}



