package ir.sharif.sad;

import ir.sharif.sad.entity.Profession;
import ir.sharif.sad.entity.Role;
import ir.sharif.sad.entity.User;
import ir.sharif.sad.enumerators.Category;
import ir.sharif.sad.enumerators.Roles;
import ir.sharif.sad.repository.ProfessionRepository;
import ir.sharif.sad.repository.RoleRepository;
import ir.sharif.sad.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@SpringBootApplication
public class SadApplication implements CommandLineRunner {
	private final RoleRepository roleRepository;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;
	private final UserRepository userRepository;
	private final ProfessionRepository professionRepository;

	@Autowired
	public SadApplication(RoleRepository roleRepository, BCryptPasswordEncoder bCryptPasswordEncoder, UserRepository userRepository, ProfessionRepository professionRepository) {
		this.roleRepository = roleRepository;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
		this.userRepository = userRepository;
		this.professionRepository = professionRepository;
	}

	public static void main(String[] args) {
		SpringApplication.run(SadApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		List<Role> all = roleRepository.findAll();
		if(all.isEmpty()){
			roleRepository.save(new Role("ROLE_ADMIN"));
			roleRepository.save(new Role("ROLE_FOUNDATION"));
			roleRepository.save(new Role("ROLE_VOLUNTEER"));
		}
		User admin = userRepository.findByEmail("admin");
		if(admin == null){
			User user = new User();
			user.setId(1);
			user.setEmail("admin");
			user.setName("admin");
			user.setActive(1);
			user.setLastName("admini");
			Role userRole = roleRepository.findByRole(Roles.ADMIN.toString());
			Set<Role> roleSet = new HashSet<>();
			roleSet.add(userRole);
			user.setRoles(roleSet);
			user.setPassword(bCryptPasswordEncoder.encode("nimda"));
			userRepository.saveAndFlush(user);
		}
		List<Profession> professions = professionRepository.findAll();
		if(professions.isEmpty()){
			professionRepository.save(new Profession("نجاری", Category.TECH));
			professionRepository.save(new Profession("نظافت", Category.GENERAL));
			professionRepository.save(new Profession("زبان", Category.ACADEM));
		}
	}
}

