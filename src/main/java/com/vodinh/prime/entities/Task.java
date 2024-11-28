package com.vodinh.prime.entities;

import com.vodinh.prime.enums.TaskStatus;
import com.vodinh.prime.entities.audit.DateAudit;
import com.vodinh.prime.listeners.TaskEntityListener;
import jakarta.persistence.*;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "task")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(TaskEntityListener.class)
public class Task extends DateAudit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "status", nullable = true)
    @Enumerated(EnumType.STRING)
    private TaskStatus status;

    // task belong to project
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id", referencedColumnName = "id", nullable = false)
    private Project project;

    // task was assigned to user
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "assigned_to", referencedColumnName = "id")
    private User assignedTo;
}
