package ir.sharif.sad.dto;

import ir.sharif.sad.entity.Project;
import ir.sharif.sad.enumerators.ProjectStatus;
import lombok.Data;
import org.springframework.lang.Nullable;

import java.sql.Timestamp;

@Data
public class ProjectDto {
    private long primaryMoney;
    private long paidSoFar;
    private String description;
    private Timestamp deadLine;
    @Nullable
    private ProjectStatus projectStatus;
    private String foundationName;
    private Integer projectID;

    public static ProjectDto project2ProjectDto(Project project){
        ProjectDto dto = new ProjectDto();
        dto.projectStatus = project.getStatus();
        dto.deadLine = new Timestamp(project.getDeadLine());
        dto.description = project.getDescription();
        dto.primaryMoney = project.getPrimaryMoney();
        dto.paidSoFar = project.getPaidSoFar();
        dto.foundationName = project.getFoundation().getName();
        dto.projectID = project.getId();
        return dto;
    }
}
