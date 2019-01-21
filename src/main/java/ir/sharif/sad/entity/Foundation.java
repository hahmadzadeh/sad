package ir.sharif.sad.entity;



import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "foundation")
@Data
public class Foundation {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String name;
    @OneToMany(mappedBy = "foundation", cascade = CascadeType.ALL)
    private List<Charity> charities;
    @OneToMany(mappedBy = "foundation", cascade = CascadeType.ALL)
    private List<Project> projects;

    public Foundation(String name) {
        this.name = name;
    }
}
