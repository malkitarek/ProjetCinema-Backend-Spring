package org.sid.service;

import org.sid.dao.*;
import org.sid.entities.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.stream.Stream;
@Transactional
@Service
public class CinemaInitServiceImp implements ICenimaInitService {
    @Autowired
    private VilleRepository villeRepository;
    @Autowired
    private CinemaRepository cinemaRepository;
    @Autowired
    private SalleRepository salleRepository;
    @Autowired
    private PlaceRepository placeRepository;
    @Autowired
    private SeanceRepository seanceRepository;
    @Autowired
    private CategorieRepository categorieRepository;
    @Autowired
    private FilmRepository filmRepository;
    @Autowired
    private ProjectionRepository projectionRepository;
    @Autowired
    private TicketRepository ticketRepository;
    @Override
    public void initVille() {
        Stream.of("SBA","Oran","Alger","Mascara","Telemcan").forEach(v->{
            Ville ville=new Ville();
            ville.setName(v);
            villeRepository.save(ville);
        });
    }

    @Override
    public void initCinema() {
         villeRepository.findAll().forEach(v->{
             Stream.of("Vox","Amarna","Versay","Founoun").forEach(c->{
                 Cinema cinema=new Cinema();
                 cinema.setName(c);
                 cinema.setNombreSalles(3+ (int)(Math.random()*7));
                 cinema.setVille(v);
                 cinemaRepository.save(cinema);
             });
         });
    }

    @Override
    public void initSalle() {

        cinemaRepository.findAll().forEach(c->{
           for(int i=0; i< c.getNombreSalles();i++){
                Salle salle=new Salle();
                salle.setName("S"+i);
                salle.setNombrePlaces(15+(int)(Math.random()*20));
                salle.setCinema(c);
                salleRepository.save(salle);
            }
        });

    }

    @Override
    public void initPlace() {
        salleRepository.findAll().forEach(s->{
            for(int i=0; i< s.getNombrePlaces();i++){
                Place place=new Place();
                place.setNumero(i+1);
                place.setSalle(s);
                placeRepository.save(place);
            }
        });
    }

    @Override
    public void initSeance() {
        DateFormat dateFormat=new SimpleDateFormat("HH:mm");
           Stream.of("12:00","15:00","17:00","19:00","21:00").forEach(s->{
               try {
                   seanceRepository.save(new Seance(null,dateFormat.parse(s)));
               } catch (ParseException e) {
                   e.printStackTrace();
               }
           });
    }

    @Override
    public void initCategorie() {
        Stream.of("Histoire","Action","Drama","Remontique").forEach(c->{
            Categorie categorie=new Categorie();
            categorie.setName(c);
            categorieRepository.save(categorie);
        });

    }

    @Override
    public void initFilm() {
        double duree[]={1,1.5,2,2.5,3};
        categorieRepository.findAll().forEach(c->{
            Stream.of("Iron","Game of thronze","Dracula","Izel").forEach(f->{
                Film film =new Film();
                film.setTitre(f);
                film.setPhoto(f);
                film.setCategorie(c);
                film.setDuree(duree[new Random().nextInt(duree.length)]);
                filmRepository.save(film);
            });
        });
    }

    @Override
    public void initProjection() {
      List<Film> films=filmRepository.findAll();
      villeRepository.findAll().forEach(ville -> {
          ville.getCinemas().forEach(cinema -> {
             cinema.getSalles().forEach(salle->{
                      int index=new Random().nextInt(4);
                      seanceRepository.findAll().forEach(seance->{
                          Projection projection=new Projection();
                          projection.setDateProjrction(new Date());
                          projection.setFilm(films.get(index));
                          projection.setSalle(salle);
                          projection.setSeance(seance);
                          projection.setPrix(Math.random()*100);
                          projectionRepository.save(projection);
                      });

              });
          });
          });


    }

    @Override
    public void initTicket() {
             projectionRepository.findAll().forEach(projection -> {
                 projection.getSalle().getPlaces().forEach(place -> {
                     Ticket ticket=new Ticket();
                     ticket.setPlace(place);
                     ticket.setProjection(projection);
                     ticket.setPrix(projection.getPrix());
                     ticket.setReserve(false);
                     ticketRepository.save(ticket);
                 });
             });
    }
}
