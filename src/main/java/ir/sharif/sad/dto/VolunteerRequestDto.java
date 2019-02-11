package ir.sharif.sad.dto;


import ir.sharif.sad.entity.VolunteerRequest;
import ir.sharif.sad.enumerators.State;
import lombok.Data;
import org.springframework.lang.Nullable;

@Data
public class VolunteerRequestDto {
    private String description;
    private Integer charityId;
    @Nullable
    private Integer volunteerId;
    @Nullable
    private State state;

    public static VolunteerRequestDto volunteerRequest2VolunteerRequestDto(VolunteerRequest request){
        VolunteerRequestDto dto = new VolunteerRequestDto();
        dto.description = request.getDescription();
        dto.charityId = request.getCharity().getId();
        dto.volunteerId = request.getVolunteer().getId();
        dto.state = request.getState();
        return dto;
    }
}
