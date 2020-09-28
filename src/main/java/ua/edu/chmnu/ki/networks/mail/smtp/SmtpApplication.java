package ua.edu.chmnu.ki.networks.mail.smtp;

import org.apache.commons.lang3.StringUtils;
import ua.edu.chmnu.ki.networks.mail.smtp.factories.EmailType;
import ua.edu.chmnu.ki.networks.mail.smtp.factories.SmtpSenderFactory;
import ua.edu.chmnu.ki.networks.mail.smtp.senders.SmtpSender;

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

public class SmtpApplication {
    private static EmailType selectEmailType() {
        Console console = System.console();
        console.printf("E-mail types\n");
        EmailType[] values = EmailType.values();
        for(int i = 0; i < values.length; ++i) {
            console.printf("-> %s [%d]\n", values[i], i + 1);
        }
        console.printf("Select e-mail type: ");
        return EmailType.valueOf(console.readLine());
    }

    private static void addSmtpParams(EmailType emailType, SmtpSenderParams.SmtpSenderParamsBuilder paramsBuilder) {
        Console console = System.console();

        switch (emailType) {
            case HTML:
                console.printf("Type email template name: ");
                paramsBuilder.templateName(console.readLine());
                break;

            case HTML_MULTI_PART:
                console.printf("Type email template name: ");
                paramsBuilder.templateName(console.readLine());
                break;
            default:
                console.printf("Type text message: ");
                paramsBuilder.text(console.readLine());
        }
    }

    public static void main(String[] args) throws IOException, MessagingException {
        Console console = System.console();
        SmtpSenderParams.SmtpSenderParamsBuilder smtpSenderParamsBuilder = SmtpSenderParams.builder();

        console.printf("Enter smtp user: ");
        smtpSenderParamsBuilder.smtpUser(console.readLine());

        console.printf("Enter smtp password: ");
        smtpSenderParamsBuilder.smtpPassword(new String(console.readPassword()));

        do {
            EmailType emailType = selectEmailType();

            console.printf("Enter subject: ");
            smtpSenderParamsBuilder.subject(console.readLine());

            console.printf("Type from: ");
            smtpSenderParamsBuilder.from(console.readLine());

            console.printf("Enter recipient(s): ");
            String line = console.readLine();
            if (StringUtils.isEmpty(line)) {
                throw new IllegalArgumentException("Enter a valid emails");
            }
            String[] recipients = line.split("[\\s;:]+");
            smtpSenderParamsBuilder.recipients(recipients);
            addSmtpParams(emailType, smtpSenderParamsBuilder);

            SmtpSenderFactory smtpSenderFactory = SmtpSenderFactory.createFactory(emailType);
            console.printf("Creating smtp sender...\n");
            SmtpSender smtpSender = smtpSenderFactory.create(smtpSenderParamsBuilder.build());
            console.printf("Done!\n");
            console.printf("Try to send...\n");
            smtpSender.send();
            console.printf("Done!\n");
            console.printf("Continue - any key, Q - exit");

            line = console.readLine();
            if ("Q".equals(line)) {
                break;
            }
        } while (true);

    }
}
