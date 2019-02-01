package ir.sharif.sad.repository;

import ir.sharif.sad.entity.Volunteer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface VolunteerRepository extends JpaRepository<Volunteer, Integer> {
    Optional<Volunteer> findOneByEmail(String email);

}
