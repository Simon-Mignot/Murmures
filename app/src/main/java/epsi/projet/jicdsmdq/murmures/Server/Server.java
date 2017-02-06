package epsi.projet.jicdsmdq.murmures.Server;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Enumeration;

/**
 *
 * @author Simon
 */
public class Server
{
	static public ArrayList<String> localAddresses;
	final static public int TCP_PORT = 65501;
	final static public int UDP_PORT = 65500;
	final static public int ANNOUCEMENT_INTERVAL = 10000;
	public Server()
	{
		initLocalAddresses();
		ServerTCP serverTCP = new ServerTCP(TCP_PORT);
		ServerUDP serverUDP = new ServerUDP(UDP_PORT);
		BroadcastUDP broadcastUDP = new BroadcastUDP(UDP_PORT, ANNOUCEMENT_INTERVAL);
		broadcastUDP.start();
		serverUDP.start();
		serverTCP.start();
	}

	static private void initLocalAddresses()
	{
		localAddresses = new ArrayList<>();
		Enumeration e = null;
		try
		{
			e = NetworkInterface.getNetworkInterfaces();
		} catch(SocketException e1)
		{
			e1.printStackTrace();
		}
		while(e.hasMoreElements())
		{
			NetworkInterface n = (NetworkInterface) e.nextElement();
			Enumeration ee = n.getInetAddresses();
			while (ee.hasMoreElements())
			{
				InetAddress i = (InetAddress) ee.nextElement();
				localAddresses.add(i.getHostAddress());
			}
		}
	}
}
