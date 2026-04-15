package ua.edu.chmnu.ki.networks.rsv;


import ua.edu.chmnu.ki.networks.rsv.common.AppRunner;
import ua.edu.chmnu.ki.networks.rsv.server.ScreenServer;

public class RemoteScreenServerApp {

    public static void main(String[] args) throws Exception {
        AppConfig config = AppConfig.fromEnvForServer();
        AppRunner appRunner = new ScreenServer(config);
        appRunner.start();
    }
}
