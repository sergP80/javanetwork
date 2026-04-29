package ua.edu.chmnu.ki.networks.mail.smtp.config;

import ua.edu.chmnu.ki.networks.mail.smtp.factories.EmailType;

import java.io.Console;
import java.io.IOException;

public interface SmtpSenderParamConfigurer {

    SmtpSenderConfig configure(EmailType emailType) throws IOException;

    Console console();
}
