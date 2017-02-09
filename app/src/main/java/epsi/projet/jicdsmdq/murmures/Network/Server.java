package epsi.projet.jicdsmdq.murmures.Network;

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

	static public void startNetwork()
	{
		initLocalAddresses();

		ServerTCP serverTCP = new ServerTCP(NetworkConstants.TCP_PORT);
		ServerUDP serverUDP = new ServerUDP(NetworkConstants.UDP_PORT);
		BroadcastUDP broadcastUDP = new BroadcastUDP(NetworkConstants.UDP_PORT, NetworkConstants.ANNOUNCEMENT_INTERVAL_MS);

		broadcastUDP.start();
		serverUDP.start();
		serverTCP.start();
	}

	static private void initLocalAddresses()
	{
        localAddresses = new ArrayList<>();
		Enumeration enumeration = getNetworkInterfaces();
		while(enumeration.hasMoreElements())
		{
			NetworkInterface n = (NetworkInterface)enumeration.nextElement();
			Enumeration inetAddresses = n.getInetAddresses();
			while(inetAddresses.hasMoreElements())
			{
				InetAddress i = (InetAddress)inetAddresses.nextElement();
				localAddresses.add(i.getHostAddress());
			}
		}
	}
    static private Enumeration getNetworkInterfaces()
    {
        try
        {
            return NetworkInterface.getNetworkInterfaces();
        }
        catch(SocketException e)
        {
            e.printStackTrace();
        }
        return null;
    }
}
