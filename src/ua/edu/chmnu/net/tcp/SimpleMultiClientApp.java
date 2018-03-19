/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.edu.chmnu.net.tcp;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *
 * @author svpuzyrov
 */
public class SimpleMultiClientApp {
    public static void main(String[] args) throws InterruptedException {
        String[] testData = {
            "Hello, I'm here!",
            "Is anyone here?",
            "All are active"
        };
        
        String host = "127.0.0.1";
        int port = 5558;
        ExecutorService executor = Executors.newFixedThreadPool(10);
        for (String line: testData)
        {
            executor.submit(new SimpleTCPClient(host, port, line));
        }
        
        Thread.sleep(2000);
        executor.shutdown();
    }
}
