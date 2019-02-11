package ir.sharif.sad.dto;

import lombok.Data;

@Data
public class LoginDto {
    boolean success;

    public LoginDto(boolean success) {
        this.success = success;
    }
}
