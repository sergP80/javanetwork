package ua.edu.chmnu.ki.networks.common;

import java.net.*;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 * This class contains some wrapped method 
 * for @see java.ua.chmnu.edu.net.NetworkInterface class
 */
public class NetworkUtils {
    /**
     * This method retrieves all up and non-loopback interfaces
     * @return list of available network interfaces 
     * @throws SocketException 
     */
    public static List<NetworkInterface> getAvailableInterfaces() throws SocketException {
        return Collections.list(NetworkInterface.getNetworkInterfaces())
                .stream()
                .filter(netIface -> {
                    try {
                        return netIface.isUp() && !netIface.isLoopback();
                    } catch (SocketException ex) {
                        Logger.getLogger(NetworkUtils.class.getName()).log(Level.SEVERE, null, ex);
                        return false;
                    }
                })
                .collect(Collectors.toList());
    }
    /**
     * Retrieves available @see java.ua.chmnu.edu.net.InetAddress list
     * @return available InetAddress list
     * @throws SocketException 
     */
    public static List<InetAddress> getAvailableInetAddresses() throws SocketException {
        return getAvailableInterfaces().stream()
                .flatMap(iface
                        -> iface.getInterfaceAddresses().stream().map(a -> a.getAddress()))
                .collect(Collectors.toList());
    }
    /**
     * Retrieves only available IPv4 addresses
     * @return list of available IPv4 addresses
     * @see Inet4Address
     * @throws SocketException
     */
    public static List<InetAddress> getAvailableIPv4Adresses() throws SocketException {
        return getAvailableInterfaces().stream()
                .flatMap(iface -> iface.getInterfaceAddresses().stream())
                .filter(ifaceAddr -> ifaceAddr.getBroadcast() instanceof Inet4Address)
                .map(ifaceAddr -> ifaceAddr.getAddress())
                .collect(Collectors.toList());
    }
    /**
     * Retrieves only available IPv6 addresses
     * @return list of available IPv6 addresses
     * @see Inet6Address
     * @throws SocketException
     */
    public static List<InetAddress> getAvailableIPv6Adresses() throws SocketException {
        return getAvailableInterfaces().stream()
                .flatMap(iface -> iface.getInterfaceAddresses().stream())
                .filter(ifaceAddr -> ifaceAddr.getBroadcast() instanceof Inet6Address)
                .map(ifaceAddr -> ifaceAddr.getAddress())
                .collect(Collectors.toList());
    }
    /**
     * Retrieves IPv4 addresses that support multicast
     * @return list of available IPv4 addresses with multicast support
     * @see NetworkInterface#supportsMulticast()
     * @throws SocketException 
     */
    public static List<InetAddress> getSupportedMulticastIPv4Addresses() throws SocketException {
        return getAvailableInterfaces().stream()
                .filter((NetworkInterface iface) -> {
                    try {
                        return iface.supportsMulticast();
                    } catch (SocketException ex) {
                        Logger.getLogger(NetworkUtils.class.getName()).log(Level.SEVERE, null, ex);
                        return false;
                    }
                })
                .flatMap(iface
                        -> iface.getInterfaceAddresses().stream().map(a -> a.getAddress())
                )
                .filter(a -> a instanceof Inet4Address)
                .collect(Collectors.toList());
    }    
}
