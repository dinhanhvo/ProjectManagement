package com.vodinh.prime.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "region")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Region {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "region_id", nullable = false, unique = true, length = 255)
    private String regionId;

    @Column(nullable = false, length = 255)
    private String name;

    @Column(nullable = false)
    private Boolean status;
}
