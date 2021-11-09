package ua.edu.chmnu.ki.networks.common;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.net.InetAddress;
import java.net.SocketException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;

@ExtendWith(MockitoExtension.class)
public class NetworkUtilsTest {

	@Test
	public void shouldSuccessRetrieveAvailableInetAddress() throws SocketException {
		List<InetAddress> addressList = NetworkUtils.getAvailableInetAddresses();
		assertFalse(addressList.isEmpty());
	}
}
