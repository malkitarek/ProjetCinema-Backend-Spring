package org.sid.entities;

import org.springframework.data.rest.core.config.Projection;

import java.io.File;
import java.util.Collection;
import java.util.Date;

@Projection(name = "p1",types = {org.sid.entities.Projection.class})
public interface ProjectionProj {
    public Long getId();
    public Date getDateProjrction();
    public double getPrix();
    public  Salle getSalle();
    public Film getFilm();
    public Seance getSeance();
    public Collection<Ticket> getTickets();

}
