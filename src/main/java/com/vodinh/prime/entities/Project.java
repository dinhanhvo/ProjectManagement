package com.vodinh.prime.entities;

import com.vodinh.prime.entities.audit.UserDateAudit;
import com.vodinh.prime.listeners.ProjectEntityListener;
import com.vodinh.prime.listeners.ProjectEventListener;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "project")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
//@EntityListeners(ProjectEntityListener.class)
@EntityListeners(ProjectEventListener.class)
public class Project extends UserDateAudit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description")
    private String description;

    // Foreign key relate to 'user' (ownerId)
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "owner_id", referencedColumnName = "id", nullable = false)
    private User owner;

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Task> tasks;

}
