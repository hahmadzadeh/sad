package ir.sharif.sad.entity;

import ir.sharif.sad.dto.VolunteerDto;
import ir.sharif.sad.enumerators.Gender;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "volunteer")
@Data
@NoArgsConstructor
public class Volunteer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String name;
    private String email;
    private Gender gender;
    private int age;
    private String province;
    private String city;
    private int district;
    private String phone;
    private String interests;

    @OneToMany(mappedBy = "volunteer", cascade = CascadeType.ALL)
    private List<Payment> payments;
    @OneToMany(mappedBy = "volunteer", cascade = CascadeType.ALL)
    private List<WorkExperience> workExperiences;

    @OneToMany
    private Set<Ability> abilities;

    public Volunteer(VolunteerDto dto, String email) {
        this.name = dto.getName();
        this.email = email;
        this.gender = dto.getGender();
        this.age = dto.getAge();
        this.province = dto.getProvince();
        this.city = dto.getCity();
        this.district = dto.getDistrict();
        this.phone = dto.getPhone();
        this.interests = dto.getInterests();
        this.abilities = dto.getAbilities();
    }

}
