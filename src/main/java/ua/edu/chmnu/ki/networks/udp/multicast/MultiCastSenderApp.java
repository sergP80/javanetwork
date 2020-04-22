package ua.edu.chmnu.ki.networks.udp.multicast;

import java.io.IOException;
import java.net.SocketException;
import java.time.LocalDateTime;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static ua.edu.chmnu.ki.networks.common.CmdLineParser.extractValue;


public class MultiCastSenderApp {

    public static void main(String[] args) throws SocketException, IOException {
        ExecutorService service = Executors.newCachedThreadPool();

        String group = "224.0.0.3";
        int port = 5559;
        for (int i = 0; i < args.length; ++i) {
            String value = extractValue(args[i], "-g:");
            if (value != null) {
                group = value;
                continue;
            }

            value = extractValue(args[i], "-p:");
            if (value != null) {
                port = Integer.parseInt(value);
            }

        }

        MultiCastSender sender =  new MultiCastSender(group, port).setAction(() -> {
            String toSend = String.format("Local time: %s", LocalDateTime.now().toString());
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
