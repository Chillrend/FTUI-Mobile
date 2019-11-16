
package org.ftui.mobile.model.singlekeluhan;

import com.google.gson.annotations.Expose;
import org.ftui.mobile.model.keluhan.Ticket;

@SuppressWarnings("unused")
public class Results {

    @Expose
    private Ticket ticket;

    public Ticket getTicket() {
        return ticket;
    }

    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }

}
