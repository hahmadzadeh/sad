package ir.sharif.sad.repository;

import ir.sharif.sad.entity.Charity;
import ir.sharif.sad.enumerators.ProjectStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.sql.Timestamp;

public interface CharityRepository extends CustomRepository<Charity, Integer> {
    Page<Charity> findByStatusAndTimeUpperBoundGreaterThanEqual
            (ProjectStatus status, Timestamp current, Pageable pageRequest);
}
