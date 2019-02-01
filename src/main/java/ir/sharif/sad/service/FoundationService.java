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


    public Foundation save(FoundationDto foundationDto, String name) {
        return foundationRepository.save(new Foundation(foundationDto, name));
    }

    @Transactional
    public Foundation createProject(ProjectDto projectDto, int id) throws Exception {
        Optional<Foundation> byId = foundationRepository.findById(id);
        if(byId.isPresent()){
            Foundation foundation = byId.get();
            foundation.getProjects().add(new Project(projectDto, foundation));
            return foundation;
        }else {
            throw new Exception("foundation not found");
        }
    }

    @Transactional
    public Foundation createCharity(CharityDto charityDto, int id) throws Exception{
        Optional<Foundation> byId = foundationRepository.findById(id);
        if(byId.isPresent()){
            Foundation foundation = byId.get();
            Set<Profession> professions = professionRepository.findByName(charityDto.getProfessions());
            foundation.getCharities().add(new Charity(charityDto, foundation, professions));
            return foundation;
        }else {
            throw new Exception("foundation not found");
        }
    }
}
