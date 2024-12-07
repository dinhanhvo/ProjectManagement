package com.vodinh.prime.entities;

import com.vodinh.prime.entities.audit.DateAudit;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "weights")
public class Weight extends DateAudit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "serial_number", nullable = false, length = 255)
    private String serialNumber;

    @Column(name = "model", nullable = false, length = 255)
    private String model;

    @Column(name = "quy_cach", nullable = false)
    private Double quyCach;

    @Column(name = "sell_at")
    private LocalDateTime sellAt;

    @ManyToOne
    @JoinColumn(name = "contact_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "line_id", nullable = false)
    private Line line;
}
