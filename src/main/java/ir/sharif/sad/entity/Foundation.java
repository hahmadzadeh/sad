package ir.sharif.sad.entity;


import ir.sharif.sad.dto.FoundationUserDto;
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
    private String email;
    @OneToMany(mappedBy = "foundation", cascade = CascadeType.ALL)
    private List<Charity> charities;
    @OneToMany(mappedBy = "foundation", cascade = CascadeType.ALL)
    private List<Project> projects;

    public Foundation(FoundationUserDto dto, String email) {
        this.name = dto.getName();
        this.email = email;
    }
}
