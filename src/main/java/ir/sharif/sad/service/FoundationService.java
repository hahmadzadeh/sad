package ir.sharif.sad.service;

import ir.sharif.sad.dto.CharityDto;
import ir.sharif.sad.dto.FoundationDto;
import ir.sharif.sad.dto.ProjectDto;
import ir.sharif.sad.dto.VolunteerRequestDto;
import ir.sharif.sad.entity.*;
import ir.sharif.sad.enumerators.ProjectStatus;
import ir.sharif.sad.exceptions.EntityNotExistException;
import ir.sharif.sad.repository.CharityRepository;
import ir.sharif.sad.repository.FoundationRepository;
import ir.sharif.sad.repository.ProfessionRepository;
import ir.sharif.sad.repository.ProjectRepository;
import ir.sharif.sad.specification.CustomSpecification;
import ir.sharif.sad.specification.Filter;
import ir.sharif.sad.specification.SearchCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class FoundationService {
    private FoundationRepository foundationRepository;
    private ProfessionRepository professionRepository;
    private final ProjectRepository projectRepository;
    private final CharityRepository charityRepository;


    @Autowired
    public FoundationService(FoundationRepository foundationRepository, ProfessionRepository professionRepository, ProjectRepository projectRepository, CharityRepository charityRepository){
        this.foundationRepository = foundationRepository;
        this.professionRepository = professionRepository;
        this.projectRepository = projectRepository;
        this.charityRepository = charityRepository;
    }


    public FoundationDto save(FoundationDto foundationDto, String name) throws Exception {
        Optional<Foundation> oneByEmail = foundationRepository.findOneByEmail(name);
        if(oneByEmail.isPresent()){
            throw new Exception("already signed up");
        }
        return FoundationDto.Foundation2FoundationDto(foundationRepository.save(new Foundation(foundationDto, name)));
    }

    @Transactional
    public ProjectDto createProject(ProjectDto projectDto, String email) throws Exception {
        Optional<Foundation> byId = foundationRepository.findOneByEmail(email);
        if(byId.isPresent()){
            Foundation foundation = byId.get();
            Project project = new Project(projectDto, foundation);
            foundation.getProjects().add(project);
            return ProjectDto.project2ProjectDto(project);
        }else {
            throw new Exception("foundation not found");
        }
    }

    @Transactional
    public CharityDto createCharity(CharityDto charityDto, String email) throws Exception{
        Optional<Foundation> byId = foundationRepository.findOneByEmail(email);
        if(byId.isPresent()){
            Foundation foundation = byId.get();
            Set<Profession> professions = professionRepository.findByNameIn(charityDto.getProfessions());
            Charity charity = new Charity(charityDto, foundation, professions);
            foundation.getCharities().add(charity);
            return CharityDto.charity2CharityDto(charity);
        }else {
            throw new Exception("foundation not found");
        }
    }

    public Foundation readOne(String name) throws Exception {
        Optional<Foundation> byId = foundationRepository.findOneByEmail(name);
        if(byId.isPresent()){
            return byId.get();
        }else {
            throw new Exception("foundation is not signed up yet");
        }
    }

    @Transactional
    public FoundationDto update(FoundationDto foundationDto, String name) {
        Optional<Foundation> oneByEmail = foundationRepository.findOneByEmail(name);
        Foundation foundation = oneByEmail.get();
        foundation.setAboutUs(foundationDto.getAboutUs());
        foundation.setAddress(foundationDto.getAddress());
        foundation.setPhone(foundationDto.getPhone());
        return FoundationDto.Foundation2FoundationDto(foundation);
    }

    @Transactional
    public Page<ProjectDto> readProjects(Filter filter, String name, Pageable page) {
        PageRequest pageRequest = new PageRequest(page.getPageNumber(), page.getPageSize(), Sort.Direction.ASC, "deadLine");
        Timestamp current = new Timestamp(System.currentTimeMillis());
        Specification specified = filter.getSpecified();
        Optional<Foundation> oneByEmail = foundationRepository.findOneByEmail(name);
        SearchCriteria searchCriteria = new SearchCriteria("foundation", "#", oneByEmail.get());
        if(specified == null){
            specified = new CustomSpecification(searchCriteria);
        }else {
            Specification.where(specified).and(new CustomSpecification(searchCriteria));
        }
        Page<Project> all = projectRepository.findAll(specified, pageRequest);
        all.get().filter(e -> e.getDeadLine() < current.getTime()
                && e.getStatus() == ProjectStatus.NOT_FINISHED)
                .forEach(e -> e.setStatus(ProjectStatus.FINISHED));
        List<ProjectDto> collect = all.get().map(ProjectDto::project2ProjectDto).collect(Collectors.toList());
        PageImpl<ProjectDto> projectDtos = new PageImpl<>(collect, page, all.getTotalElements());
        return projectDtos;
    }

    @Transactional
    public Page<CharityDto> readCharity(Filter filter, String name, Pageable page) {
        PageRequest pageRequest = new PageRequest(page.getPageNumber()
                , page.getPageSize(), Sort.Direction.ASC, "deadLine");
        Timestamp current = new Timestamp(System.currentTimeMillis());
        Specification specified = filter.getSpecified();
        Optional<Foundation> oneByEmail = foundationRepository.findOneByEmail(name);
        SearchCriteria searchCriteria = new SearchCriteria("foundation", "#", oneByEmail.get());
        if(specified == null){
            specified = new CustomSpecification(searchCriteria);
        }else {
            Specification.where(specified).and(new CustomSpecification(searchCriteria));
        }
        Page<Charity> all = charityRepository.findAll(specified, pageRequest);
        all.get().filter(e -> e.getDeadLine() < current.getTime()
                && e.getStatus() == ProjectStatus.NOT_FINISHED)
                .forEach(e -> e.setStatus(ProjectStatus.FINISHED));
        List<CharityDto> collect = all.get().map(CharityDto::charity2CharityDto).collect(Collectors.toList());
        PageImpl<CharityDto> charityDtos = new PageImpl<>(collect, page, all.getTotalElements());
        return charityDtos;
    }

    public Page<VolunteerRequestDto> readMyRequests(Filter filter, Pageable page, Integer id, String name) throws EntityNotExistException {
        Optional<Charity> byId = charityRepository.findById(id);
        if(byId.isPresent() && byId.get().getFoundation().getEmail().equals(name)){
            Specification specified = filter.getSpecified();
            Page<VolunteerRequest> all = charityRepository.findAll(specified, page);
            List<VolunteerRequestDto> collect = all.get().
                    map(VolunteerRequestDto::volunteerRequest2VolunteerRequestDto).collect(Collectors.toList());
            return new PageImpl<>(collect, page, all.getTotalElements());
        }else{
            throw new EntityNotExistException("charity is not found");
        }
    }
}
