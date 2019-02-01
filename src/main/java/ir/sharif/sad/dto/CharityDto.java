package ir.sharif.sad.dto;

import ir.sharif.sad.enumerators.Gender;
import lombok.Data;

import java.sql.Timestamp;
import java.util.Set;

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
}
