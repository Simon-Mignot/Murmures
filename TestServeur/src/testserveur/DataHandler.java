/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testserveur;

import Server.ClientTCP;
import java.net.InetAddress;
import java.util.LinkedList;

/**
 *
 * @author Simon
 */
public class DataHandler
{
	static final public int ANNOUCEMENT_MSG = 0x01;
	static final public int HELLO_MSG = 0x02;
	static final public int GLOBAL_MESSAGE_MSG = 0x04;
	
	static final public int HOST_DISCONNECT_EVENT = 0x01;
	
	static public String name = "septimus";
	static private LinkedList<Host> knownHostList = new LinkedList<Host>();
	static private LinkedList<String> globalMessage = new LinkedList<String>();
	
	static public void networkMessage(int eventType, byte[] data, Object ip)
	{
		networkMessage(eventType, new String(data).substring(1), ip);
	}
	static public void networkMessage(int eventType, String data)
	{
		networkMessage(eventType, data, "");
	}
	static public void networkMessage(int eventType, String data, Object host)
	{
		switch(eventType)
		{
			case ANNOUCEMENT_MSG:
				System.out.println("NetworkEvent: " + data);
				receivedAnnoucementMessage(data, (InetAddress)host);
				break;
			
			case GLOBAL_MESSAGE_MSG:
				globalMessage.add(((Host)host).name + " : " + data);
				for(String s : globalMessage)
					System.out.println(s + '\n');
				break;
		}
	}
	
	static public void networkEvent(int eventType, Object data)
	{
		switch(eventType)
		{
			case HOST_DISCONNECT_EVENT:
				hostDisconnectEvent((Host)data);
				break;
		}
	}
	
	
	static private String getNewHostname(String name, byte[] ip)
	{
		int id = 0;
		for(int i = 0; i < 4; ++i)
			id += (Byte.toUnsignedInt(ip[i]) * (i + 1));
		return name + id;
	}
	
	
	static private void receivedAnnoucementMessage(String data, InetAddress ip)
	{
		String str_ip = ip.getHostAddress();
		for(Host host : knownHostList)
		{
			if(host.name.equals(data))
			{
				if(host.tcp.getIP().equals(str_ip))
				{
					host.resetKeepalive();
					return;
				}
				else
					data = getNewHostname(data, ip.getAddress());
			}
		}
		knownHostList.add(new Host(data, new ClientTCP(str_ip, 55056)));
	}
	
	static private void hostDisconnectEvent(Host host)
	{
		System.out.println("Remove : " + host.name);
		knownHostList.remove(host);
	}
}
