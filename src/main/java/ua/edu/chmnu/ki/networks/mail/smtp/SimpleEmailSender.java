package ua.edu.chmnu.ki.networks.mail.smtp;

import org.apache.commons.lang3.StringUtils;

import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.Console;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Objects;
import java.util.Properties;

public class SimpleEmailSender {
    private static final String PROPERTY_FILE_NAME = "/smtp.settings.properties";
    private final String smtpUser;
    private final String smtpPassword;

    public SimpleEmailSender(String smtpUser, String smtpPassword) {
        this.smtpUser = smtpUser;
        this.smtpPassword = smtpPassword;
    }

    private Session getSession() throws IOException {
        Properties properties = new Properties();
        properties.load(SimpleEmailSender.class.getResourceAsStream(PROPERTY_FILE_NAME));
        Authenticator authenticator = new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(SimpleEmailSender.this.smtpUser, SimpleEmailSender.this.smtpPassword);
            }
        };
        return Session.getDefaultInstance(properties, authenticator);
    }

    public void send(String subject, String from, String[] to, String text) throws MessagingException, IOException {
        Session session = this.getSession();
        MimeMessage mimeMessage = new MimeMessage(session);
        mimeMessage.setSubject(subject);
        mimeMessage.setFrom(from);
        InternetAddress[] toAddresses = Arrays.stream(to).map(a -> {
            try {
                return new InternetAddress(a);
            } catch (AddressException e) {
                return null;
            }
        }).filter(Objects::nonNull).toArray(InternetAddress[]::new);
        mimeMessage.setRecipients(Message.RecipientType.TO, toAddresses);
        mimeMessage.setText(text, StandardCharsets.UTF_8.name());
        Transport.send(mimeMessage);
    }

    public static void main(String[] args) throws IOException, MessagingException {
        Console console = System.console();
        do {
            console.printf("Enter smtp user: ");
            String smtpUser = console.readLine();
            console.printf("Enter smtp password: ");
            String smtpPassword = new String(console.readPassword());
            console.printf("Enter recipient(s): ");
            String line = console.readLine();
            if (StringUtils.isEmpty(line)) {
                throw new IllegalArgumentException("Enter a valid emails");
            }
            String[] recipients = line.split("[\\s;:]+");
            console.printf("Type message: ");
            String text = console.readLine();
            if (StringUtils.isEmpty(text)) {
                throw new IllegalArgumentException("Type a valid message");
            }
            new SimpleEmailSender(smtpUser, smtpPassword)
                    .send("My first init message", "admin@mail.com", recipients, text);

            console.printf("Continue - any key, Q - exit");
            line = console.readLine();
            if ("Q".equals(line)) {
                break;
            }
        } while (true);

    }
}
