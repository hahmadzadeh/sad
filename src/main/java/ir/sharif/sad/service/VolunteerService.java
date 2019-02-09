package ir.sharif.sad.service;

import ir.sharif.sad.dto.*;
import ir.sharif.sad.exceptions.EntityNotExistException;
import ir.sharif.sad.exceptions.InvalidAmountException;
import ir.sharif.sad.entity.*;
import ir.sharif.sad.enumerators.ProjectStatus;
import ir.sharif.sad.enumerators.State;
import ir.sharif.sad.exceptions.VolunteerExistException;
import ir.sharif.sad.repository.*;
import ir.sharif.sad.specification.CustomSpecification;
import ir.sharif.sad.specification.Filter;
import ir.sharif.sad.specification.SearchCriteria;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
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
    private final PaymentRepository paymentRepository;

    @Autowired
    public VolunteerService(VolunteerRepository volunteerRepository, ProjectRepository projectRepository
            , CharityRepository charityRepository, ProfessionRepository professionRepository, PaymentRepository paymentRepository) {
        this.volunteerRepository = volunteerRepository;
        this.projectRepository = projectRepository;
        this.charityRepository = charityRepository;
        this.professionRepository = professionRepository;
        this.paymentRepository = paymentRepository;
    }


    public Volunteer save(VolunteerDto volunteerDto, String name) throws VolunteerExistException {
        Optional<Volunteer> byEmail = volunteerRepository.findOneByEmail(name);
        if (byEmail.isPresent()) {
            logger.error("volunteer already signed up");
            throw new VolunteerExistException("volunteer exists");
        }
        Volunteer volunteer = new Volunteer(volunteerDto, name);
        volunteerRepository.save(volunteer);
        logger.info("create new volunteer");
        return volunteer;
    }

    @Transactional
    public Volunteer fillAbilities(VolunteerDto volunteerDto, Volunteer volunteer) {
        Stream<String> professionDtoStream = volunteerDto.getProfessions().stream()
                .filter(e -> professionRepository.findByName(e).isPresent());
        professionDtoStream.forEach(e -> volunteer.getProfessions()
                .add(professionRepository.findByName(e).get()));
        return volunteer;
    }

    @Transactional
    public Page<ProjectDto> readProjects(Pageable page, Filter filter) {
        PageRequest pageRequest = new PageRequest(page.getPageNumber(), page.getPageSize(), Sort.Direction.ASC, "deadLine");
        Timestamp current = new Timestamp(System.currentTimeMillis());
        Specification specified = filter.getSpecified();
        Page<Project> all = projectRepository.findAll(specified, pageRequest);
        all.get().filter(e -> e.getDeadLine() < current.getTime()
                && e.getStatus() == ProjectStatus.NOT_FINISHED)
                .forEach(e -> e.setStatus(ProjectStatus.FINISHED));
        List<ProjectDto> collect = all.get().map(ProjectDto::project2ProjectDto).collect(Collectors.toList());
        PageImpl<ProjectDto> projectDtos = new PageImpl<>(collect, page, all.getTotalElements());
        return projectDtos;
    }

    @Transactional
    public PaymentDto makePayment(Integer amount, String name, Integer id) throws Exception {
        Optional<Volunteer> byEmail = volunteerRepository.findOneByEmail(name);
        Optional<Project> byId = projectRepository.findById(id);
        Timestamp current = new Timestamp(System.currentTimeMillis());
        if (byEmail.isPresent() && byId.isPresent() && byId.get().getDeadLine() > current.getTime()) {
            Project project = byId.get();
            Volunteer volunteer = byEmail.get();
            if (amount <= project.getRemainingMoney() && amount > 0) {
                project.setPaidSoFar(project.getPaidSoFar() + amount);
                Payment payment = new Payment(volunteer, project, amount);
                project.getPayments().add(payment);
                volunteer.getPayments().add(payment);
                if (project.getRemainingMoney() == 0) {
                    project.setStatus(ProjectStatus.FINISHED);
                }
                return PaymentDto.PaymentDto2Payment(payment);
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

    public VolunteerDto readOne(String name) throws Exception {
        Optional<Volunteer> byId = volunteerRepository.findOneByEmail(name);
        if (byId.isPresent()) {
            return VolunteerDto.volunteer2VolunteerDto(byId.get());
        } else {
            logger.error("volunteer is not signed up yet");
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

    @Transactional
    public Volunteer updateProfile(VolunteerDto volunteerDto, String name) throws EntityNotExistException {
        Optional<Volunteer> byEmail = volunteerRepository.findOneByEmail(name);
        if (!byEmail.isPresent()) {
            logger.error("volunteer does not exist");
            throw new EntityNotExistException("volunteer does not exist");
        }
        Volunteer volunteer = byEmail.get();
        volunteer.setName(volunteerDto.getName());
        volunteer.setAge(volunteerDto.getAge());
        volunteer.setCity(volunteerDto.getCity());
        volunteer.setDistrict(volunteerDto.getDistrict());
        volunteer.setGender(volunteerDto.getGender());
        volunteer.setPhone(volunteerDto.getPhone());
        volunteer.setProvince(volunteerDto.getProvince());
        volunteer.setProfessions(new HashSet<>());
        return volunteer;
    }

    @Transactional
    public ProjectDto readOneProject(Integer id) throws EntityNotExistException {
        Optional<Project> byId = projectRepository.findById(id);
        if (!byId.isPresent()) {
            logger.error("project does not exist");
            throw new EntityNotExistException("project does not exist");
        }
        Timestamp current = new Timestamp(System.currentTimeMillis());
        if (byId.get().getDeadLine() < current.getTime() && byId.get().getStatus() == ProjectStatus.NOT_FINISHED) {
            byId.get().setStatus(ProjectStatus.FINISHED);
        }
        return ProjectDto.project2ProjectDto(byId.get());
    }

    public Page<PaymentDto> readMyPayments(Pageable page, Filter filterObj, String email) {
        Specification specified = filterObj.getSpecified();
        Optional<Volunteer> byEmail = volunteerRepository.findOneByEmail(email);
        Volunteer volunteer = byEmail.get();
        SearchCriteria searchCriteria = new SearchCriteria("volunteer", ":",  volunteer);
        if (specified == null) {
            specified = new CustomSpecification(searchCriteria);
        }else{
            Specification.where(specified).and(new CustomSpecification(searchCriteria));
        }
        Page<Payment> all = paymentRepository.findAll(specified, page);
        List<PaymentDto> collect = all.get()
                .map(PaymentDto::PaymentDto2Payment).collect(Collectors.toList());
        PageImpl<PaymentDto> ans = new PageImpl<PaymentDto>(collect, page, collect.size());
        return ans;
    }
}