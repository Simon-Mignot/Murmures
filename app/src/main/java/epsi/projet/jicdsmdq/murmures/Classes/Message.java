package epsi.projet.jicdsmdq.murmures.Classes;

import java.util.Date;

/**
 * Created by Simon on 14/11/2016.
 */
public class Message
{
    String message;
    Date date;
    User user;

    public Message(User user, String text)
    {
        message = text;
        this.user = user;
    }

    public String getMessage() {
        return message;
    }

    public void setText(String text) {
        this.message = text;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString()
    {
        return user.getPseudo()+ ":" + getMessage();
    }
}
