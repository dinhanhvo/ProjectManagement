package com.vodinh.prime.entities;

import com.vodinh.prime.entities.audit.DateAudit;
import jakarta.persistence.*;
import lombok.*;

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

//    @Column(name = "contact_id", nullable = false)
//    private Long contactId;

    @ManyToOne
    @JoinColumn(name = "contact_id", nullable = false, referencedColumnName = "id", foreignKey = @ForeignKey(name = "fk_weights_contact_id"))
    private User user;
}
