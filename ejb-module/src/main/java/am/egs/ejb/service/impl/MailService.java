package am.egs.ejb.service.impl;

import lombok.NoArgsConstructor;
import javax.ejb.Stateless;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

@NoArgsConstructor
@Stateless
public class MailService {

    private static final String FROM = "library162@gmail.com";
    private static final String HOST = "smtp.gmail.com";
    private static final String PORT = "587";
    private static final String PASSWORD = "199798se";
    private static final String SUBJECT = "Library user verification";
    private static final String DEFAULT_CONTENT = "Please, use this code for verification\n";

    public void sendMail(final String recipientEmail,final String verificationCode) {
        Properties properties = new Properties();
        properties.put("mail.smtp.host", HOST);
        properties.put("mail.smtp.port", PORT);
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");

        Session session = Session.getInstance(properties,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(FROM, PASSWORD);
                    }
                });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(FROM));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(recipientEmail));
            message.setSubject(SUBJECT);
            message.setContent(DEFAULT_CONTENT + verificationCode, "text/plain");

            Transport.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
