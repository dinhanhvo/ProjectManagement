package com.vodinh.prime.entities.audit;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(
        value = {"createdAt", "updatedAt"},
        allowGetters = true
)
@Data
public abstract class DateAudit implements Serializable {

    @CreatedDate
    private Instant createdAt;

    @LastModifiedDate
    private Instant updatedAt;

    public LocalDateTime getCreatedAtAsLocalDateTime() {
        return getCreatedAt().atZone(ZoneId.systemDefault()).toLocalDateTime();
    }

    public LocalDateTime getUpdatetedAtAsLocalDateTime() {
        return getUpdatedAt().atZone(ZoneId.systemDefault()).toLocalDateTime();
    }
}
