package ir.sharif.sad.repository;

import ir.sharif.sad.entity.Profession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository("professionRepository")
public interface ProfessionRepository extends JpaRepository<Profession, Integer> {
    Set<Profession> findByName(Iterable<String> strings);
}
