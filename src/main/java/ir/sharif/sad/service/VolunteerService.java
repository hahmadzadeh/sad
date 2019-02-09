package ir.sharif.sad.service;

import ir.sharif.sad.dto.AbilityDto;
import ir.sharif.sad.exceptions.InvalidAmountException;
import ir.sharif.sad.dto.VolunteerDto;
import ir.sharif.sad.dto.VolunteerRequestDto;
import ir.sharif.sad.entity.*;
import ir.sharif.sad.enumerators.ProjectStatus;
import ir.sharif.sad.enumerators.State;
import ir.sharif.sad.repository.CharityRepository;
import ir.sharif.sad.repository.ProfessionRepository;
import ir.sharif.sad.repository.ProjectRepository;
import ir.sharif.sad.repository.VolunteerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.Optional;
import java.util.stream.Stream;

@Service
public class VolunteerService {
    private final Logger logger = LoggerFactory.getLogger(VolunteerService.class);
    @Value("${page.size}")
    private Integer pageSize;
    private final VolunteerRepository volunteerRepository;
    private final ProjectRepository projectRepository;
    private final CharityRepository charityRepository;
    private final ProfessionRepository professionRepository;

    @Autowired
    public VolunteerService(VolunteerRepository volunteerRepository, ProjectRepository projectRepository
            , CharityRepository charityRepository, ProfessionRepository professionRepository) {
        this.volunteerRepository = volunteerRepository;
        this.projectRepository = projectRepository;
        this.charityRepository = charityRepository;
        this.professionRepository = professionRepository;
    }


    public Volunteer save(VolunteerDto volunteerDto, String name) {
        Volunteer volunteer = new Volunteer(volunteerDto, name);
        volunteerRepository.save(volunteer);
        logger.info("create new volunteer");
        return volunteer;
    }

    @Transactional
    public Volunteer fillAbilities(VolunteerDto volunteerDto, Volunteer volunteer) {
        Stream<AbilityDto> abilityDtoStream = volunteerDto.getAbilities().stream()
                .filter(e -> professionRepository.findByName(e.getProfession()).isPresent());
        abilityDtoStream.forEach(e -> volunteer.getAbilities()
                .add(new Ability(e, volunteer, professionRepository.findByName(e.getProfession()).get())));
        return volunteer;
    }

    public Page<Project> readProjects(Integer page) {
        PageRequest pageRequest = new PageRequest(page, pageSize, Sort.Direction.ASC, "deadLine");
        Timestamp current = new Timestamp(System.currentTimeMillis());
        return projectRepository.findByStatusAndDeadLineGreaterThanEqual
                (ProjectStatus.NOT_FINISHED, current, pageRequest);
    }

    @Transactional
    public Payment makePayment(Integer amount, String name, Integer id) throws Exception {
        Optional<Volunteer> byEmail = volunteerRepository.findOneByEmail(name);
        Optional<Project> byId = projectRepository.findById(id);
        Timestamp current = new Timestamp(System.currentTimeMillis());
        if (byEmail.isPresent() && byId.isPresent() && byId.get().getDeadLine().after(current)) {
            Project project = byId.get();
            Volunteer volunteer = byEmail.get();
            if (amount <= project.getMoney() && amount > 0) {
                project.setMoney(project.getMoney() - amount);
                Payment payment = new Payment(volunteer, project, amount);
                project.getPayments().add(payment);
                volunteer.getPayments().add(payment);
                if (project.getMoney() == 0) {
                    project.setStatus(ProjectStatus.FINISHED);
                }
                return payment;
            } else {
                logger.error("Invalid amount of money(perhaps too much!!)");
                throw new InvalidAmountException("Invalid amount of money(perhaps too much!!)");
            }
        } else {
            throw new Exception("Volunteer not created please complete volunteer");
        }
    }

    public Page<Charity> readCharities(Integer page) {
        PageRequest pageRequest = new PageRequest(page, pageSize, Sort.Direction.ASC, "deadLine");
        Timestamp current = new Timestamp(System.currentTimeMillis());
        return charityRepository.findByStatusAndTimeUpperBoundGreaterThanEqual
                (ProjectStatus.NOT_FINISHED, current, pageRequest);
    }

    public Volunteer readOne(String name) throws Exception {
        Optional<Volunteer> byId = volunteerRepository.findOneByEmail(name);
        if (byId.isPresent()) {
            return byId.get();
        } else {
            throw new Exception("volunteer is not signed up yet");
        }
    }

    public VolunteerRequest makeRequest(VolunteerRequestDto dto, String name, Integer id) throws Exception {
        Optional<Charity> byId = charityRepository.findById(id);
        Optional<Volunteer> byEmail = volunteerRepository.findOneByEmail(name);
        if (byId.isPresent() && byEmail.isPresent()) {
            Charity charity = byId.get();
            Volunteer volunteer = byEmail.get();
            if (isQualified(volunteer, charity)) {
                VolunteerRequest request = new VolunteerRequest();
                request.setCharity(charity);
                request.setVolunteer(volunteer);
                request.setState(State.INQUEUE);
                request.setDescription(dto.getDescription());
                charity.getRequests().add(request);
                volunteer.getVolunteerRequests().add(request);
            } else {
                throw new Exception("volunteer is not qualified");
            }
        }
        throw new Exception("volunteer or charity dose not exist");
    }

    private boolean isQualified(Volunteer volunteer, Charity charity) {
        return volunteer.getAge() >= charity.getAgeLowerBound() && volunteer.getAge() <= charity.getAgeUpperBound() &&
                volunteer.getGender() == charity.getGender();
    }
}