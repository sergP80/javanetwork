package ua.edu.chmnu.ki.networks.mail.smtp;

import org.apache.commons.lang3.StringUtils;
import ua.edu.chmnu.ki.networks.mail.smtp.factories.EmailType;
import ua.edu.chmnu.ki.networks.mail.smtp.factories.SmtpSenderFactory;
import ua.edu.chmnu.ki.networks.mail.smtp.senders.SmtpSender;

import javax.mail.*;
import java.io.Console;
import java.io.IOException;

public class SmtpApplication {
    private static EmailType selectEmailType() {
        Console console = System.console();
        console.printf("E-mail types\n");
        EmailType[] values = EmailType.values();
        for(int i = 0; i < values.length; ++i) {
            console.printf("-> %s [%d]\n", values[i], i + 1);
        }
        console.printf("Select e-mail type: ");
        return EmailType.of(Integer.parseInt(console.readLine()) - 1);
    }

    private static void addSmtpParams(EmailType emailType, SmtpSenderParams.SmtpSenderParamsBuilder paramsBuilder) {
        Console console = System.console();

        switch (emailType) {
            case HTML -> {
                console.printf("Type email template name: ");
                paramsBuilder.templateName(console.readLine());
            }
            case HTML_MULTI_PART -> {
                console.printf("Type email template name: ");
                paramsBuilder.templateName(console.readLine());
                console.printf("Type attachments: ");
                paramsBuilder.attachments(splitBy(console.readLine()));
            }
            default -> {
                console.printf("Type text message: ");
                paramsBuilder.text(console.readLine());
            }
        }
    }

    private static String[] splitBy(String source) {
        if (StringUtils.isEmpty(source)) {
            throw new IllegalArgumentException("Enter a non-empty string");
        }
        return source.split("[\\s;:]+");
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
            String[] recipients = splitBy(console.readLine());
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

            String line = console.readLine();
            if ("Q".equals(line)) {
                break;
            }
        } while (true);

    }
}
