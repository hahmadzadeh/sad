package ir.sharif.sad.entity;

import ir.sharif.sad.enumerators.Category;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class Profession {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "profession_id")
    private int id;
    private String name;
    private Category category;

    public Profession(String name, Category category) {
        this.name = name;
        this.category = category;
    }
}
