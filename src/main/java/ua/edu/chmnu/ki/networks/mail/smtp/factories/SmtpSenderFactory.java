package ua.edu.chmnu.ki.networks.mail.smtp.factories;

import ua.edu.chmnu.ki.networks.mail.smtp.SmtpSenderParams;
import ua.edu.chmnu.ki.networks.mail.smtp.senders.SmtpSender;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public interface SmtpSenderFactory {
    SmtpSender create(SmtpSenderParams params) throws IOException, MessagingException;

    static SmtpSenderFactory createFactory(EmailType emailType) {
        Map<EmailType, SmtpSenderFactory> map = new HashMap<>();
        map.put(EmailType.TEXT, new TextSmtpSenderFactoryImpl());
        map.put(EmailType.HTML, new HtmlSmtpSenderFactoryImpl());
        map.put(EmailType.HTML_MULTI_PART, new HtmlMultipartSmtpSenderFactoryImpl());
        if (!map.containsKey(emailType)) {
            throw new IllegalArgumentException("Email type " + emailType + " has not been supported yet");
        }
        return map.get(emailType);
    }
}
