package ua.edu.chmnu.ki.networks.udp.multicast;

import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static ua.edu.chmnu.ki.networks.common.CmdLineParser.extractValue;


public class MultiCastReceiverApp {

    public static void main(String[] args) throws IOException {
        ExecutorService service = Executors.newFixedThreadPool(10);
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
        int n = 3;
        MultiCastReceiver receivers[] = new MultiCastReceiver[n];
        
        for (int i = 0; i < n; ++i) {
            service.submit(receivers[i] = new MultiCastReceiver(group, port));
        }

        try (Scanner in = new Scanner(System.in)) {
            in.nextLine();
        }
        for (MultiCastReceiver r: receivers)
        {
            r.setActive(false);
        }
        
        service.shutdown();
    }

}
