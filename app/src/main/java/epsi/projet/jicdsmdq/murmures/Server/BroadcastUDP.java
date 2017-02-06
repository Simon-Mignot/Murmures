package epsi.projet.jicdsmdq.murmures.Server;

import android.util.Log;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import epsi.projet.jicdsmdq.murmures.Classes.DataHandler;

/**
 * Created by Simon on 06/02/2017.
 */
public class BroadcastUDP extends Thread
{
    private int interval = 2000;
    private DatagramPacket udp;
    public BroadcastUDP(int _interval)
    {
        interval = _interval;
        udp = initPacket();
    }
    @Override
    public void run()
    {
        System.out.println("run()");
        while(true)
        {

            try {
                sleep(interval);
                sendBroadcast();
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
            String msg = DataHandler.ANNOUCEMENT_MSG + DataHandler.localhost.name;
            byte[] data = msg.getBytes();
            return new DatagramPacket(data, data.length, InetAddress.getByName("255.255.255.255"), 65500);
        }
        catch(UnknownHostException e)
        {
            System.out.println("init " + e.getMessage());
        }
        return null;
    }

    private void sendBroadcast() throws IOException
    {
        DatagramSocket d = new DatagramSocket(65501);
        d.send(udp);
    }
}
