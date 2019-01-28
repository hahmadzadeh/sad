package ir.sharif.sad.controller;


import ir.sharif.sad.dto.FoundationUserDto;
import ir.sharif.sad.entity.Foundation;
import ir.sharif.sad.repository.FoundationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RepositoryRestController
@RequestMapping("/foundations/")
public class FoundationController {
    private final FoundationRepository foundationRepository;

    @Autowired
    public FoundationController(FoundationRepository foundationRepository){
        this.foundationRepository = foundationRepository;
    }

    @PostMapping("/sign_up")
    public ResponseEntity signUp(@RequestBody FoundationUserDto foundationDto){
        return ResponseEntity.ok(foundationRepository.save(new Foundation(foundationDto.getName())));
    }

}
