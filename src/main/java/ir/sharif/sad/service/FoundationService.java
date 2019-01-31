package ir.sharif.sad.service;

import ir.sharif.sad.dto.FoundationDto;
import ir.sharif.sad.dto.FoundationUserDto;
import ir.sharif.sad.dto.ProjectDto;
import ir.sharif.sad.entity.Foundation;
import ir.sharif.sad.entity.Project;
import ir.sharif.sad.repository.FoundationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class FoundationService {
    private FoundationRepository foundationRepository;

    @Autowired
    public FoundationService(FoundationRepository foundationRepository){
        this.foundationRepository = foundationRepository;
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
}
