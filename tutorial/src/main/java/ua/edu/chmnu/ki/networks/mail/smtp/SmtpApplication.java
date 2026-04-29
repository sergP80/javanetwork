package ua.edu.chmnu.ki.networks.mail.smtp;

import ua.edu.chmnu.ki.networks.mail.smtp.config.ConsolePropertySmtpSenderParamConfigurer;
import ua.edu.chmnu.ki.networks.mail.smtp.config.SmtpSenderConfig;
import ua.edu.chmnu.ki.networks.mail.smtp.config.SmtpSenderParamConfigurer;
import ua.edu.chmnu.ki.networks.mail.smtp.factories.EmailType;
import ua.edu.chmnu.ki.networks.mail.smtp.factories.SmtpSenderFactory;
import ua.edu.chmnu.ki.networks.mail.smtp.senders.SmtpSender;

import javax.mail.MessagingException;
import java.io.Console;
import java.io.IOException;

public class SmtpApplication {

    public static void main(String[] args) throws IOException, MessagingException {
        SmtpSenderParamConfigurer configurer = new ConsolePropertySmtpSenderParamConfigurer();

        do {
            EmailType emailType = selectEmailType();

            SmtpSenderConfig smtpSenderConfig = configurer.configure(emailType);

            Console console = configurer.console();

            SmtpSenderFactory smtpSenderFactory = SmtpSenderFactory.createFactory(emailType);

            console.printf("Creating smtp sender...\n");
            SmtpSender smtpSender = smtpSenderFactory.create(smtpSenderConfig);
            console.printf("Done!\n");
            console.printf("Try to send...\n");
            smtpSender.send();
            console.printf("Done!\n");
            console.printf("Continue - any key, Q - exit");

            String line = console.readLine();
            if ("Q".equals(line)) {
                break;
            }
        } while (true);

    }

    private static EmailType selectEmailType() {
        Console console = System.console();
        console.printf("E-mail types\n");
        EmailType[] values = EmailType.values();
        for (int i = 0; i < values.length; ++i) {
            console.printf("-> %s [%d]\n", values[i], i + 1);
        }
        console.printf("Select e-mail type: ");
        return EmailType.valueOf(console.readLine());
    }
}
