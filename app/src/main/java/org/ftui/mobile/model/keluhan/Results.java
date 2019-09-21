
package org.ftui.mobile.model.keluhan;

import java.io.Serializable;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Results implements Serializable
{

    @SerializedName("ticket")
    @Expose
    private List<Ticket> ticket = null;
    private final static long serialVersionUID = 1538989712742374163L;

    public List<Ticket> getTicket() {
        return ticket;
    }

    public void setTicket(List<Ticket> ticket) {
        this.ticket = ticket;
    }

}
