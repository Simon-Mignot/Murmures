/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testserveur;

import Server.ClientTCP;
import java.util.LinkedList;

/**
 *
 * @author Simon
 */
public class DataHandler
{
	static final public int ANNOUCEMENT_MSG = 0x01;
	static final public int NEW_GLOBAL_MESSAGE = 0x02;
	
	static final public int HOST_DISCONNECT_EVENT = 0x128;
	
	static private LinkedList<Host> knownHostList = new LinkedList();
	//static public ArrayList
	
	static public void networkEvent(int eventType, byte[] data, String ip)
	{
		networkEvent(eventType, new String(data).substring(1), ip);
	}
	static public void networkEvent(int eventType, Object data)
	{
		networkEvent(eventType, data, "");
	}
	static private void networkEvent(int eventType, Object data, String ip)
	{
		switch(eventType)
		{
			case ANNOUCEMENT_MSG:
				System.out.println("NetworkEvent: " + data);
				receivedAnnoucementMessage((String)data, ip);
				break;
			
			case HOST_DISCONNECT_EVENT:
				hostDisconnectEvent((Host)data);
				break;
		}
	}
	
	
	static private void receivedAnnoucementMessage(String data, String ip)
	{
		for(Host host : knownHostList)
		{
			if(host.name.equals(data))
			{
				if(host.tcp.getIP().equals(ip))
				{
					host.resetKeepalive();
					return;
				}
				else
					data = data + ip;
			}
		}
		knownHostList.add(new Host(data, new ClientTCP(ip, 55056)));
	}
	
	static private void hostDisconnectEvent(Host host)
	{
		System.out.println("Remove : " + host.name);
		knownHostList.remove(host);
	}
	
	
}
