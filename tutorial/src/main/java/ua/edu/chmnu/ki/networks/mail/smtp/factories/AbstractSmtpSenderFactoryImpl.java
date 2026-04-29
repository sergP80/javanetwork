package ua.edu.chmnu.ki.networks.mail.smtp.factories;

import ua.edu.chmnu.ki.networks.mail.smtp.config.SmtpSenderConfig;
import ua.edu.chmnu.ki.networks.mail.smtp.senders.AbstractSmtpSender;

import javax.mail.MessagingException;

public abstract class AbstractSmtpSenderFactoryImpl implements SmtpSenderFactory {

    public AbstractSmtpSender setup(AbstractSmtpSender smtpSender, SmtpSenderConfig params) throws MessagingException {
        smtpSender
                .withSubject(params.getSubject())
                .withSender(params.getSender())
                .withFrom(params.getFrom())
                .withRecipients(params.getRecipients())
                .withCc(params.getCc())
                .withBcc(params.getBcc());
        return  smtpSender;
    }
}
