package ua.edu.chmnu.ki.networks.mail.smtp.senders;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import ua.edu.chmnu.ki.networks.mail.smtp.SimpleEmailSender;
import ua.edu.chmnu.ki.networks.mail.utils.MailUtils;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Objects;
import java.util.Properties;

public abstract class AbstractSmtpSender implements SmtpSender {
    private static final String SMTP_SETTINGS = "smtp.settings";

    private Session session;
    private MimeMessage mimeMessage;

    public AbstractSmtpSender(Session session) {
        this.session = session;
        this.mimeMessage = createMimeMessage(session);
    }

    public AbstractSmtpSender(String smtpUser, String smtpPassword) throws IOException {
        this.session = createSmtpSession(smtpUser, smtpPassword);
        this.mimeMessage = createMimeMessage(session);
    }

    public Session getSession() {
        return session;
    }

    public MimeMessage getMimeMessage() {
        return mimeMessage;
    }

    public static Session createSmtpSession(String smtpUser, String smtpPassword) throws IOException {
        Properties properties = new Properties();
        String systemStmpSettings = System.getProperty(SMTP_SETTINGS);
        if (StringUtils.isNotEmpty(systemStmpSettings)) {
            properties.load(new FileInputStream(systemStmpSettings));
        } else {
            systemStmpSettings = "/" + SMTP_SETTINGS + ".properties";
            properties.load(SimpleEmailSender.class.getResourceAsStream(systemStmpSettings));
        }
        Authenticator authenticator = new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(smtpUser, smtpPassword);
            }
        };
        return Session.getDefaultInstance(properties, authenticator);
    }

    protected MimeMessage createMimeMessage(Session session) {
        return new MimeMessage(session);
    }

    public AbstractSmtpSender withSubject(String subject) throws MessagingException {
        if (ObjectUtils.isNotEmpty(this.mimeMessage) && ObjectUtils.isNotEmpty(subject)) {
            this.mimeMessage.setSubject(subject);
        }
        return this;
    }

    public AbstractSmtpSender withSender(String sender) throws MessagingException {
        if (ObjectUtils.isNotEmpty(this.mimeMessage) && ObjectUtils.isNotEmpty(sender)) {
            this.mimeMessage.setSender(new InternetAddress(sender));
        }
        return this;
    }

    public AbstractSmtpSender withFrom(String from) throws MessagingException {
        if (ObjectUtils.isNotEmpty(this.mimeMessage) && ObjectUtils.isNotEmpty(from)) {
            this.mimeMessage.setFrom(new InternetAddress(from));
        }
        return this;
    }

    public AbstractSmtpSender withRecipients(String[] recipientList) throws MessagingException {
        Objects.requireNonNull(this.mimeMessage);
        this.mimeMessage.setRecipients(Message.RecipientType.TO, MailUtils.getInetAddressList(recipientList));
        return this;
    }

    public AbstractSmtpSender withCc(String[] ccList) throws MessagingException {
        if (ObjectUtils.isNotEmpty(this.mimeMessage) && ObjectUtils.isNotEmpty(ccList)) {
            this.mimeMessage.setRecipients(Message.RecipientType.CC, MailUtils.getInetAddressList(ccList));
        }
        return this;
    }

    public AbstractSmtpSender withBcc(String[] bccList) throws MessagingException {
        if (ObjectUtils.isNotEmpty(this.mimeMessage) && ObjectUtils.isNotEmpty(bccList)) {
            this.mimeMessage.setRecipients(Message.RecipientType.BCC, MailUtils.getInetAddressList(bccList));
        }
        return this;
    }

    public AbstractSmtpSender withReplyTo(String[] replyTo) throws MessagingException {
        if (ObjectUtils.isNotEmpty(this.mimeMessage) && ObjectUtils.isNotEmpty(replyTo)) {
            this.mimeMessage.setReplyTo(MailUtils.getInetAddressList(replyTo));
        }
        return this;
    }

    @Override
    public void send() throws MessagingException, IOException {
        MailUtils.verifyRequiredSettings(this.mimeMessage);
        Transport.send(mimeMessage);
    }
}
