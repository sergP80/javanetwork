package ua.edu.chmnu.ki.networks.mail.imap;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;
import ua.edu.chmnu.ki.networks.mail.utils.MailUtils;

import javax.mail.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.function.Consumer;

public class ImapReceiver implements Runnable {
	private final static String SETTINGS = "imap.settings.properties";

	private static final Properties PROPERTIES;

	static {
		PROPERTIES = new Properties();
		try {
			String systemSettings = System.getProperty(SETTINGS);
			if (!StringUtils.isEmpty(systemSettings) && !StringUtils.isBlank(systemSettings)) {
				PROPERTIES.load(new FileInputStream(systemSettings));
			} else {
				PROPERTIES.load(ImapReceiver.class.getResourceAsStream("/" + SETTINGS));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Getter
    private final String mailBox;
	private final String password;
	@Getter
    private final Session session;
	@Getter
    private final Store store;

	@Setter
    @Getter
    private String folderName;
	@Setter
    @Getter
    private Consumer<Message> messageConsumer;
	@Setter
    @Getter
    private boolean active = false;

    private void checkParams() {
		if (!MailUtils.isEmailValid(this.mailBox)) {
			throw new IllegalArgumentException();
		}
	}
	public ImapReceiver(String mailBox, String password) throws NoSuchProviderException {
		this.mailBox = mailBox;
		this.password = password;
		this.checkParams();
		this.session = Session.getDefaultInstance(PROPERTIES, null);
        String storeProtocol = "imaps";
        this.store = this.session.getStore(storeProtocol);
	}

    protected String getHostFromMailBox() {
		checkParams();
		return this.mailBox.substring(this.mailBox.indexOf("@") + 1);

	}

    public String getMailHost() {
		String connectionHost = PROPERTIES.getProperty("mail.imap.host");
		if (StringUtils.isBlank(connectionHost)) {
            String hostProtocol = "imap";
            return String.join(".", hostProtocol, getHostFromMailBox());
		}
		return connectionHost;
	}

    void checkFolder() throws MessagingException {
		if (StringUtils.isEmpty(this.folderName) || StringUtils.isBlank(this.folderName)) {
			throw new IllegalArgumentException("Folder cannot be empty");
		}
		try (Store store = this.store) {
			store.connect(getMailHost(), this.mailBox, this.password);
			try (Folder folder = store.getFolder(this.folderName)) {
				folder.open(Folder.READ_ONLY);

				int newMessageCount = folder.getNewMessageCount();
				if (newMessageCount > 0) {
					System.out.printf("Detected new incoming messages: [%d]\n", newMessageCount);
				}
				for (int i = 1; i <= folder.getMessageCount() && this.active; ++i) {
					this.messageConsumer.accept(folder.getMessage(i));
				}
			}
		}
	}

    @Override
	public void run() {
		while (this.active && Thread.currentThread().isAlive()) {
			try {
				checkFolder();
				Thread.sleep(5000);
			} catch (MessagingException | InterruptedException e) {
				e.printStackTrace();
			}
        }
	}
}
