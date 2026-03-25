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
import java.util.Scanner;
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

        String connectionUrl;

        if (args == null || args.length == 0) {
            try (Scanner scanner = new Scanner(System.in)) {
                System.out.print("Enter end-point:");

                connectionUrl = scanner.nextLine();
            }
        } else {
            connectionUrl = args[0];
        }

        int poolSize = Integer.parseInt(System.getProperty("client.pool-size", "10"));

        try (ExecutorService executor = Executors.newFixedThreadPool(poolSize)) {
            String[] urlParts = connectionUrl.split(":");

            String host = urlParts[0];

            int port = Integer.parseInt(urlParts[1]);

//            String host = "5.tcp.eu.ngrok.io";
//            int port = 14262;

            for (double[] data : testData) {
                executor.submit(new TCPClient(host, port, new Request(data), new ServerResponseDelegateImpl()));
            }

            Thread.sleep(2000);

            executor.shutdown();
        }
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