package ua.chmnu.edu.net.common;

import junitparams.JUnitParamsRunner;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.net.InetAddress;
import java.net.SocketException;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(JUnitParamsRunner.class)
public class NetworkUtilsTest {

    @Test
    public void test01() throws SocketException {
        List<InetAddress> addressList = NetworkUtils.getAvailableInetAddresses();
        addressList.forEach(System.out::println);
        Assert.assertFalse(addressList.isEmpty());
    }
}