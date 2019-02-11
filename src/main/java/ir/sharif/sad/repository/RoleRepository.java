package ir.sharif.sad.repository;

import ir.sharif.sad.entity.Role;

public interface RoleRepository extends CustomRepository<Role, Integer> {
    Role findByRole(String role);

}
