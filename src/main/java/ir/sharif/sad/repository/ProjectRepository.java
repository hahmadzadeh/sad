package ir.sharif.sad.repository;

import ir.sharif.sad.entity.Project;
import ir.sharif.sad.enumerators.ProjectStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.sql.Timestamp;


public interface ProjectRepository extends PagingAndSortingRepository<Project, Integer> {
     Page<Project> findByStatusAndDeadLineGreaterThanEqual
            (ProjectStatus status, Timestamp current, Pageable pageRequest);
}
