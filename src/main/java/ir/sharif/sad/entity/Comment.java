package ir.sharif.sad.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "comment_id")
    private int id;
    private String comment;
    @ManyToOne
    @JoinColumn(name = "foundation_id")
    private  Foundation foundation;
    @ManyToOne
    @JoinColumn(name = "volunteer_id")
    private Volunteer volunteer;
}
