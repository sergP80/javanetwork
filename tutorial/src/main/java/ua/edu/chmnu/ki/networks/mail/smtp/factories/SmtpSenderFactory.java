package ua.edu.chmnu.ki.networks.mail.smtp.factories;

import ua.edu.chmnu.ki.networks.mail.smtp.config.SmtpSenderConfig;
import ua.edu.chmnu.ki.networks.mail.smtp.senders.SmtpSender;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public interface SmtpSenderFactory {
    static SmtpSenderFactory createFactory(EmailType emailType) {
        Map<EmailType, SmtpSenderFactory> map = new HashMap<>();
        map.put(EmailType.TEXT, new TextSmtpSenderFactoryImpl());
        map.put(EmailType.HTML, new HtmlSmtpSenderFactoryImpl());
        map.put(EmailType.HTML_MULTI_PART, new HtmlMultipartSmtpSenderFactoryImpl());
        map.put(EmailType.TEXT_ATTACHMENT, new AttachmentSmtpSenderFactoryImpl());
        if (!map.containsKey(emailType)) {
            throw new IllegalArgumentException("Email type " + emailType + " has not been supported yet");
        }
        return map.get(emailType);
    }

    SmtpSender create(SmtpSenderConfig params) throws IOException, MessagingException;
}
