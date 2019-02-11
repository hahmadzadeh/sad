package ir.sharif.sad.repository;

import ir.sharif.sad.entity.Payment;
import ir.sharif.sad.entity.Volunteer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

public interface PaymentRepository extends CustomRepository<Payment, Integer>{
    Page<Payment> findAllByVolunteer(Volunteer volunteer, Pageable page, Specification specification);
}
