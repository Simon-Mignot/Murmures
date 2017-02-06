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
	public Server()
	{
		initLocalAddresses();
		ServerTCP serverTCP = new ServerTCP(65501);
		ServerUDP serverUDP = new ServerUDP(65500);
		BroadcastUDP broadcastUDP = new BroadcastUDP(2000);
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
