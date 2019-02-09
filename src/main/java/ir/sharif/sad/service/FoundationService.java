package ir.sharif.sad.service;

import ir.sharif.sad.dto.CharityDto;
import ir.sharif.sad.dto.FoundationDto;
import ir.sharif.sad.dto.ProjectDto;
import ir.sharif.sad.entity.Charity;
import ir.sharif.sad.entity.Foundation;
import ir.sharif.sad.entity.Profession;
import ir.sharif.sad.entity.Project;
import ir.sharif.sad.repository.FoundationRepository;
import ir.sharif.sad.repository.ProfessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Set;

@Service
public class FoundationService {
    private FoundationRepository foundationRepository;
    private ProfessionRepository professionRepository;


    @Autowired
    public FoundationService(FoundationRepository foundationRepository, ProfessionRepository professionRepository){
        this.foundationRepository = foundationRepository;
        this.professionRepository = professionRepository;
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
    public Charity createCharity(CharityDto charityDto, int id) throws Exception{
        Optional<Foundation> byId = foundationRepository.findById(id);
        if(byId.isPresent()){
            Foundation foundation = byId.get();
            Set<Profession> professions = professionRepository.findByNameIn(charityDto.getProfessions());
            Charity charity = new Charity(charityDto, foundation, professions);
            foundation.getCharities().add(charity);
            return charity;
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
}
