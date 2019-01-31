package ir.sharif.sad.entity;

import ir.sharif.sad.enumerators.Gender;
import lombok.Data;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "volunteer")
@Data
public class Volunteer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String name;
    private String email;
    private Gender gender;
    private int age;
    private String province;
    private String city;
    private int district;
    private String phone;
    private String interserts;

    @OneToMany(mappedBy = "volunteer", cascade = CascadeType.ALL)
    private List<Payment> payments;
    @OneToMany(mappedBy = "volunteer", cascade = CascadeType.ALL)
    private List<WorkExperience> workExperiences;

    @ManyToMany
    @JoinTable(name = "volunteer_ability", joinColumns = @JoinColumn(name = "volunteer_id", referencedColumnName = "id")
            , inverseJoinColumns = @JoinColumn(name = "ability_id"))
    private Set<Ability> abilities;
}
