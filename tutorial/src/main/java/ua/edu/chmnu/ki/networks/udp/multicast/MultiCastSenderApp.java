package ua.edu.chmnu.ki.networks.udp.multicast;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static ua.edu.chmnu.ki.networks.utils.CmdLineParser.extractValue;


public class MultiCastSenderApp {

    public static void main(String[] args) throws IOException {
        ExecutorService service = Executors.newCachedThreadPool();

        String group = "224.0.0.3";
        int port = 5559;
        for (String arg : args) {
            String value = extractValue(arg, "-g:");
            if (value != null) {
                group = value;
                continue;
            }

            value = extractValue(arg, "-p:");
            if (value != null) {
                port = Integer.parseInt(value);
            }

        }

        MultiCastSender sender =  new MultiCastSender(group, port).setAction(() -> {
            String toSend = String.format("Local time: %s", LocalDateTime.now());
            return toSend.getBytes();
        });
        service.submit(sender);

        try (Scanner in = new Scanner(System.in)) {
            System.out.println("To stop press Q");
            String line;
            do {
                line = in.nextLine();
            } while (line != null && !line.equalsIgnoreCase("Q"));
        }
        sender.setActive(false);
        service.shutdown();
    }
}
