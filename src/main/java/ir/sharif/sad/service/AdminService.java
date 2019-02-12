package ir.sharif.sad.service;

import ir.sharif.sad.dto.AdminUserDto;
import ir.sharif.sad.dto.ProfessionDto;
import ir.sharif.sad.entity.Profession;
import ir.sharif.sad.entity.Role;
import ir.sharif.sad.entity.User;
import ir.sharif.sad.enumerators.Roles;
import ir.sharif.sad.repository.*;
import ir.sharif.sad.specification.Filter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

@Service
public class AdminService {
    private final Logger logger = LoggerFactory.getLogger(VolunteerService.class);
    private final UserRepository userRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private final RoleRepository roleRepository;
    private final ProjectRepository projectRepository;
    private final FoundationRepository foundationRepository;
    private final VolunteerRepository volunteerRepository;
    private final CharityRepository charityRepository;
    private final ProfessionRepository professionRepository;
    private final Map<Class, CustomRepository> repositoryMap;


    @Autowired
    public AdminService(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder
            , RoleRepository roleRepository, ProjectRepository projectRepository, FoundationRepository foundationRepository, VolunteerRepository volunteerRepository, CharityRepository charityRepository, ProfessionRepository professionRepository) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.roleRepository = roleRepository;
        this.projectRepository = projectRepository;
        this.foundationRepository = foundationRepository;
        this.volunteerRepository = volunteerRepository;
        this.charityRepository = charityRepository;
        this.professionRepository = professionRepository;
        this.repositoryMap = new HashMap<>();
        repositoryMap.put(UserRepository.class, userRepository);
        repositoryMap.put(ProjectRepository.class, projectRepository);
        repositoryMap.put(VolunteerRepository.class, volunteerRepository);
        repositoryMap.put(FoundationRepository.class, foundationRepository);
        repositoryMap.put(CharityRepository.class, charityRepository);
        repositoryMap.put(ProfessionRepository.class, professionRepository);
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

    public Profession createProfession(ProfessionDto dto){
        Profession profession = new Profession(dto.getName(), dto.getCategory());
        return professionRepository.save(profession);
    }

    public Page readAll(Pageable page, Filter filter, Class dataClass) {
        Specification specified = filter.getSpecified();
        if (repositoryMap.containsKey(dataClass)) {
            return repositoryMap.get(dataClass).findAll(specified, page);
        }
        return null;
    }
}
