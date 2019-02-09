package ir.sharif.sad.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class AbilityDto {
    private String profession;
    private String freeTime;
    private String province;
    private String city;
    private List<Integer> districts;
}
