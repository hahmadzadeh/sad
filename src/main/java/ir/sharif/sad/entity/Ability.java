package ir.sharif.sad.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import ir.sharif.sad.dto.AbilityDto;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "ability")
@JsonIgnoreProperties(value = {"volunteer", "profession"})
public class Ability {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ability_id")
    private int id;
    @ManyToOne
    @JoinColumn(name = "charity_id")
    private Profession profession;
    private String freeTime;
    private String province;
    private String city;
    private String district;
    @ManyToOne
    @JoinColumn(name = "volunteer_id")
    private Volunteer volunteer;

    public Ability(AbilityDto dto, Volunteer volunteer, Profession profession){
        this.profession = profession;
        this.freeTime = dto.getFreeTime();
        this.province = dto.getProvince();
        this.city = dto.getCity();
        this.district = dto.getDistricts().parallelStream().map(Object::toString).reduce((x, y)-> x + "#" + y).get();
        this.volunteer = volunteer;
    }
}
