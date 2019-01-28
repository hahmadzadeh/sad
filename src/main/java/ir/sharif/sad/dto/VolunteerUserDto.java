package ir.sharif.sad.dto;

import ir.sharif.sad.entity.User;
import lombok.Data;

@Data
public class VolunteerUserDto {
    private String name;
    private String lastName;
    private String password;
    private String email;

    public User volunteerUserDto2User(){
        User user = new User();
        user.setActive(1);
        user.setPassword(this.password);
        user.setEmail(this.email);
        user.setLastName(this.lastName);
        user.setName(this.name);
        return user;
    }
}
