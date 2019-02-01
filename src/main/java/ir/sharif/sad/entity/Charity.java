package ir.sharif.sad.entity;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import ir.sharif.sad.dto.CharityDto;
import ir.sharif.sad.enumerators.Gender;
import ir.sharif.sad.enumerators.ProjectStatus;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Set;


@Entity
@Data
@JsonIgnoreProperties(value = {"foundation"})
@NoArgsConstructor
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
    private Timestamp timeLowerBound;
    private Timestamp timeUpperBound;
    private ProjectStatus status;

    public Charity(CharityDto dto, Foundation foundation, Set<Profession> professions){
        this.foundation = foundation;
        this.description = dto.getDescription();
        this.gender = dto.getGender();
        this.ageLowerBound = dto.getAgeLowerBound();
        this.ageUpperBound = dto.getAgeUpperBound();
        this.province = dto.getProvince();
        this.city = dto.getCity();
        this.district = dto.getDistrict();
        this.timeLowerBound = dto.getTimeLowerBound();
        this.timeUpperBound = dto.getTimeUpperBound();
        this.status = ProjectStatus.NOT_FINISHED;
        this.professions = professions;
    }

    @ManyToMany
    @JoinTable(name = "charity_ability", joinColumns = @JoinColumn(name = "charity_id", referencedColumnName = "id")
            , inverseJoinColumns = @JoinColumn(name = "profession_id"))
    private Set<Profession> professions;

    @Override
    public String toString(){
        return "Charity{" +
                "id = " + id +
                        ", description = " + description +
                        ", gender = " + gender +
                        ", ageLowerBound = " + ageLowerBound +
                        ", ageUpperBound = " + ageUpperBound +
                        ", province = " + province +
                        ", city = " + city +
                        ", district = " + district +
                        ", timeLowerBound = " + timeLowerBound +
                        ", timeUpperBound = " + timeUpperBound +
                        ", status = " + status + "}";
    }
}
