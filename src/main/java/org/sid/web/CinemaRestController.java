package org.sid.web;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.sid.dao.FilmRepository;
import org.sid.dao.TicketRepository;
import org.sid.entities.Film;
import org.sid.entities.Ticket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.print.attribute.standard.Media;
import java.io.File;
import java.io.FileReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin("*")
public class CinemaRestController {

    @Autowired
    private FilmRepository filmRepository;
    @Autowired
    private TicketRepository ticketRepository;

    @GetMapping(path = "imageFilm/{id}",produces = MediaType.IMAGE_JPEG_VALUE)
    byte[] image(@PathVariable (name = "id")Long id) throws Exception{
       Film film= filmRepository.findById(id).get();
       String photo=film.getPhoto();
        File file=new File("E:/projets-Intelliji/cinema-App-Spring-Angular/images/"+photo+".jpg");
        return Files.readAllBytes(Paths.get(file.toURI()));
    }
    @PostMapping("/payerTickets")
    List<Ticket> payerTicket(@RequestBody TicketForm ticketForm){
        List<Ticket> ticketList=new ArrayList<>();
        ticketForm.getTicketId().forEach(id->{
       Ticket ticket= ticketRepository.findById(id).get();
       ticket.setNomClient(ticketForm.getNomClient());
       ticket.setCodePayemet(ticketForm.getCodePayement());
       ticket.setReserve(true);
       ticketList.add(ticket);
       ticketRepository.save(ticket);
        });
      return ticketList;
    }
    @PostMapping("/payerTickets2")
    List<Ticket> payerTicket2(@RequestBody TicketForm ticketForm){
        System.out.println(ticketForm);
        List<Ticket> ticketList=new ArrayList<>();
        ticketList=ticketRepository.findTicketsByReserveFalse();
                    for(int i=0;i<ticketForm.getNbrTickets();i++){
                        Ticket ticket1= ticketRepository.findById(ticketList.get(i).getId()).get();
                        ticket1.setNomClient(ticketForm.getNomClient());
                        ticket1.setCodePayemet(ticketForm.getCodePayement());
                        ticket1.setReserve(true);
                        ticketRepository.save(ticket1);
                    }



        return ticketList;
    }

}
@Data
class TicketForm{
    private String nomClient;
    private int codePayement;
    private int nbrTickets;
    private List<Long> ticketId=new ArrayList<>();

}
