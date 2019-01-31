package ir.sharif.sad.dto;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class ProjectDto {
    private long money;
    private String description;
    private Timestamp timestamp;
}
