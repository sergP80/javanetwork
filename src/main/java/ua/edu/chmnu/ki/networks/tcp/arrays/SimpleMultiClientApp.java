/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.edu.chmnu.ki.networks.tcp.arrays;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *
 * @author svpuzyrov
 */
public class SimpleMultiClientApp {
    public static void main(String[] args) throws InterruptedException, IOException {
        Integer[][] testData = {
                {-1, 4, 10, 15, 22, 17},
                {6, -2, 8},
                {-1, -9, 11, 22},
                {55, -1, 22, 12, 198, 19, 1, 5, 127},
        };
        
        String host = "127.0.0.1";
        int port = 5559;
        ExecutorService executor = Executors.newFixedThreadPool(10);
        for (Integer[] data: testData)
        {
            executor.submit(new SimpleTCPClient(host, port, data));
        }
        
        Thread.sleep(2000);
        executor.shutdown();
    }
}
