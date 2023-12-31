package lk.ijse.computershop.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class Order_DetailsDTO {
    private String orderId;
    private String itemCode;
    private Integer qty;
    private Double total;
    private LocalDate date;
}
