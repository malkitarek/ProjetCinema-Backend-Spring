package org.sid.entities;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
@Entity
@Data @NoArgsConstructor @AllArgsConstructor @ToString
public class Ticket implements Serializable {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 75)
    private String nomClient;
    private double prix;
    @Column(unique = false ,nullable = true)
    private Integer codePayemet;
    @Getter
    private boolean reserve;
    @ManyToOne
    private Place place;
    @ManyToOne
    private Projection projection;


}
