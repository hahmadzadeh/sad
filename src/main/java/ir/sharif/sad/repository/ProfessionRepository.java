package ir.sharif.sad.repository;

import ir.sharif.sad.entity.Profession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

public interface ProfessionRepository extends JpaRepository<Profession, Integer> {
    Set<Profession> findByNameIn(Iterable<String> strings);
}
