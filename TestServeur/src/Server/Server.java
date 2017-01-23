package Server;

/**
 *
 * @author Simon
 */
public class Server
{
	public Server()
	{
		ServerTCP serverTCP = new ServerTCP(65501);
		ServerUDP serverUDP = new ServerUDP(65500);
		serverUDP.start();
		serverTCP.start();
	}
}
