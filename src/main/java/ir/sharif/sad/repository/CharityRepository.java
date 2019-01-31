package ir.sharif.sad.repository;

import ir.sharif.sad.entity.Charity;
import ir.sharif.sad.enumerators.ProjectStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.sql.Timestamp;

public interface CharityRepository extends PagingAndSortingRepository<Charity, Integer> {
    Page<Charity> findByStatusAndTimeUpperBoundGreaterThanEqual
            (ProjectStatus status, Timestamp current, Pageable pageRequest);
}
