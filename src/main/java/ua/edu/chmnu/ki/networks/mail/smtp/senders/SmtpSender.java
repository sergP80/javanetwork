package ua.edu.chmnu.ki.networks.mail.smtp.senders;

import javax.mail.MessagingException;
import java.io.IOException;

public interface SmtpSender {
    void send() throws MessagingException, IOException;
}
