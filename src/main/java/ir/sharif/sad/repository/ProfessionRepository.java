package ir.sharif.sad.repository;

import ir.sharif.sad.entity.Profession;

import java.util.Optional;
import java.util.Set;

public interface ProfessionRepository extends CustomRepository<Profession, Integer> {
    Set<Profession> findByNameIn(Iterable<String> strings);
    Optional<Profession> findByName(String name);
}
