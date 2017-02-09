package epsi.projet.jicdsmdq.murmures.Handlers;

import java.net.InetAddress;

import epsi.projet.jicdsmdq.murmures.Classes.Host;
import epsi.projet.jicdsmdq.murmures.Classes.Message;
import epsi.projet.jicdsmdq.murmures.Network.NetworkConstants;

/**
 * Created by Simon on 09/02/2017.
 */
public class NetworkHandler
{
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
            case NetworkConstants.ANNOUCEMENT_MSG:
                DataHandler.receivedAnnouncementMessage(data, (InetAddress) host);
                break;

            case NetworkConstants.HELLO_MSG:
                ((Host)host).announcementName = data;
                DataHandler.setNameHelloMsg(((Host) host), data);
                break;

            case NetworkConstants.GLOBAL_MESSAGE_MSG:
                DataHandler.globalMessage.add(new Message((Host)host, data));
                MiscHandler.vibrate();
                break;
        }
        DataHandler.handler.post(DataHandler.updateUI);
    }

    static public void networkEvent(int eventType, Object data)
    {
        switch(eventType)
        {
            case NetworkConstants.HOST_DISCONNECT_EVENT:
                DataHandler.hostDisconnectEvent((Host) data);
                break;
        }
    }

    static public void networkSend(Message message)
    {
        //if(message.host == localhost)
        DataHandler.globalMessage.add(message);
        for(Host host : DataHandler.knownHostList)
        {
            if(host.tcp != null)
                host.tcp.sendMessage(message);
        }

    }

    static public void disconnect()
    {
        for(Host h : DataHandler.knownHostList)
            if(h.tcp != null)
                h.tcp.disconnect();
    }
}
