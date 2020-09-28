package ua.edu.chmnu.ki.networks.mail.smtp.factories;

import ua.edu.chmnu.ki.networks.mail.smtp.SmtpSenderParams;
import ua.edu.chmnu.ki.networks.mail.smtp.senders.HtmlMultipartSmtpSender;
import ua.edu.chmnu.ki.networks.mail.smtp.senders.SmtpSender;

import javax.mail.MessagingException;
import java.io.IOException;

public class HtmlMultipartSmtpSenderFactoryImpl extends AbstractSmtpSenderFactoryImpl {
    @Override
    public SmtpSender create(SmtpSenderParams params) throws IOException, MessagingException {
        HtmlMultipartSmtpSender smtpSender = new HtmlMultipartSmtpSender(params.getSmtpUser(), params.getSmtpPassword())
                .withTemplate(params.getTemplateName())
                .withAttachments(params.getAttachments());
        return super.setup(smtpSender, params);
    }
}
