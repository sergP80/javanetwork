/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.edu.chmnu.net.tcp;

import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *
 * @author svpuzyrov
 */
public class SimpleTCPServerApp {
    public static void main(String[] args) throws IOException {
        SimpleTCPServer server = new SimpleTCPServer();
        ExecutorService executor = Executors.newFixedThreadPool(20);
        server.setExecutor(executor);
        executor.submit(server);
        System.out.println("To stop application press any key");
        try(Scanner in = new Scanner(System.in))
        {
            while(!in.hasNext())
            {}
        }
        
        server.setActive(false);
        executor.shutdown();
        System.out.println("Stopped application");
    }
}
