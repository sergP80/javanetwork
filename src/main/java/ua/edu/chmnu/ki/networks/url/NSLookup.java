package ua.edu.chmnu.ki.networks.url;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class NSLookup {

    private static InetAddress[] getBoundIPList(String host) throws UnknownHostException {
        InetAddress[] iaList = InetAddress.getAllByName(host);
        return iaList;
    }

    private static String enterHost(String[] args) {
        if (args.length > 0) {
            return args[0];
        } else {
            try (Scanner in = new Scanner(System.in)) {
                System.out.print("Enter a host:");
                return in.nextLine();
            }
        }
    }

    public static void main(String[] args) {
        try {
            String host = enterHost(args);
            InetAddress[] iaList = getBoundIPList(host);
            if (iaList != null) {
                for (InetAddress ia : iaList) {
                    System.out.println(ia);
                }
            }
        } catch (UnknownHostException ex) {
            Logger.getLogger(NSLookup.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
