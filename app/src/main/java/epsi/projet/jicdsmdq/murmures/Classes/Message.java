package epsi.projet.jicdsmdq.murmures.Classes;

import java.util.Date;

/**
 * Created by Simon on 14/11/2016.
 */
public class Message
{
    String text;
    Date date;
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }


}
