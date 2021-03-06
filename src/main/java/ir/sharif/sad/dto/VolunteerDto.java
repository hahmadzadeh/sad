package ir.sharif.sad.dto;

import ir.sharif.sad.entity.Profession;
import ir.sharif.sad.entity.Volunteer;
import ir.sharif.sad.enumerators.Gender;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.util.List;
import java.util.stream.Collectors;


@Data
@NoArgsConstructor
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
    private List<String> professions;
    private int volunteerId;
    public static VolunteerDto volunteer2VolunteerDto(Volunteer volunteer){
        VolunteerDto volunteerDto = new VolunteerDto();
        volunteerDto.name = volunteer.getName();
        volunteerDto.gender = volunteer.getGender();
        volunteerDto.age = volunteer.getAge();
        volunteerDto.province = volunteer.getProvince();
        volunteerDto.city = volunteer.getCity();
        volunteerDto.district = volunteer.getDistrict();
        volunteerDto.phone = volunteer.getPhone();
        volunteerDto.interests = volunteer.getInterests();
        volunteerDto.professions = volunteer.getProfessions().parallelStream()
                .map(Profession::getName).collect(Collectors.toList());
        volunteerDto.volunteerId = volunteer.getId();
        return volunteerDto;
    }
}
