package ua.edu.chmnu.ki.networks.url;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class SimplePing {

    public static void main(String[] args) throws UnknownHostException, IOException {
        String host = args.length >= 1 ? args[0] : "localhost";
        int timeout = 1000;
        int count = 4;
        if (args.length >= 2) {
            String paramTimeout = "-t:";
            int idxTimeout = args[1].indexOf(paramTimeout);
            if (idxTimeout >= 0) {
                timeout = Integer.parseInt(args[1].substring(paramTimeout.length()));
            }
        }
        while(count-- > 0)
        {
           System.out.print("Host: " + host + " is ");
           if (InetAddress.getByName(host).isReachable(timeout))
           {
               System.out.println("reacheable");
           } else
           {
               System.out.println("unreacheable");
           }
        }        
    }
}
