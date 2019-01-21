package ir.sharif.sad.entity;


import javax.persistence.*;


@Entity
public class Charity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @ManyToOne
    @JoinColumn(name = "foundation_id")
    private  Foundation foundation;
}
