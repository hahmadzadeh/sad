package ir.sharif.sad.entity;


import ir.sharif.sad.dto.FoundationDto;
import ir.sharif.sad.dto.FoundationUserDto;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "foundation")
@Data
@NoArgsConstructor
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

    public Foundation(FoundationDto dto, String email) {
        this.name = dto.getName();
        this.email = email;
    }

    @Override
    public String toString() {
        return "Foundation{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
