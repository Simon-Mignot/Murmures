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
    private int port;
    private int interval;
    private DatagramPacket udpPacket;
    private DatagramSocket udpSocket;

    public BroadcastUDP(int port, int interval)
    {
        this.interval = interval;
        this.port = port;
        this.udpPacket = initPacket();
    }

    @Override
    public void run()
    {
        while(true)
        {
            try
            {
                sendBroadcast();
                sleep(interval);
            }
            catch(InterruptedException | IOException e)
            {
                e.printStackTrace();
            }
        }
    }

    private DatagramPacket initPacket()
    {
        try
        {
            String msg = Character.toString((char)NetworkConstants.ANNOUNCEMENT_MSG) + DataHandler.localhost.name;
            byte[] data = msg.getBytes();
            udpSocket = new DatagramSocket();
            return new DatagramPacket(data, data.length, InetAddress.getByName("255.255.255.255"), port);
        }
        catch(UnknownHostException | SocketException e)
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
        udpSocket.send(udpPacket);
    }
}
