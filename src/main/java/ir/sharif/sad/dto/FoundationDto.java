package ir.sharif.sad.dto;

import ir.sharif.sad.entity.Foundation;
import lombok.Data;

@Data
public class FoundationDto {
    private String name;
    private String address;
    private String phone;
    private String aboutUs;
    public static FoundationDto Foundation2FoundationDto(Foundation foundation){
        FoundationDto dto = new FoundationDto();
        dto.name = foundation.getName();
        dto.aboutUs = foundation.getAboutUs();
        dto.address = foundation.getAddress();
        dto.phone = foundation.getPhone();
        return dto;
    }
}
