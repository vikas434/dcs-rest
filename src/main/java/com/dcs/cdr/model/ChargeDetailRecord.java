package com.dcs.cdr.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import lombok.Setter;
import lombok.Value;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "charge_detail_record")
@Data
@Builder
public class ChargeDetailRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Session identification cannot be null")
    private String sessionId;

    @NotNull(message = "Vehicle identification cannot be null")
    private Long vehicleId;

    @NotNull(message = "Start time cannot be null")
    private LocalDateTime startTime;

    @NotNull(message = "End time cannot be null")
    private LocalDateTime endTime;

    @NotNull(message = "Total cost cannot be null")
    @DecimalMin(value = "0.00", message = "Total cost must be greater than or equal to 0.00")
    //TODO need to check validation for 0.
    private Double totalCost;
}
