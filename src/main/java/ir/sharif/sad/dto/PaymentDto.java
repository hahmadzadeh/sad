package ir.sharif.sad.dto;

import ir.sharif.sad.entity.Payment;
import lombok.Data;

import java.sql.Timestamp;

@Data
public class PaymentDto {
    private Integer amount;
    private Integer projectId;
    private ProjectDto project;
    private Timestamp paidTime;
    public static PaymentDto PaymentDto2Payment(Payment payment){
        PaymentDto dto = new PaymentDto();
        dto.amount = payment.getAmount();
        dto.project = ProjectDto.project2ProjectDto(payment.getProject());
        dto.projectId = payment.getId();
        dto.paidTime = payment.getTimestamp();
        return dto;
    }
}
