package ir.sharif.sad.repository;

import ir.sharif.sad.entity.User;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;


public interface UserRepository extends CustomRepository<User, Long>, JpaSpecificationExecutor<User> {
    User findByEmail(String email);
}