package ua.edu.chmnu.ki.networks.mail.smtp.senders;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMultipart;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class AttachmentSmtpSender extends AbstractSmtpSender {
    private final MimeMultipart mimeMultipart = new MimeMultipart();


    public AttachmentSmtpSender(String smtpUser, String smtpPassword) throws IOException {
        super(smtpUser, smtpPassword);
    }

    public AttachmentSmtpSender withText(String text) throws MessagingException {
        MimeBodyPart textPart = new MimeBodyPart();
        textPart.setText(text, StandardCharsets.UTF_8.name());
        this.mimeMultipart.addBodyPart(textPart);
        return this;
    }

    public AttachmentSmtpSender withAttachments(String[] attachments) throws MessagingException {
        if (attachments == null) {
            return this;
        }

        for (String attachmentPath : attachments) {
            File file = new File(attachmentPath);
            if (file.exists()) {
                addAttachment(file);
            } else {
                System.err.println("File not found: " + attachmentPath);
            }
        }
        return this;
    }

    private void addAttachment(File file) throws MessagingException {
        MimeBodyPart attachmentPart = new MimeBodyPart();
        attachmentPart.setDataHandler(new DataHandler(new FileDataSource(file)));
        attachmentPart.setFileName(file.getName());
        this.mimeMultipart.addBodyPart(attachmentPart);
    }

    @Override
    public void send() throws MessagingException, IOException {

        this.mimeMessage.setContent(mimeMultipart);
        super.send();
    }
}
