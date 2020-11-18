package ua.edu.chmnu.ki.networks.mail.imap;

import org.apache.commons.lang3.StringUtils;
import ua.edu.chmnu.ki.networks.mail.utils.MailUtils;

import javax.mail.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Properties;
import java.util.function.Consumer;

public class ImapReceiver implements Runnable {
	private final static String SETTINGS = "imap.settings.properties";

	private static Properties PROPERTIES;

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

	private final String mailBox;
	private final String password;
	private final Session session;
	private final Store store;

	private String folderName;
	private Consumer<Message> messageConsumer;
	private boolean active = false;
	private String storeProtocol = "imaps";
	private String hostProtocol = "imap";

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
		this.store = this.session.getStore(this.storeProtocol);
	}

	public String getMailBox() {
		return mailBox;
	}

	public Session getSession() {
		return session;
	}

	public Store getStore() {
		return store;
	}

	public String getFolderName() {
		return folderName;
	}

	public void setFolderName(String folderName) {
		this.folderName = folderName;
	}

	protected String getHostFromMailBox() {
		checkParams();
		return this.mailBox.substring(this.mailBox.indexOf("@") + 1);

	}

	public Consumer<Message> getMessageConsumer() {
		return messageConsumer;
	}

	public String getMailHost() {
		String connectionHost = PROPERTIES.getProperty("mail.imap.host");
		if (StringUtils.isBlank(connectionHost)) {
			return String.join(".", hostProtocol, getHostFromMailBox());
		}
		return connectionHost;
	}

	public void setMessageConsumer(Consumer<Message> messageConsumer) {
		this.messageConsumer = messageConsumer;
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

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	@Override
	public void run() {
		while (this.active && Thread.currentThread().isAlive()) {
			try {
				checkFolder();
				Thread.sleep(5000);
			} catch (MessagingException e) {
				e.printStackTrace();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
