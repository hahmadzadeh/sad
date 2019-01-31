package ir.sharif.sad.service;

import ir.sharif.sad.dto.VolunteerDto;
import ir.sharif.sad.entity.Charity;
import ir.sharif.sad.entity.Payment;
import ir.sharif.sad.entity.Project;
import ir.sharif.sad.entity.Volunteer;
import ir.sharif.sad.enumerators.ProjectStatus;
import ir.sharif.sad.repository.CharityRepository;
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

@Service
public class VolunteerService {
    Logger logger = LoggerFactory.getLogger(VolunteerService.class);
    @Value("${page.size}")
    private Integer pageSize;
    private VolunteerRepository volunteerRepository;
    private ProjectRepository projectRepository;
    private CharityRepository charityRepository;

    @Autowired
    public VolunteerService(VolunteerRepository volunteerRepository, ProjectRepository projectRepository
            , CharityRepository charityRepository) {
        this.volunteerRepository = volunteerRepository;
        this.projectRepository = projectRepository;
        this.charityRepository = charityRepository;
    }

    public Volunteer save(VolunteerDto volunteerDto, String name) {
        return volunteerRepository.save(new Volunteer(volunteerDto, name));
    }

    public Page<Project> readProjects(Integer page) {
        PageRequest pageRequest = new PageRequest(page, pageSize, Sort.Direction.ASC, "deadLine");
        Timestamp current = new Timestamp(System.currentTimeMillis());
        return projectRepository.findByStatusAndDeadLineGreaterThanEqual
                (ProjectStatus.NOT_FINISHED, current, pageRequest);
    }

    @Transactional
    public Payment makePayment(Integer amount, String name, Integer id) throws Exception {
        Volunteer volunteer = volunteerRepository.findOneByEmail(name);
        Optional<Project> byId = projectRepository.findById(id);
        Timestamp current = new Timestamp(System.currentTimeMillis());
        if (volunteer != null && byId.isPresent() && byId.get().getDeadLine().after(current)) {
            Project project = byId.get();
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
                throw new Exception("Invalid amount of money(perhaps too much!!)");
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
}
