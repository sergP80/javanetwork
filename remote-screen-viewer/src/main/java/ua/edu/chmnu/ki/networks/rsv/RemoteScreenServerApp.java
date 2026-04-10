package ua.edu.chmnu.ki.networks.rsv;


import ua.edu.chmnu.ki.networks.rsv.server.ScreenServer;
import ua.edu.chmnu.ki.networks.rsv.server.ScreenServerImpl;

public class RemoteScreenServerApp {

    public static void main(String[] args) throws Exception {
        AppConfig config = AppConfig.fromEnvForServer();
        ScreenServer server = new ScreenServerImpl(config);
        server.start();
    }
}
