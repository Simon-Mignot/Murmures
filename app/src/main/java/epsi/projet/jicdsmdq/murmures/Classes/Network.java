package epsi.projet.jicdsmdq.murmures.Classes;


import android.util.Log;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.List;

/**
 * Created by Simon on 14/11/2016.
 */
public class Network
{
    private String interfaceName;
    private String ipAddress;
    private String broadcastAddress;
    private String macAddress;
    private short port;

    /*public Network()
    {
        try
        {
            Enumeration<NetworkInterface> l = NetworkInterface.getNetworkInterfaces();
            while(l.hasMoreElements())
            {
                NetworkInterface n = l.nextElement();
                String name = n.getDisplayName();
                if(name.startsWith("wlan") || name.startsWith("eth"))
                {
                    List<InterfaceAddress> addrs = n.getInterfaceAddresses();
                    for(int i = 0; i < addrs.size(); ++i)
                    {
                        InterfaceAddress addr = addrs.get(i);
                        try
                        {
                            Log.i("TEST", "Interface " + i);
                            Log.i("TEST", addr.toString());
                            Log.i("TEST", addr.getBroadcast().toString());
                            Log.i("TEST", addr.getAddress().toString());
                            Log.i("TEST", Short.toString(addr.getNetworkPrefixLength()));
                        }
                        catch(Exception e)
                        {

                        }
                        finally
                        {
                            Log.i("TEST", "-");
                        }
                    }
                }
            }
        }
        catch(Exception e)
        {

        }
    }*/


    public void test()
    {
        try
        {
            Log.i("TEST", "Logcat");
            Enumeration<NetworkInterface> l = NetworkInterface.getNetworkInterfaces();
            while(l.hasMoreElements())
            {
                NetworkInterface n = l.nextElement();
                String name = n.getDisplayName();
                Log.i("TEST", name + " : " + n.isLoopback());
                if(name.startsWith("wlan") || name.startsWith("eth"))
                {
                    Log.i("TEST", name);
                    Log.i("TEST", Utils.bytesToHex(n.getHardwareAddress()));
                    List<InterfaceAddress> addrs = n.getInterfaceAddresses();
                    for(int i = 0; i < addrs.size(); ++i)
                    {
                        InterfaceAddress addr = addrs.get(i);
                        try
                        {
                            Log.i("TEST", "Interface " + i);
                            Log.i("TEST", addr.toString());
                            Log.i("TEST", addr.getBroadcast().toString());
                            Log.i("TEST", addr.getAddress().toString());
                            Log.i("TEST", Short.toString(addr.getNetworkPrefixLength()));
                        }
                        catch(Exception e)
                        {

                        }
                        finally
                        {
                            Log.i("TEST", "-");
                        }
                    }
                }
            }
        }
        catch(SocketException e)
        {
            Log.e("TEST", "Exception : " + e.getMessage());
        }
    }
}
