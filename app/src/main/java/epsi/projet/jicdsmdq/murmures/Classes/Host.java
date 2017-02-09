package epsi.projet.jicdsmdq.murmures.Classes;

import java.util.Date;

import epsi.projet.jicdsmdq.murmures.Server.ClientTCP;

/**
 *
 * @author Simon
 */
public class Host
{
	public String name;
    protected String annoucementName = "blop";
	protected ClientTCP tcp;
	private Date lastKeepalive;

	public Host(String name)
	{
		this.name = name;
        this.annoucementName = name;
		tcp = null;

	}
	public Host(String _name, ClientTCP _tcp)
	{
		System.out.println("new host : " + _name);
		name = _name;
        annoucementName = _name;
		tcp = _tcp;
		tcp.setHost(this);
		tcp.start();
		lastKeepalive = new Date();
	}

    public Host(String _name, String altName, ClientTCP _tcp)
    {
        System.out.println("new host : " + _name);
        name = altName;
        annoucementName = _name;
        tcp = _tcp;
        tcp.setHost(this);
        tcp.start();
        lastKeepalive = new Date();
    }

	public boolean isLocalhost()
	{
		return tcp == null;
	}
	
	public void resetKeepalive()
	{
		System.out.println(name + " : " + (new Date().getTime() - lastKeepalive.getTime())/1000.);
		lastKeepalive = new Date();
	}
	
	public byte[] toBytesArray()
	{
		String firstPart = new String(name + '\0');
		int firstPartLength = firstPart.length();
		byte[] out = new byte[firstPartLength + 4];
		System.arraycopy(firstPart.getBytes(), 0, out, 0, firstPartLength);
		System.arraycopy(tcp.getInetAddress().getAddress(), 0, out, firstPartLength, 4);
		return out;
	}
	
	@Override
	public String toString()
	{
		String ip = tcp == null ? "localhost" : tcp.getIP();
		long time = lastKeepalive != null ? ((new Date()).getTime() - lastKeepalive.getTime()) : -1;
		return (name + " - " + ip + " (" + time + ")");
	}
	@Override
	public boolean equals(Object o)
	{
		if(o != null && o instanceof Host)
			return (name.equals(((Host)o).name));
		return false;
	}
}