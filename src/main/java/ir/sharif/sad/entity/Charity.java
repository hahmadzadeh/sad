package ir.sharif.sad.entity;


import lombok.Data;

import javax.persistence.*;


@Entity
@Data
public class Charity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @ManyToOne
    @JoinColumn(name = "foundation_id")
    private  Foundation foundation;
}
