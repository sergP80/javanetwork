package ua.edu.chmnu.ki.networks.mail.smtp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SmtpSenderParams {
    private String smtpUser;
    private String smtpPassword;
    private String subject;
    private String text;
    private String templateName;
    private String[] attachments;
    private String from;
    private String sender;
    private String[] recipients;
    private String[] cc;
    private String[] bcc;
    private String[] replyTo;
}
