package ir.sharif.sad.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Data
@JsonIgnoreProperties(value = {"volunteer", "project"})
@NoArgsConstructor
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @ManyToOne
    @JoinColumn(name = "volunteer_id")
    private Volunteer volunteer;
    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;
    private Integer amount;
    private Timestamp timestamp;

    public Payment(Volunteer volunteer, Project project, Integer amount) {
        this.volunteer = volunteer;
        this.project = project;
        this.amount = amount;
        this.timestamp = new Timestamp(System.currentTimeMillis());
    }
}
