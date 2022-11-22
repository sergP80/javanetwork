package ua.edu.chmnu.ki.networks.mail.smtp.senders;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
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
        MimeMessage mimeMessage = getMimeMessage();
        mimeMessage.setContent(this.mimeMultipart);
        return this;
    }

    public HtmlMultipartSmtpSender withAttachments(String[] attachments) throws MessagingException {
        final var defaultAttachmentRoot = "./src/main/resources/emails/attachments";
        var attachmentRoot = System.getProperty("mail.attachments.root", defaultAttachmentRoot);
        for (String attachment: attachments) {
            var attachmentPath = Paths.get(attachmentRoot, attachment);
            if (Files.exists(attachmentPath)) {
                addAttachment(this.mimeMultipart, attachmentPath.toString(), attachment);
            }
        }
        return this;
    }

    private void addAttachment(MimeMultipart multipart, String attachment, String name) throws MessagingException {
        MimeBodyPart bodyPart = new MimeBodyPart();
        bodyPart.setFileName(name);
        bodyPart.setDataHandler(new DataHandler(new FileDataSource(attachment)));
        multipart.addBodyPart(bodyPart);
    }
}
