package ua.edu.chmnu.ki.networks.mail.smtp.senders;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMultipart;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class HtmlMultipartSmtpSender extends HtmlSmtpSender {

    private final MimeMultipart mimeMultipart = new MimeMultipart();

    public HtmlMultipartSmtpSender(Session session) {
        super(session);
    }

    public HtmlMultipartSmtpSender(String smtpUser, String smtpPassword) throws IOException {
        super(smtpUser, smtpPassword);
    }

    @Override
    public HtmlMultipartSmtpSender withTemplate(String templateName) throws MessagingException, FileNotFoundException {
        MimeBodyPart bodyPart = new MimeBodyPart();
        String content = getTemplateContent(templateName);
        bodyPart.setContent(content, "text/html");
        this.mimeMultipart.addBodyPart(bodyPart);
        return this;
    }

    public HtmlMultipartSmtpSender withAttachments(String[] attachments) throws MessagingException {
        for (String attachment: attachments) {
            if (Files.exists(Paths.get(attachment))) {
                addAttachment(this.mimeMultipart, attachment);
            }
        }
        return this;
    }

    private void addAttachment(MimeMultipart multipart, String attachment) throws MessagingException {
        MimeBodyPart bodyPart = new MimeBodyPart();
        bodyPart.setDataHandler(new DataHandler(new FileDataSource(attachment)));
        multipart.addBodyPart(bodyPart);
    }
}