package ir.sharif.sad.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class FoundationOffer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @ManyToOne
    @JoinColumn(name = "charity_id")
    private Charity charity;
    @ManyToOne
    @JoinColumn(name = "volunteer_id")
    private Volunteer volunteer;
    private String description;
    private boolean expired;
}
