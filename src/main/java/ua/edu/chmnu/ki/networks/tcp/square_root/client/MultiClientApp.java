/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.edu.chmnu.ki.networks.tcp.square_root.client;

import ua.edu.chmnu.ki.networks.tcp.core.client.ServerResponseDelegate;
import ua.edu.chmnu.ki.networks.tcp.core.client.TCPClient;
import ua.edu.chmnu.ki.networks.tcp.square_root.model.Request;
import ua.edu.chmnu.ki.networks.tcp.square_root.model.Response;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author svpuzyrov
 */
public class MultiClientApp {
    public static void main(String[] args) throws InterruptedException, IOException {
        double[][] testData = {
                {-1, 2, -3},
                {4, 5, 1},
                {1, 4, 4}
        };

        String host = "127.0.0.1";
        int port = 5558;
        int poolSize = Integer.parseInt(System.getProperty("client.pool-size", "10"));
        ExecutorService executor = Executors.newFixedThreadPool(poolSize);
        for (double[] data : testData) {
            executor.submit(new TCPClient(host, port, new Request(data), new ServerResponseDelegateImpl()));
        }

        Thread.sleep(2000);
        executor.shutdown();
    }

    public static class ServerResponseDelegateImpl implements ServerResponseDelegate {

        @Override
        public void handle(Object r) {
            Response response = (Response) r;
            switch (response.getResult()) {
                case NO_ROOTS -> System.out.println("Has no roots");
                case ONE_ROOT -> System.out.println("Two equal roots: x1=x2=" + response.getRoots()[0]);
                default ->
                        System.out.println("Two roots: x1=" + response.getRoots()[0] + ", x2=" + response.getRoots()[1]);
            }
        }
    }
}