package epsi.projet.jicdsmdq.murmures.Network;

import android.util.Log;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import epsi.projet.jicdsmdq.murmures.Handlers.DataHandler;
import epsi.projet.jicdsmdq.murmures.Handlers.OptionsHandler;

/**
 * Created by Simon on 06/02/2017.
 */
public class BroadcastUDP extends Thread
{
    private int interval = 2000;
    private int port;
    private DatagramPacket udp;
    private DatagramSocket socket;

    public BroadcastUDP(int _port, int _interval)
    {
        interval = _interval;
        port = _port;
        udp = initPacket();
    }
    @Override
    public void run()
    {
        System.out.println("run()");
        while(true)
        {
            try {
                sendBroadcast();
                System.out.println(DataHandler.knownHostList);
                sleep(interval);
            } catch(InterruptedException e) {
                e.printStackTrace();
            } catch(IOException e) {
                e.printStackTrace();
            }
        }
    }

    private DatagramPacket initPacket()
    {
        System.out.println("initPacket()");
        try
        {
            String msg = Character.toString((char) NetworkConstants.ANNOUCEMENT_MSG) + DataHandler.localhost.name;
            byte[] data = msg.getBytes();
            socket = new DatagramSocket();
            return new DatagramPacket(data, data.length, InetAddress.getByName("255.255.255.255"), port);
        }
        catch(UnknownHostException e)
        {
            System.out.println("init " + e.getMessage());
        }
        catch (SocketException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    private void sendBroadcast() throws IOException
    {
        if(OptionsHandler.options_stalkerMode)
            return;
        Log.i("NETWORK", "OUT - sendBroadCast()");
        socket.send(udp);
    }
}
