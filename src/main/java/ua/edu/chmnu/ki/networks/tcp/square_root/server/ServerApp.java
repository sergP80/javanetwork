package ua.edu.chmnu.ki.networks.tcp.square_root.server;

import ua.edu.chmnu.ki.networks.tcp.core.server.AbstractServerApp;

import java.io.IOException;

public class ServerApp extends AbstractServerApp {

    public ServerApp() {
        super();
    }

    public static void main(String[] args) throws IOException {
        new ServerApp().runApp(args, new DefaultClientSessionDelegate());
    }
}
