package ua.edu.chmnu.ki.networks.mail.smtp.factories;

import ua.edu.chmnu.ki.networks.mail.smtp.config.SmtpSenderConfig;
import ua.edu.chmnu.ki.networks.mail.smtp.senders.AttachmentSmtpSender;
import ua.edu.chmnu.ki.networks.mail.smtp.senders.SmtpSender;

import javax.mail.MessagingException;
import java.io.IOException;

public class AttachmentSmtpSenderFactoryImpl extends AbstractSmtpSenderFactoryImpl {
    @Override
    public SmtpSender create(SmtpSenderConfig params) throws IOException, MessagingException {
        AttachmentSmtpSender smtpSender = new AttachmentSmtpSender(params.getSmtpUser(), params.getSmtpPassword())
                .withText(params.getText())
                .withAttachments(params.getAttachments());
        return super.setup(smtpSender, params);
    }
}
