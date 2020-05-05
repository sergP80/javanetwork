package ua.edu.chmnu.ki.networks.tcp.arrays;

import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SimpleTCPServerApp {
    public static void main(String[] args) throws IOException {
        int port = 5559;
        SimpleTCPServer server = new SimpleTCPServer(port);
        ExecutorService executor = Executors.newFixedThreadPool(20);
        server.setExecutor(executor);
        executor.submit(server);
        System.out.println("To stop application press any key");
        try(Scanner in = new Scanner(System.in))
        {
            do {
                String line = in.nextLine();
                if (line.equalsIgnoreCase("Q"))
                {
                    break;
                }
            }while(true);
        }
        
        server.setActive(false);
        executor.shutdown();
        System.out.println("Stopped application");
    }
}
