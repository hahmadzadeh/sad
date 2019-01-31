package ir.sharif.sad.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "ability")
public class Ability {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ability_id")
    private int id;
    private String name;
}
