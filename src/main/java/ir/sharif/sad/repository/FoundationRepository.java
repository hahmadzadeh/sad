package ir.sharif.sad.repository;

import ir.sharif.sad.entity.Foundation;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;


public interface FoundationRepository extends CrudRepository<Foundation, Integer> {
    Optional<Foundation> findOneByEmail(String email);
}
