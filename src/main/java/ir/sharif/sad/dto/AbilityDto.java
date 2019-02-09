package ir.sharif.sad.dto;

import ir.sharif.sad.entity.Ability;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
public class AbilityDto {
    private String profession;
    private String freeTime;
    private String province;
    private String city;
    private List<Integer> districts;

    public static AbilityDto ability2AbilityDto(Ability ability) {
        AbilityDto abilityDto = new AbilityDto();
        abilityDto.profession = ability.getProfession().getName();
        abilityDto.freeTime = ability.getFreeTime();
        abilityDto.province = ability.getProvince();
        abilityDto.city = ability.getCity();
        abilityDto.districts = Arrays.stream(ability.getDistrict().split("#"))
                .map(Integer::parseInt).collect(Collectors.toList());
        return abilityDto;
    }
}
