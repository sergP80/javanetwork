package ua.edu.chmnu.ki.networks.mail.imap;

import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import java.io.Console;
import java.util.Scanner;

public class ImapReceiverApp {
	public static void main(String[] args) throws NoSuchProviderException, InterruptedException {
		Console console = System.console();
		console.printf("Enter mailbox: ");
		String mailBox = console.readLine();

		console.printf("Enter mailbox password: ");
		String password = new String(console.readPassword());
		ImapReceiver imapReceiver = new ImapReceiver(mailBox, password);
		imapReceiver.setFolderName("Inbox");
		imapReceiver.setMessageConsumer((message -> {
			try {
				String shortMsg = String.format("[%s]%s", message.getFrom()[0].toString(), message.getSubject());
				System.out.println(shortMsg);
			} catch (MessagingException e) {
				e.printStackTrace();
			}
		}));
		imapReceiver.setActive(true);
		Thread thread = new Thread(imapReceiver);
		thread.start();

		System.out.println("To stop type any key...");
		try (Scanner in = new Scanner(System.in)) {
			in.next();
		}
		System.out.println("Stopping...");
		imapReceiver.setActive(false);
		System.out.println("Stopped");
	}
}
