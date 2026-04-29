package ua.edu.chmnu.ki.networks.mail.smtp.config;

import org.apache.commons.lang3.StringUtils;
import ua.edu.chmnu.ki.networks.mail.smtp.factories.EmailType;

import java.io.Console;
import java.io.IOException;
import java.util.Optional;
import java.util.Properties;
import java.util.function.Function;
import java.util.function.Supplier;

public class ConsolePropertySmtpSenderParamConfigurer implements SmtpSenderParamConfigurer {
    private static final String SMTP_SENDER = "smtp.sender";

    private static final Console CONSOLE = System.console();

    @Override
    public SmtpSenderConfig configure(EmailType emailType) throws IOException {
        Properties properties = loadProperties();

        SmtpSenderConfig.SmtpSenderConfigBuilder smtpSenderParamsBuilder = SmtpSenderConfig.builder();

        String smtpUser = readPropertyFrom("smtp.auth.user", properties, Function.identity());

        smtpSenderParamsBuilder.smtpUser(smtpUser);

        String smtpPassword = readPropertyFrom("smtp.auth.password", properties, Function.identity(), () -> {
            CONSOLE.printf("Enter smtp password: ");
            return new String(CONSOLE.readPassword());
        });

        smtpSenderParamsBuilder.smtpPassword(smtpPassword);

        String subject = readPropertyFrom("smtp.subject", properties, Function.identity());

        smtpSenderParamsBuilder.subject(subject);

        String from = readPropertyFrom("smtp.from", properties, Function.identity());

        smtpSenderParamsBuilder.from(from);

        String[] recipients = readPropertyFrom("smtp.recipients", properties, this::parseRecipients);

        smtpSenderParamsBuilder.recipients(recipients);

        configContent(emailType, properties, smtpSenderParamsBuilder);

        return smtpSenderParamsBuilder.build();
    }

    @Override
    public Console console() {
        return CONSOLE;
    }

    private static Properties loadProperties() throws IOException {
        Properties properties = new Properties();

        String senderSmtpPropertyPath = "/" + SMTP_SENDER + ".properties";
        properties.load(ConsolePropertySmtpSenderParamConfigurer.class.getResourceAsStream(senderSmtpPropertyPath));

        return properties;
    }

    private String[] parseRecipients(String source) {
        if (StringUtils.isEmpty(source)) {
            throw new IllegalArgumentException("Enter a valid emails");
        }

        return source.split("[\\s;:,]+");
    }

    private void configContent(EmailType emailType, Properties properties, SmtpSenderConfig.SmtpSenderConfigBuilder paramsBuilder) {

        switch (emailType) {
            case HTML, HTML_MULTI_PART:

                String templateName = readPropertyFrom("smtp.content.html.template.name", properties, Function.identity());

                paramsBuilder.templateName(templateName);

                break;
            default:
                String contentText = readPropertyFrom("smtp.contet.text", properties, Function.identity());

                paramsBuilder.text(contentText);
        }
    }

    private <T> T readPropertyFrom(String propertyKey, Properties properties, Function<String, T> mapper, Supplier<T> defaultSupplier) {
        return Optional.ofNullable(System.getenv().get(convertToEnvID(propertyKey)))
                .or(() -> Optional.ofNullable(properties.getProperty(propertyKey)))
                .map(mapper)
                .orElseGet(defaultSupplier);
    }

    private <T> T readPropertyFrom(String propertyKey, Properties properties, Function<String, T> mapper) {
        return Optional.ofNullable(System.getenv().get(convertToEnvID(propertyKey)))
                .or(() -> Optional.ofNullable(properties.getProperty(propertyKey)))
                .map(mapper)
                .orElseGet(() -> {
                    CONSOLE.printf("Enter " + propertyKey + ":");
                    return mapper.apply(CONSOLE.readLine());
                });
    }

    private String convertToEnvID(String propertyKey) {
        return propertyKey.replace('.', '_').toUpperCase();
    }
}
