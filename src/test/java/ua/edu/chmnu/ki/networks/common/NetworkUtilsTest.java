package ua.edu.chmnu.ki.networks.common;

import org.junit.jupiter.api.Test;

import java.net.InetAddress;
import java.net.SocketException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;

public class NetworkUtilsTest {

    @Test
    public void test01() throws SocketException {
        List<InetAddress> addressList = NetworkUtils.getAvailableInetAddresses();
        addressList.forEach(System.out::println);
        assertFalse(addressList.isEmpty());
    }
}
