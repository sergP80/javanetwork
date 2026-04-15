package ua.edu.chmnu.ki.networks.rsv.client.service;

import java.io.IOException;
import java.net.InetSocketAddress;

public interface HelloService {

    boolean sendTo(InetSocketAddress target) throws IOException;
}
