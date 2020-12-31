package org.sid;

import org.sid.entities.*;
import org.sid.service.ICenimaInitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;

@SpringBootApplication
public class CinemaBackApplication implements CommandLineRunner {
    @Autowired
    ICenimaInitService iCenimaInitService;
    @Autowired
    private RepositoryRestConfiguration repositoryRestConfiguration;

    public static void main(String[] args) {
        SpringApplication.run(CinemaBackApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        repositoryRestConfiguration.exposeIdsFor(Film.class, Salle.class,
                Ticket.class, Ville.class, Cinema.class,Seance.class,Place.class,Categorie.class);
        iCenimaInitService.initVille();
        iCenimaInitService.initCinema();
        iCenimaInitService.initSalle();
        iCenimaInitService.initPlace();
        iCenimaInitService.initSeance();
        iCenimaInitService.initCategorie();
        iCenimaInitService.initFilm();
        iCenimaInitService.initProjection();
        iCenimaInitService.initTicket();
    }
}
