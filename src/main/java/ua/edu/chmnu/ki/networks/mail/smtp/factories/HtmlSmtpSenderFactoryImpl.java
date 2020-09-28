package ua.edu.chmnu.ki.networks.mail.smtp.factories;

import ua.edu.chmnu.ki.networks.mail.smtp.SmtpSenderParams;
import ua.edu.chmnu.ki.networks.mail.smtp.senders.HtmlSmtpSender;
import ua.edu.chmnu.ki.networks.mail.smtp.senders.SmtpSender;

import javax.mail.MessagingException;
import java.io.IOException;

public class HtmlSmtpSenderFactoryImpl extends AbstractSmtpSenderFactoryImpl {
    @Override
    public SmtpSender create(SmtpSenderParams params) throws IOException, MessagingException {
        HtmlSmtpSender smtpSender = new HtmlSmtpSender(params.getSmtpUser(), params.getSmtpPassword())
                .withTemplate(params.getTemplateName());
        return super.setup(smtpSender, params);
    }
}
