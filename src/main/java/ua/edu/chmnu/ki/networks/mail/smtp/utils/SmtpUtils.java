package ua.edu.chmnu.ki.networks.mail.smtp.utils;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;

public class SmtpUtils {
    private SmtpUtils() {
    }

    public static InternetAddress[] getInetAddressList(String[] addresses) {
        return Arrays.stream(addresses).map(a -> {
            try {
                return new InternetAddress(a);
            } catch (AddressException e) {
                return null;
            }
        }).filter(Objects::nonNull).toArray(InternetAddress[]::new);
    }

    public static void verifyRequiredSettings(MimeMessage message) throws MessagingException, IOException {
        if (ObjectUtils.isEmpty(message)) {
            throw new IllegalArgumentException("Mime message is not created");
        }

        if (ObjectUtils.isEmpty(message.getFrom())) {
            throw new IllegalArgumentException("FROM should not be empty");
        }

        if (ObjectUtils.isEmpty(message.getRecipients(Message.RecipientType.TO))) {
            throw new IllegalArgumentException("Recipients TO should not be empty");
        }

        if (ObjectUtils.isEmpty(message.getContent())) {
            throw new IllegalArgumentException("Content was not set");
        }
    }

    public static String readFileContent(InputStream is) {
        return new BufferedReader(new InputStreamReader(is))
                .lines()
                .collect(Collectors.joining("\n"));
    }
}