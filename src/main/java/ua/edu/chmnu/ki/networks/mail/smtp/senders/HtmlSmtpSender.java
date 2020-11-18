package ua.edu.chmnu.ki.networks.mail.smtp.senders;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import ua.edu.chmnu.ki.networks.mail.utils.MailUtils;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.MimeMessage;
import java.io.*;

public class HtmlSmtpSender extends AbstractSmtpSender {
    private static String DEFAULT_HTML_ROOT = "/emails";
    public HtmlSmtpSender(Session session) {
        super(session);
    }

    public HtmlSmtpSender(String smtpUser, String smtpPassword) throws IOException {
        super(smtpUser, smtpPassword);
    }

    public HtmlSmtpSender withTemplate(String templateName) throws MessagingException, FileNotFoundException {
        if (ObjectUtils.isEmpty(templateName)) {
            throw new IllegalArgumentException("Template name is not set");
        }
        String content = getTemplateContent(templateName);
        MimeMessage mimeMessage = getMimeMessage();
        mimeMessage.setContent(content, "text/html");
        return this;
    }

    protected String getTemplateContent(String templateName) throws FileNotFoundException {
        String templateRootPath = System.getProperty("emails.html.root");
        InputStream is;
        templateName = getTemplateName(templateName);
        if (StringUtils.isNotEmpty(templateRootPath)) {
            is = new FileInputStream(templateRootPath + File.separator + templateName);
        } else {
            is = HtmlSmtpSender.class.getResourceAsStream(DEFAULT_HTML_ROOT + "/" + templateName);
        }
        return MailUtils.readFileContent(is);
    }

    private String getTemplateName(String templateName) {
        templateName = templateName.substring(templateName.indexOf(File.separator) + 1);
        return templateName.endsWith(".html") ? templateName : templateName + ".html";
    }
}
