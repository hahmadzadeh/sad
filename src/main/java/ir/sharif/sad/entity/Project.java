package ir.sharif.sad.entity;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import ir.sharif.sad.dto.ProjectDto;
import ir.sharif.sad.enumerators.ProjectStatus;
import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Data
@JsonIgnoreProperties(value = {"foundation"})
@NoArgsConstructor
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private long money;
    private String description;
    private Timestamp timestamp;
    private ProjectStatus status;

    @ManyToOne
    @JoinColumn(name = "foundation_id")
    private  Foundation foundation;

    public Project(ProjectDto dto, Foundation foundation){
        this.money = dto.getMoney();
        this.description = dto.getDescription();
        this.timestamp = dto.getTimestamp();
        this.foundation = foundation;
        this.status = ProjectStatus.NOT_FINISHED;
    }


    @Override
    public String toString() {
        return "Project{" +
                "id=" + id +
                ", money=" + money +
                ", description='" + description + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }
}

