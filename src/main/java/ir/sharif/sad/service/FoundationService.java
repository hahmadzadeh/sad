package ir.sharif.sad.service;

import ir.sharif.sad.dto.FoundationUserDto;
import ir.sharif.sad.entity.Foundation;
import ir.sharif.sad.repository.FoundationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FoundationService {
    private FoundationRepository foundationRepository;

    @Autowired
    public FoundationService(FoundationRepository foundationRepository){
        this.foundationRepository = foundationRepository;
    }


    public Foundation save(FoundationUserDto foundationDto, String name) {
        return foundationRepository.save(new Foundation(foundationDto, name));
    }
}
