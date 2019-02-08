package ir.sharif.sad.dto;

import ir.sharif.sad.enumerators.Gender;
import lombok.Data;
import lombok.NonNull;

import java.util.List;


@Data
public class VolunteerDto {
    private String name;
    private Gender gender;
    private int age;
    private String province;
    private String city;
    private int district;
    private String phone;
    private String interests;
    @NonNull
    private List<AbilityDto> abilities;
}
