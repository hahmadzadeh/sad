package ir.sharif.sad.entity;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import ir.sharif.sad.dto.CharityDto;
import ir.sharif.sad.enumerators.Gender;
import ir.sharif.sad.enumerators.ProjectStatus;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;
import java.util.Set;


@Entity
@Data
@JsonIgnoreProperties(value = {"foundation"})
@NoArgsConstructor
@ToString(exclude = {"foundation"})
public class Charity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String description;

    @ManyToOne
    @JoinColumn(name = "foundation_id")
    private  Foundation foundation;

    private Gender gender;
    private int ageLowerBound;
    private int ageUpperBound;
    private String province;
    private String city;
    private int district;
    private long deadLine;
    private long startTime;
    private ProjectStatus status;
    private long numWeeks;
    private String schedule;

    public Charity(CharityDto dto, Foundation foundation, Set<Profession> professions){
        this.foundation = foundation;
        this.description = dto.getDescription();
        this.gender = dto.getGender();
        this.ageLowerBound = dto.getAgeLowerBound();
        this.ageUpperBound = dto.getAgeUpperBound();
        this.province = dto.getProvince();
        this.city = dto.getCity();
        this.district = dto.getDistrict();
        this.deadLine = dto.getDeadLine().getTime();
        this.startTime = dto.getStartTime().getTime();
        this.status = ProjectStatus.NOT_FINISHED;
        this.professions = professions;
        this.schedule = dto.getSchedule();
        this.numWeeks = dto.getNumWeeks();
    }

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "charity_profession", joinColumns = @JoinColumn(name = "charity_id"), inverseJoinColumns = @JoinColumn(name = "profession_id"))
    private Set<Profession> professions;
    @OneToMany(mappedBy = "charity", cascade = CascadeType.ALL)
    private List<VolunteerRequest> requests;
}
