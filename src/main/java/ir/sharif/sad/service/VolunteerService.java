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
import java.util.List;
import java.util.Optional;
import java.util.Set;
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
    private final RequestRepository requestRepository;
    private final FoundationRepository foundationRepository;

    @Autowired
    public VolunteerService(VolunteerRepository volunteerRepository, ProjectRepository projectRepository
            , CharityRepository charityRepository, ProfessionRepository professionRepository, PaymentRepository paymentRepository, RequestRepository requestRepository, FoundationRepository foundationRepository) {
        this.volunteerRepository = volunteerRepository;
        this.projectRepository = projectRepository;
        this.charityRepository = charityRepository;
        this.professionRepository = professionRepository;
        this.paymentRepository = paymentRepository;
        this.requestRepository = requestRepository;
        this.foundationRepository = foundationRepository;
    }


    public Volunteer save(VolunteerDto volunteerDto, String name) throws VolunteerExistException {
        Optional<Volunteer> byEmail = volunteerRepository.findOneByEmail(name);
        if (byEmail.isPresent()) {
            logger.error("volunteer" + name +  "already signed up");
            throw new VolunteerExistException("volunteer" + name + "exists");
        }
        Volunteer volunteer = new Volunteer(volunteerDto, name);
        volunteerRepository.save(volunteer);
        logger.info("create new volunteer:" + volunteer.getId());
        return volunteer;
    }

    @Transactional
    public Volunteer fillAbilities(VolunteerDto volunteerDto, Volunteer volunteer) {
        Stream<String> professionDtoStream = volunteerDto.getProfessions().stream()
                .filter(e -> professionRepository.findByName(e).isPresent());
        professionDtoStream.forEach(e -> volunteer.getProfessions()
                .add(professionRepository.findByName(e).get()));
        logger.info("fill abilities for volunteer:" + volunteer.getId());
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
        logger.info("read projects");
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
                logger.info("volunteer:" + volunteer.getId() + " payed " + amount);
                return PaymentDto.PaymentDto2Payment(payment);
            } else {
                logger.error("Invalid amount of money(perhaps too much!!)");
                throw new InvalidAmountException("Invalid amount of money(perhaps too much!!)");
            }
        } else {
            logger.error("Volunteer not created please complete volunteer");
            throw new Exception("Volunteer not created please complete volunteer");
        }
    }

    @Transactional
    public Page<CharityDto> readCharities(Pageable page, Filter filter) {
        PageRequest pageRequest = new PageRequest(page.getPageNumber()
                , pageSize, Sort.Direction.ASC, "deadLine");
        Timestamp current = new Timestamp(System.currentTimeMillis());
        Specification specified = filter.getSpecified();
        Page<Charity> all = charityRepository.findAll(specified, pageRequest);
        all.get().filter(e -> e.getDeadLine() < current.getTime()
                && e.getStatus() == ProjectStatus.NOT_FINISHED)
                .forEach(e -> e.setStatus(ProjectStatus.FINISHED));
        List<CharityDto> collect = all.get().map(CharityDto::charity2CharityDto).collect(Collectors.toList());
        PageImpl<CharityDto> charityDtos = new PageImpl<>(collect, page, all.getTotalElements());
        logger.info("read charities");
        return charityDtos;
    }

    public VolunteerDto readMyProfile(String name) throws Exception {
        Optional<Volunteer> byId = volunteerRepository.findOneByEmail(name);
        if (byId.isPresent()) {
            logger.info("read profile for volunteer :" + name);
            return VolunteerDto.volunteer2VolunteerDto(byId.get());
        } else {
            logger.error("volunteer is not signed up yet");
            throw new Exception("volunteer is not signed up yet");
        }
    }

    @Transactional
    public VolunteerRequestDto makeRequest(VolunteerRequestDto dto, String name) throws Exception {
        Optional<Charity> byId = charityRepository.findById(dto.getCharityId());
        Optional<Volunteer> byEmail = volunteerRepository.findOneByEmail(name);
        if (byId.isPresent() && byEmail.isPresent() && byId.get().getStatus() == ProjectStatus.NOT_FINISHED) {
            Charity charity = byId.get();
            Volunteer volunteer = byEmail.get();
            Set<VolunteerRequest> collect = volunteer.getVolunteerRequests().parallelStream()
                    .filter(e -> e.getCharity().getId() == charity.getId()).collect(Collectors.toSet());
            if(!collect.isEmpty()){
                logger.error("you are already requested");
                throw new Exception("you are already requested");
            }
            if (isQualified(volunteer, charity)) {
                VolunteerRequest request = new VolunteerRequest();
                request.setCharity(charity);
                request.setVolunteer(volunteer);
                request.setState(State.INQUEUE);
                request.setDescription(dto.getDescription());
                charity.getRequests().add(request);
                volunteer.getVolunteerRequests().add(request);
                logger.info("volunteer" + volunteer.getId() + "made request for charity: " + charity.getId());
                return VolunteerRequestDto.volunteerRequest2VolunteerRequestDto(request);
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
        logger.info("volunteer: " + volunteer.getId() + "updates his profile");
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
        logger.info("read project:" + id);
        return ProjectDto.project2ProjectDto(byId.get());
    }

    public Page<PaymentDto> readMyPayments(Pageable page, Filter filterObj, String email) {
        Specification specified = filterObj.getSpecified();
        Optional<Volunteer> byEmail = volunteerRepository.findOneByEmail(email);
        Volunteer volunteer = byEmail.get();
        SearchCriteria searchCriteria = new SearchCriteria("volunteer", "#", volunteer);
        if (specified == null) {
            specified = new CustomSpecification(searchCriteria);
        } else {
            Specification.where(specified).and(new CustomSpecification(searchCriteria));
        }
        Page<Payment> all = paymentRepository.findAll(specified, page);
        List<PaymentDto> collect = all.get()
                .map(PaymentDto::PaymentDto2Payment).collect(Collectors.toList());
        PageImpl<PaymentDto> ans = new PageImpl<PaymentDto>(collect, page, collect.size());
        logger.info("volunteer " + volunteer.getId() + "reads his payments");
        return ans;
    }

    @Transactional
    public CharityDto readOneCharity(Integer id) throws EntityNotExistException {
        Optional<Charity> byId = charityRepository.findById(id);
        if (!byId.isPresent()) {
            logger.error("project does not exist");
            throw new EntityNotExistException("project does not exist");
        }
        Timestamp current = new Timestamp(System.currentTimeMillis());
        if (byId.get().getDeadLine() < current.getTime() && byId.get().getStatus() == ProjectStatus.NOT_FINISHED) {
            byId.get().setStatus(ProjectStatus.FINISHED);
        }
        logger.info("read charity: " + id);
        return CharityDto.charity2CharityDto(byId.get());
    }

    public Page<VolunteerRequestDto> readMyRequests(Pageable page, Filter filterObj, String name) {
        Specification specified = filterObj.getSpecified();
        Optional<Volunteer> byEmail = volunteerRepository.findOneByEmail(name);
        Volunteer volunteer = byEmail.get();
        SearchCriteria searchCriteria = new SearchCriteria("volunteer", "#", volunteer);
        if (specified == null) {
            specified = new CustomSpecification(searchCriteria);
        } else {
            Specification.where(specified).and(new CustomSpecification(searchCriteria));
        }
        Page<VolunteerRequest> all = requestRepository.findAll(specified, page);
        List<VolunteerRequestDto> collect = all.get()
                .map(VolunteerRequestDto::volunteerRequest2VolunteerRequestDto).collect(Collectors.toList());
        PageImpl<VolunteerRequestDto> ans = new PageImpl<>(collect, page, collect.size());
        logger.info("volunteer :" + volunteer.getId() + "read requests");
        return ans;
    }

    public List<ProfessionDto> readProfessions() {
        return professionRepository.findAll().parallelStream().map(ProfessionDto::profession2ProfessionDto)
                .collect(Collectors.toList());
    }

    public List<FoundationDto> readFoundations() {
        return foundationRepository.findAll().parallelStream()
                .map(FoundationDto::Foundation2FoundationDto).collect(Collectors.toList());
    }
}