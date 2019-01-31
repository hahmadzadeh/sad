package ir.sharif.sad.service;

import ir.sharif.sad.dto.VolunteerDto;
import ir.sharif.sad.entity.Volunteer;
import ir.sharif.sad.repository.VolunteerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VolunteerService {
    private VolunteerRepository volunteerRepository;

    @Autowired
    public VolunteerService(VolunteerRepository volunteerRepository){
        this.volunteerRepository = volunteerRepository;
    }

    public Volunteer save(VolunteerDto volunteerDto, String name) {
        return volunteerRepository.save(new Volunteer(volunteerDto, name));
    }
}
