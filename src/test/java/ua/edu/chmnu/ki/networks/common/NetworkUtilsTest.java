package ua.edu.chmnu.ki.networks.common;

import junitparams.JUnitParamsRunner;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.net.InetAddress;
import java.net.SocketException;
import java.util.List;

@RunWith(JUnitParamsRunner.class)
public class NetworkUtilsTest {

    @Test
    public void test01() throws SocketException {
        List<InetAddress> addressList = NetworkUtils.getAvailableInetAddresses();
        addressList.forEach(System.out::println);
        Assert.assertFalse(addressList.isEmpty());
    }
}
