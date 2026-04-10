package ua.edu.chmnu.ki.networks.mail.smtp.senders;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class TextSmtpSender extends AbstractSmtpSender {
    public TextSmtpSender(Session session) {
        super(session);
    }

    public TextSmtpSender(String smtpUser, String smtpPassword) throws IOException {
        super(smtpUser, smtpPassword);
    }

    public TextSmtpSender withText(String text) throws MessagingException {
        MimeMessage mimeMessage = getMimeMessage();
        mimeMessage.setText(text, StandardCharsets.UTF_8.name());
        return this;
    }
}
