package ir.sharif.sad.repository;

import ir.sharif.sad.entity.Volunteer;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface VolunteerRepository extends CustomRepository<Volunteer, Integer> {
    Optional<Volunteer> findOneByEmail(String email);

}
