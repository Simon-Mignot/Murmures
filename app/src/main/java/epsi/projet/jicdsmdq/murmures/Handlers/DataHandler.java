package epsi.projet.jicdsmdq.murmures.Handlers;

import android.os.Handler;

import java.io.IOException;
import java.net.InetAddress;
import java.util.Date;
import java.util.LinkedList;

import epsi.projet.jicdsmdq.murmures.Activities.HomeActivity;
import epsi.projet.jicdsmdq.murmures.Classes.Host;
import epsi.projet.jicdsmdq.murmures.Classes.Message;
import epsi.projet.jicdsmdq.murmures.Network.ClientTCP;
import epsi.projet.jicdsmdq.murmures.Network.NetworkConstants;

/**
 *
 * @author Simon
 */
public class DataHandler
{
    static public Host localhost;
	static public LinkedList<Host> knownHostList = new LinkedList<>();
	static public LinkedList<Message> globalMessage = new LinkedList<>();

    static private HomeActivity homeActivity;

	static final Handler handler = new Handler();
	static final Runnable updateUI = new Runnable()
	{
		@Override
		public void run()
		{
			if(homeActivity != null)
				homeActivity.refresh();
		}
	};

	static public void init(Host localhost, HomeActivity homeActivity)
	{
        DataHandler.homeActivity = homeActivity;
        DataHandler.localhost = localhost;
		knownHostList.add(localhost);
	}

    static public void setGlobalMessagesRead()
    {
        new Thread()
        {
            @Override
            public void run()
            {
                try {
                    Thread.sleep(5000);
                    for(Message msg : globalMessage)
                        msg.read = true;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
    }
    static public int countUnreadGlobalMessages()
    {
        int count = 0;
        for(Message msg : globalMessage)
            if(!msg.read)
                ++count;
        return count;
    }


	static private String generateAltName(String name, byte[] ip)
	{
		int id = 0;
		for(int i = 0; i < 4; ++i)
			id += (/*Byte.toUnsignedInt*/(ip[i]) * (i + 1));
		for(char c : name.toCharArray())
			id += (int)c;
		return name + '-' + String.format("%08X", id);
	}

	static private boolean hostIsWaitingHello(Host host, String ip)
	{
		Date before = new Date();
		before.setTime(before.getTime() - NetworkConstants.HELLO_WAITING_TIME_MS);

		return (host.tcp.getIP().equals(ip)
 			 && host.name.length() == 0
			 );
	}


    static protected void setNameHelloMsg(Host host, String name)
    {
        for(Host h : knownHostList)
        {
            if(h.announcementName.equals(name) && h.name.length() > 0)
            {
                host.name = generateAltName(name, host.tcp.getInetAddress().getAddress());
                return;
            }
        }
        host.name = name;
    }
	
	static protected void receivedAnnouncementMessage(String data, InetAddress ip)
	{
		String str_ip = ip.getHostAddress();
        String altName = generateAltName(data, ip.getAddress());
        boolean isAltName = false;
		for(Host host : knownHostList)
		{
            /*Log.e("DEBUG", host.announcementName + " == " + data + " ? " + host.announcementName.equals(data) + "(" + (host.tcp == null ? "null" : host.tcp.getIP()) + ")");
            Log.e("DEBUG", host.announcementName + " " + host.name);
            Log.e("DEBUG", Integer.toString(host.announcementName.length()));
            Log.e("DEBUG", " ");*/
            if(host.announcementName.equals(data))
            {
                if(host.tcp != null && host.tcp.getIP().equals(str_ip))
                {
                    host.resetKeepalive();
                    return;
                }
                else// if(host.tcp != null)
                {
                    isAltName = true;
                }
            }
            else if(host == localhost)
                continue;
            else if(hostIsWaitingHello(host, str_ip))
                return;
		}

		try
		{
            Host newHost;
            ClientTCP tcpClient = new ClientTCP(str_ip);
            if(isAltName)
                newHost = new Host(data, altName, tcpClient);
            else
                newHost = new Host(data, tcpClient);
			knownHostList.add(newHost);
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	static protected void hostDisconnectEvent(Host host)
	{
		System.out.println("Remove : " + host.name);
		knownHostList.remove(host);
	}
}