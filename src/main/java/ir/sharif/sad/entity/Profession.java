package ir.sharif.sad.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Profession {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "profession_id")
    private int id;
    private String name;
}
