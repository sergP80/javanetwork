/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.edu.chmnu.ki.networks.udp.mouse.sender;

import ua.edu.chmnu.ki.networks.mouse.model.MouseCapture;
import ua.edu.chmnu.ki.networks.udp.core.BasicSender;

import java.awt.*;
import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Stream;

/**
 * @author svpuzyrov
 */
public class MouseCaptureOperator {
    public static void main(String[] args) throws InterruptedException, IOException {

        String connectionUrl;

        if (args == null || args.length == 0) {
            try (Scanner scanner = new Scanner(System.in)) {
                System.out.print("Enter end-point:");

                connectionUrl = scanner.nextLine();
            }
        } else {
            connectionUrl = args[0];
        }

        String[] urlParts = connectionUrl.split(":");

        String host = urlParts[0];

        int port = Integer.parseInt(urlParts[1]);

        try (ExecutorService executor = Executors.newSingleThreadExecutor()) {
            BasicSender<MouseCapture> mouseCaptureSender = new BasicSender<>(host, port, () -> {
                PointerInfo mousePointerInfo = MouseInfo.getPointerInfo();

                return new MouseCapture(mousePointerInfo.getLocation());
            });

            executor.submit(mouseCaptureSender);

            try (Scanner scanner = new Scanner(System.in)) {
                boolean isDown = false;

                while (!isDown) {
                    String line = scanner.nextLine();

                    isDown = Stream.of("Q", "q", "Quit", "By", "Exit").anyMatch(s -> s.equalsIgnoreCase(line));
                }
            }
        }
    }
}