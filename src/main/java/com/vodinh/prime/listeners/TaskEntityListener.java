package com.vodinh.prime.listeners;

import com.vodinh.prime.entities.Task;
import jakarta.persistence.PostPersist;
import jakarta.persistence.PostRemove;
import jakarta.persistence.PostUpdate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class TaskEntityListener {

    final private CacheManager cacheManager;

    public TaskEntityListener(CacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }

    @PostPersist
    @PostUpdate
    public void onTaskChange(Task task) {
        Long projectId = task.getProject().getId();
        if (Objects.nonNull(projectId)) {
            Objects.requireNonNull(cacheManager.getCache("projects")).evict(projectId);
        }
    }

    @PostRemove
    public void onTaskDelete(Task task) {
        Long projectId = task.getProject().getId();
        if (projectId != null) {
            Objects.requireNonNull(cacheManager.getCache("projects")).evict(projectId);
        }
    }
}
