package ir.sharif.sad.dto;

import ir.sharif.sad.entity.User;
import lombok.Data;

@Data
public class FoundationUserDto {
    private String name;
    private String email;
    private String password;

    public User foundationUserDto2User(){
        User user = new User();
        user.setActive(1);
        user.setPassword(this.password);
        user.setEmail(this.email);
        user.setName(this.name);
        return user;
    }
}
