package ir.sharif.sad.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import ir.sharif.sad.dto.AbilityDto;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "ability")
@JsonIgnoreProperties(value = {"volunteer"})
public class Ability {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ability_id")
    private int id;
    private String profession;
    private String freeTime;
    private String province;
    private String city;
    private int district;
    @ManyToOne
    @JoinColumn(name = "volunteer_id")
    private Volunteer volunteer;

    public Ability(AbilityDto dto, Volunteer volunteer){
        this.profession = dto.getProfession();
        this.freeTime = dto.getFreeTime();
        this.province = dto.getProvince();
        this.city = dto.getCity();
        this.district = dto.getDistrict();
        this.volunteer = volunteer;
    }
}
