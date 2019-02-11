package ir.sharif.sad.repository;

import ir.sharif.sad.entity.Foundation;

import java.util.Optional;


public interface FoundationRepository extends CustomRepository<Foundation, Integer> {
    Optional<Foundation> findOneByEmail(String email);
}
