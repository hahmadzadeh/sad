package ir.sharif.sad.dto;

import ir.sharif.sad.entity.Charity;
import ir.sharif.sad.entity.Profession;
import ir.sharif.sad.enumerators.Gender;
import lombok.Data;

import java.sql.Timestamp;
import java.util.Set;
import java.util.stream.Collectors;

@Data
public class CharityDto {
    private String description;
    private Gender gender;
    private int ageLowerBound;
    private int ageUpperBound;
    private String province;
    private String city;
    private int district;
    private Timestamp timeLowerBound;
    private Timestamp timeUpperBound;
    private Set<String> professions;
    private Integer foundationId;
    private long numWeeks;
    private String schedule;

    public static CharityDto charity2CharityDto(Charity charity){
        CharityDto dto = new CharityDto();
        dto.description = charity.getDescription();
        dto.gender = charity.getGender();
        dto.ageLowerBound = charity.getAgeLowerBound();
        dto.ageUpperBound = charity.getAgeUpperBound();
        dto.province = charity.getProvince();
        dto.city = charity.getCity();
        dto.district = charity.getDistrict();
        dto.timeLowerBound = new Timestamp(charity.getTimeLowerBound());
        dto.timeUpperBound = new Timestamp(charity.getTimeUpperBound());
        dto.professions = charity.getProfessions().parallelStream().map(Profession::getName).collect(Collectors.toSet());
        dto.foundationId = charity.getFoundation().getId();
        dto.numWeeks = charity.getNumWeeks();
        dto.schedule = charity.getSchedule();
        return dto;
    }
}
