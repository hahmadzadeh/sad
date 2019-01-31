package ir.sharif.sad.entity;


import ir.sharif.sad.enumerators.Gender;
import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Set;


@Entity
@Data
public class Charity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String decription;

    @ManyToOne
    @JoinColumn(name = "foundation_id")
    private  Foundation foundation;

    private Gender gender;
    private int ageLowerBound;
    private int ageUpperBound;
    private String province;
    private String city;
    private int district;
    private Timestamp timeLowerBound;
    private Timestamp timeUpperBound;

    @ManyToMany
    @JoinTable(name = "charity_ability", joinColumns = @JoinColumn(name = "charity_id", referencedColumnName = "id")
            , inverseJoinColumns = @JoinColumn(name = "ability_id"))
    private Set<Ability> abilities;
}
