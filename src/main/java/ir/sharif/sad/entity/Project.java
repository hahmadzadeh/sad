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
    private long primaryMoney;
    private long paidSoFar;
    private String description;
    private Timestamp deadLine;
    private ProjectStatus status;

    @ManyToOne
    @JoinColumn(name = "foundation_id")
    private  Foundation foundation;

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL)
    private List<Payment> payments;

    public Project(ProjectDto dto, Foundation foundation){
        this.primaryMoney = dto.getPrimaryMoney();
        this.paidSoFar = 0;
        this.description = dto.getDescription();
        this.deadLine = dto.getDeadLine();
        this.foundation = foundation;
        this.status = ProjectStatus.NOT_FINISHED;
    }


    @Override
    public String toString() {
        return "Project{" +
                "id=" + id +
                ", money=" + primaryMoney +
                ", description='" + description + '\'' +
                ", deadLine=" + deadLine +
                '}';
    }

    public long getRemainingMoney(){
        return primaryMoney - paidSoFar;
    }
}

