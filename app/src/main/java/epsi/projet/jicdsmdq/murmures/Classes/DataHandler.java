/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package epsi.projet.jicdsmdq.murmures.Classes;

import java.net.InetAddress;
import java.util.LinkedList;

import epsi.projet.jicdsmdq.murmures.Server.ClientTCP;

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

	static public Host localhost;
	static private LinkedList<Host> knownHostList = new LinkedList<Host>();
	static public LinkedList<Message> globalMessage = new LinkedList<Message>();
	
	static public void init(Host _localhost)
	{
		localhost = _localhost;
		knownHostList.add(localhost);
	}
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
				globalMessage.add(new Message(localhost, "NetworkEvent" + data));
				break;
			
			case GLOBAL_MESSAGE_MSG:
				globalMessage.add(new Message((Host)host, data));
				for(Message m : globalMessage)
					System.out.println((m.host.name == localhost.name ? ">" : "<") + m.toString() + '\n');
				break;
		}
	}

	static public void networkSend(Message message)
	{
		//if(message.host == localhost)
		globalMessage.add(message);
		for(Host host : knownHostList)
		{
			if(host.tcp != null)
				host.tcp.sendMessage(message);
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
			id += (/*Byte.toUnsignedInt*/(ip[i]) * (i + 1));
		for(char c : name.toCharArray())
			id += (int)c;
		return name + '-' + String.format("%08X", id);
	}
	
	
	static private void receivedAnnoucementMessage(String data, InetAddress ip)
	{
		String str_ip = ip.getHostAddress();
		for(Host host : knownHostList)
		{
			if(host == localhost)
				data = getNewHostname(data, ip.getAddress());
			else if(host.name.equals(data))
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
