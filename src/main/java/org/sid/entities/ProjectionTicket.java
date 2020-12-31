package org.sid.entities;

import org.springframework.data.rest.core.config.Projection;

import java.util.Collection;
import java.util.Date;

@Projection(name = "ticketProj",types = Ticket.class)
public interface ProjectionTicket {
    public Long getId();
    public String getNomClient();
    public double getPrix();
    public  Integer getCodePayemet();
    public boolean getReserve();
    public Place getPlace();

}
