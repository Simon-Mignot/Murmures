package epsi.projet.jicdsmdq.murmures.Classes;

/**
 * Created by Simon on 14/11/2016.
 */
public class User
{
    String pseudo;
    String id;
    String ip;

    public User(String pseudo){
        this.pseudo = pseudo;
    }

    public String getPseudo() {
        return pseudo;
    }

    public void setPseudo(String pseudo) {
        pseudo = pseudo;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }


}
