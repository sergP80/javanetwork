package ua.edu.chmnu.ki.networks.mail.smtp.factories;

import ua.edu.chmnu.ki.networks.mail.smtp.config.SmtpSenderConfig;
import ua.edu.chmnu.ki.networks.mail.smtp.senders.SmtpSender;
import ua.edu.chmnu.ki.networks.mail.smtp.senders.TextSmtpSender;

import javax.mail.MessagingException;
import java.io.IOException;

public class TextSmtpSenderFactoryImpl extends AbstractSmtpSenderFactoryImpl {
    @Override
    public SmtpSender create(SmtpSenderConfig params) throws IOException, MessagingException {
        TextSmtpSender smtpSender = new TextSmtpSender(params.getSmtpUser(), params.getSmtpPassword())
                .withText(params.getText());
        return super.setup(smtpSender, params);
    }
}
