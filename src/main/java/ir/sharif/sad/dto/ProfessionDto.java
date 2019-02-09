package ir.sharif.sad.dto;

import ir.sharif.sad.entity.Profession;
import ir.sharif.sad.enumerators.Category;
import lombok.Data;

@Data
public class ProfessionDto {
    private String name;
    private Category category;

    public static ProfessionDto profession2ProfessionDto(Profession profession){
        ProfessionDto dto = new ProfessionDto();
        dto.name = profession.getName();
        dto.category = profession.getCategory();
        return dto;
    }
}
