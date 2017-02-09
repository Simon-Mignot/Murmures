package epsi.projet.jicdsmdq.murmures.Network;

/**
 * Created by Simon on 09/02/2017.
 */
public class NetworkConstants
{
    /* MSG - Tag received from network */
    static final public int ANNOUNCEMENT_MSG = 0x01;
    static final public int HELLO_MSG = 0x02;
    static final public int GLOBAL_MESSAGE_MSG = 0x04;

    /* EVENT */
    static final public int HOST_DISCONNECT_EVENT = 0x01;

    /*  Misc */
    final static public int TCP_PORT = 65501;
    final static public int UDP_PORT = 65500;
    final static public int ANNOUNCEMENT_INTERVAL_MS = 10000;
    final static public int HELLO_WAITING_TIME_MS = 5000;
}
