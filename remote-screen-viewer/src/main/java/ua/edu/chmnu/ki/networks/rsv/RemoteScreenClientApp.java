package ua.edu.chmnu.ki.networks.rsv;

import ua.edu.chmnu.ki.networks.rsv.client.ScreenClient;
import ua.edu.chmnu.ki.networks.rsv.common.AppRunner;

public class RemoteScreenClientApp {

    public static void main(String[] args) throws Exception {
        AppConfig config = AppConfig.fromEnvForClient();
        AppRunner appRunner = new ScreenClient(config);
        appRunner.start();
    }
}
