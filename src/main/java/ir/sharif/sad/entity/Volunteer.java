package ir.sharif.sad.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "volunteer")
@Data
public class Volunteer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String name;
    private String email;
    @OneToMany(mappedBy = "volunteer", cascade = CascadeType.ALL)
    private List<Payment> payments;
    @OneToMany(mappedBy = "volunteer", cascade = CascadeType.ALL)
    private List<WorkExperience> workExperiences;
}
