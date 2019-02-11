package ir.sharif.sad.dto;

import lombok.Data;

@Data
public class AdminUserDto {
    private String name;
    private String lastName;
    private String password;
    private String email;
}
