package ua.edu.chmnu.net.common;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.stream.Collectors;

public class NetworkUtils {
    public static List<NetworkInterface> getAvailableInterfaces() throws SocketException
    {
        List<NetworkInterface> lsIfaces = new ArrayList<>();
        Enumeration<NetworkInterface> allIfaces = NetworkInterface.getNetworkInterfaces();
        while (allIfaces.hasMoreElements())
        {
            NetworkInterface iface = allIfaces.nextElement();
            if (!iface.isLoopback() && iface.isUp())
            {
                lsIfaces.add(iface);
            }
        }
        return lsIfaces;
    }
    
    public static List<InetAddress> getAvailableInetAddresses() throws SocketException
    {
        return getAvailableInterfaces().stream()
                .flatMap(iface -> 
                        iface.getInterfaceAddresses().stream().map(a -> a.getAddress()))
                .collect(Collectors.toList());
    }
    
    public static void main(String[] args) throws SocketException {
     getAvailableInetAddresses();   
    }
}
