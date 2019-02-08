package ir.sharif.sad.service;

import ir.sharif.sad.dto.AdminUserDto;
import ir.sharif.sad.entity.Role;
import ir.sharif.sad.entity.User;
import ir.sharif.sad.enumerators.Roles;
import ir.sharif.sad.repository.RoleRepository;
import ir.sharif.sad.repository.UserRepository;
import ir.sharif.sad.specification.Filter;
import ir.sharif.sad.specification.UserSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import java.lang.reflect.InvocationTargetException;
import java.util.Collections;
import java.util.HashSet;

@Service
public class AdminService {
    private final UserRepository userRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private final RoleRepository roleRepository;


    @Autowired
    public AdminService(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder
            , RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.roleRepository = roleRepository;
    }

    public User createAdmin(AdminUserDto dto, Roles role) {
        User user = new User();
        user.setPassword(bCryptPasswordEncoder.encode(dto.getPassword()));
        user.setActive(1);
        user.setName(dto.getName());
        user.setLastName(dto.getLastName());
        user.setEmail(dto.getEmail());
        Role userRole = roleRepository.findByRole(role.toString());
        user.setRoles(new HashSet<Role>(Collections.singletonList(userRole)));
        return userRepository.save(user);
    }

    public Page<User> readAll(Pageable page, Filter filter) {
        try {
            Specification specified = filter.getSpecified(UserSpecification.class);
            return userRepository.findAll(specified, page);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
        return null;
    }
}
