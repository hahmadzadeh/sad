package ir.sharif.sad.entity;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import ir.sharif.sad.dto.ProjectDto;
import ir.sharif.sad.enumerators.ProjectStatus;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

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
    private Timestamp deadLine;
    private ProjectStatus status;

    @ManyToOne
    @JoinColumn(name = "foundation_id")
    private  Foundation foundation;

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL)
    private List<Payment> payments;

    public Project(ProjectDto dto, Foundation foundation){
        this.money = dto.getMoney();
        this.description = dto.getDescription();
        this.deadLine = dto.getTimestamp();
        this.foundation = foundation;
        this.status = ProjectStatus.NOT_FINISHED;
    }


    @Override
    public String toString() {
        return "Project{" +
                "id=" + id +
                ", money=" + money +
                ", description='" + description + '\'' +
                ", deadLine=" + deadLine +
                '}';
    }
}

